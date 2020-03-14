package pro.butovanton.countries;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private ViewModelCountries viewModelCountriesSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        viewModelCountriesSplash = ViewModelProviders.of(this).get(ViewModelCountries.class);
        viewModelCountriesSplash.getAllCountries().observe(this, new Observer<List<Countrie>>() {
            @Override
            public void onChanged(@Nullable final List<Countrie> countries) {
                Log.d("DEBUG","data ok");
                // Update the cached copy of the words in the adapter.
               // adapter.setWords(words);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
