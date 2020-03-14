package pro.butovanton.countries;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
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

        networkService = NetworkService.getInstance();
        jsonPlaceHolderApi = networkService.getJSONApi();
        countries = loadWebservice();
        return countries;
    }

    LiveData<List<Countrie>> loadWebservice() {
        MutableLiveData<List<Countrie>> data = new MutableLiveData<>();
        jsonPlaceHolderApi.getAllPosts().enqueue(new Callback<List<POJO>>() {
            @Override
            public void onResponse(Call<List<POJO>> call, Response<List<POJO>> response) {
                List<Countrie> listcountries = new ArrayList<>();
                for (POJO pojo: response.body())
                    listcountries.add(new Countrie(pojo.getname(), pojo.getcapital(), pojo.getcurriencies(), pojo.getflag()));
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                data.setValue(listcountries); // finish of data load
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }

            @Override
            public void onFailure(Call<List<POJO>> call, Throwable t) {
                Log.d("DEBUG","api failure " + t);

            }

        });
    return data;
    }

}

