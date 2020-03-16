package pro.butovanton.countries;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_PICTURES;

public class Repository {

    private LiveData<List<Countrie>> countries = new MutableLiveData<>();
    //  private List<Newm> newmscash = new ArrayList<>();

    private DownloadManager dm;
   // private Context context;

    Repository(Application application) {
        dm = (DownloadManager) application.getSystemService(DOWNLOAD_SERVICE);
       // context =;

        //      RoomDatabase db = roomDatabase.getDatabase(application);
        //       dao = db.Dao();
        //     countries = dao.getAllCoutries();
    }

    public LiveData<List<Countrie>> getAllCountries() {

        countries = loadWebservice();
        return countries;
    }

    LiveData<List<Countrie>> loadWebservice() {
        Log.d("DEBUG", "Load from web");
        NetworkService networkService;
        JSONPlaceHolderApi jsonPlaceHolderApi;
        networkService = NetworkService.getInstance();
        jsonPlaceHolderApi = networkService.getJSONApi();

        MutableLiveData<List<Countrie>> data = new MutableLiveData<>();
        jsonPlaceHolderApi.getAllPosts().enqueue(new Callback<List<POJO>>() {
            @Override
            public void onResponse(Call<List<POJO>> call, Response<List<POJO>> response) {
                List<Countrie> listcountries = new ArrayList<>();
                for (POJO pojo : response.body())
                    listcountries.add(new Countrie(pojo.getname(), pojo.getcapital(), pojo.getcurriencies(), pojo.getflag()));


                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                data.setValue(listcountries); // finish of data load
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }


            @Override
            public void onFailure(Call<List<POJO>> call, Throwable t) {
                Log.d("DEBUG", "api failure " + t);

            }

        });
     //   downloadFlag2("https://raw.github.com/square/okhttp/master/README.md");
        return data;
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




    private void downloadFlag() {
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

