package pro.butovanton.countries;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

public class ViewModelCountries extends AndroidViewModel {

    Repository repository;
    LiveData<List<Countrie>> countries;

    public ViewModelCountries(Application application) {
        super(application);
       Log.d("DEBUG","view model");
       repository = new Repository(application);
       countries = repository.getAllCountries();
    }

    public LiveData<List<Countrie>> getAllCountries() {
        return countries;
    }

}
