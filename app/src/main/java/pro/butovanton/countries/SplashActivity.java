package pro.butovanton.countries;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private ViewModelCountries viewModelCountriesSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModelCountriesSplash = ViewModelProviders.of(this).get(ViewModelCountries.class);
        viewModelCountriesSplash.getAllCountries().observe(this, new Observer<List<Countrie>>() {
            @Override
            public void onChanged(@Nullable final List<Countrie> countries) {
                // Update the cached copy of the words in the adapter.
               // adapter.setWords(words);
            }
        });
    }
}
