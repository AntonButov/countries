package pro.butovanton.countries;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ahmadrosid.svgloader.SvgLoader;

import java.io.File;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    public static ViewModelCountries viewModelCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        viewModelCountries = new ViewModelProvider(this).get(ViewModelCountries.class);
        viewModelCountries.getAllCountries().observe(this, new Observer<List<Countrie>>() {
            @Override
            public void onChanged(@Nullable final List<Countrie> countries) {
                Log.d("DEBUG", "data ok");
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(SplashActivity.this, Activity2.class);
                startActivity(intent);
                Activity2.countries = countries;
                finish();
            }
           });
    }
}
