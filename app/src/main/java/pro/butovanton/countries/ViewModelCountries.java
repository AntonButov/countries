package pro.butovanton.countries;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelCountries extends AndroidViewModel {

    Repository repository;
    LiveData<List<Countrie>> countries;

    public ViewModelCountries(Application application) {
        super(application);
        repository = new Repository(application);
        countries = repository.getAllCountries();
    }



    public LiveData<List<Countrie>> getAllCountries() {
        return countries;
    }

}
