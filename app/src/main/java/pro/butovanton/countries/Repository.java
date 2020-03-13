package pro.butovanton.countries;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private LiveData<List<Countrie>> countries = new MutableLiveData<>();
    //  private List<Newm> newmscash = new ArrayList<>();

    private NetworkService networkService;
    private JSONPlaceHolderApi jsonPlaceHolderApi;

    Repository(Application application) {
        //      RoomDatabase db = roomDatabase.getDatabase(application);
        //       dao = db.Dao();
        //     countries = dao.getAllCoutries();
    }

    public LiveData<List<Countrie>> getAllCountries() {

        countries = loadWebservice();
        return countries;
    }

    LiveData<List<Countrie>> loadWebservice() {
        MutableLiveData<List<Countrie>> webcontries = new MutableLiveData<>();
        jsonPlaceHolderApi.getAllPosts().enqueue(new Callback<LiveData<List<POJO>>>() {
            @Override
            public void onResponse(Call<LiveData<List<POJO>>> call, Response<LiveData<List<POJO>>> response) {
               // countries.
            }

            @Override
            public void onFailure(Call<LiveData<List<POJO>>> call, Throwable t) {

            }

        });
    return webcontries;
    }

}

