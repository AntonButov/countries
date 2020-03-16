package pro.butovanton.countries;

import android.app.Application;
import android.app.DownloadManager;
import android.net.Uri;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Repository {

    private LiveData<List<Countrie>> countries = new MutableLiveData<>();
    //  private List<Newm> newmscash = new ArrayList<>();
    NetworkService networkService;
    JSONPlaceHolderApi jsonPlaceHolderApi;

    private DownloadManager dm;
    private Application application;
   // private Context context;

    public Repository(Application application) {
        this.application = application;
        dm = (DownloadManager) application.getSystemService(DOWNLOAD_SERVICE);
       // context =;

        //      RoomDatabase db = roomDatabase.getDatabase(application);
        //       dao = db.Dao();
        //     countries = dao.getAllCoutries();
        networkService = NetworkService.getInstance();
        jsonPlaceHolderApi = networkService.getJSONApi();
    }

    public LiveData<List<Countrie>> getAllCountries() {

        countries = loadWebservice();
        return countries;
    }

    LiveData<List<Countrie>> loadWebservice() {
        Log.d("DEBUG", "Load from web");

        MutableLiveData<List<Countrie>> data = new MutableLiveData<>();

        jsonPlaceHolderApi.getAllPosts().enqueue(new Callback<List<POJO>>() {
            @Override
            public void onResponse(Call<List<POJO>> call, Response<List<POJO>> response) {
                List<Countrie> listcountries = new ArrayList<>();
                for (POJO pojo : response.body())
                    listcountries.add(new Countrie(pojo.getname(), pojo.getcapital(), pojo.getcurriencies(), pojo.getflag()));








                String s = downloadflag(listcountries.get(0).flag);
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                data.setValue(listcountries); // finish of data load
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            @Override
            public void onFailure(Call<List<POJO>> call, Throwable t) {
                Log.d("DEBUG", "api failure " + t);
            }
        });
        return data;
    }

        String downloadflag(String patch) {
          //  final String[] res = new String[1];
            jsonPlaceHolderApi.downloadFlag(patch).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = writeResponseBodyToDisk(response.body());
                Log.d("DEBUG", res);
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

    private String writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(application.getApplicationContext().getExternalFilesDir(null) + File.separator + "Future Studio Icon.svg");
            String res = futureStudioIconFile.getAbsolutePath();
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
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

        void downloadFlag2(String url) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

           try (okhttp3.Response response = client.newCall(request).execute()) {
                response.body();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("DEBUG", "File don't download");
            }
        }




    private void downloadFlag1() {
    try {
        String file = "test" + ".svg";

         Uri downloadUri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg");
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
   //     request.setDestinationInExternalFilesDir(context, "Flags", File.separator + file);
        dm.enqueue(request);
    } catch (IllegalStateException ex) {
        ex.printStackTrace();
        Log.d("DEBUG", "Storage error! " + ex);
    } catch (Exception ex) {
        ex.printStackTrace();
        Log.d("DEBUG","Unable to save image " + ex);
    }


}



}

