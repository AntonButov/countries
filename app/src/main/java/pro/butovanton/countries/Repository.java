package pro.butovanton.countries;

import android.app.Application;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository extends Application {
    private static Repository INSTANCE;
    private static LiveData<List<Countrie>> countries;
    private cDao dao;

    private Application application;
    NetworkService networkService;
    JSONPlaceHolderApi jsonPlaceHolderApi;

    private Repository(Application application) {
        this.application = application;
         cRoomDatabase db = cRoomDatabase.getDatabase(application);
               dao = db.cdao();

        List<Countrie> countrieList = dao.getAll();
        if (countrieList.size() > 0) {
            MutableLiveData<List<Countrie>> data = new MutableLiveData<>();
            countries = data;
            data.setValue(countrieList);
        }
        else countries  = loadWebservice();
    }

    public static LiveData<List<Countrie>> getAllCountries(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(application);
        }
      return countries;
    }

    LiveData<List<Countrie>> loadWebservice() {
        Log.d("DEBUG", "Load from web");
        networkService = NetworkService.getInstance();
        jsonPlaceHolderApi = networkService.getJSONApi();
        MutableLiveData<List<Countrie>> data = new MutableLiveData<>();
        List<Countrie> listcountries = new ArrayList<>();
        jsonPlaceHolderApi.getAllPosts().enqueue(new Callback<List<POJO>>() {
            @Override
            public void onResponse(Call<List<POJO>> call, Response<List<POJO>> response) {
                for (POJO pojo : response.body())
                    listcountries.add(new Countrie(pojo.getname(), pojo.getcapital(),pojo.getcurriencies().get(0),pojo.getflag(), ""));

                Observable.range(0,listcountries.size())
                        .map(i2 -> {
                                    String url = listcountries.get(i2).flag;
                                    Response<ResponseBody> responseBody = downloadflagSinch(url);
                                    String patch = writeResponseBodyToDisk(responseBody, getFilename(url));
                                    listcountries.get(i2).flagpatch = patch;
                                    Log.d("DEBUG", patch);
                                    return i2;
                                }

                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d("DEBUG", "onSubscribe" + d.toString());
                            }

                            @Override
                            public void onNext(@NonNull Integer integer) {
                           //     Log.d("DEBUG", "onNext");
                           }
                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG", "onError" + e);
                           }

                            @Override
                            public void onComplete() {
                                Log.d("DEBUG", "onComplite");
                                //Проверку на полноту загрузки надо бы по хорошему
                                for (int i = 0; i < listcountries.size(); i++)
                                    if (listcountries.get(i).flagpatch == "") {
                                        listcountries.clear();
                                        break;
                                    }
                                if (listcountries.size() > 0)
                                      new saveRoom(dao).execute(listcountries);
                                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                data.setValue(listcountries); // finish of data load
                                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }
                        });
            }
             @Override
            public void onFailure(Call<List<POJO>> call, Throwable t) {
                Log.d("DEBUG", "api failure " + t);
                 data.setValue(listcountries);
            }
        });
    return data;
    }

    Response<ResponseBody> downloadflagSinch(String patch) {
        Response<ResponseBody> responseBody = null;
        try {
            responseBody = jsonPlaceHolderApi.downloadFlag(patch).execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DEBUG","download failure " + e);
        }

        return responseBody;
    }

        String downloadflag(String patch) {

            jsonPlaceHolderApi.downloadFlag(patch).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("DEBUG", "download flags failure");
                }
            });
        //    return res[0];
        Log.d("DEBUG","return");
        return "";
        }

    private String writeResponseBodyToDisk(Response<ResponseBody> body, String filename) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(application.getApplicationContext().getFilesDir() + File.separator + filename);
            String res = futureStudioIconFile.getAbsolutePath();
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.body().contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.body().byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("DEBUG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return res;
            } catch (IOException e) {
                return "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return "";
        }
    }

       String getFilename(String patch) {
        Uri uri= Uri.parse(patch);
        return new File(uri.getPath()).getName(); //даст имя с расширением
        }

    private static class saveRoom extends AsyncTask<List<Countrie>, Void, Void> {

        private cDao mAsyncTaskDao;

        saveRoom(cDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Countrie>... params) {
                for (int i = 0; i < params[0].size(); i++ )
                mAsyncTaskDao.insert(params[0].get(i));
                Log.d("DEBUG", "bd seved ok");
            return null;
        }
    }


}

