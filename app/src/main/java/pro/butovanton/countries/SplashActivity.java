package pro.butovanton.countries;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
        viewModelCountries = ViewModelProviders.of(this).get(ViewModelCountries.class);
        viewModelCountries.getAllCountries().observe(this, new Observer<List<Countrie>>() {
            @Override
            public void onChanged(@Nullable final List<Countrie> countries) {
                Log.d("DEBUG", "data ok");
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(SplashActivity.this, Activity2.class);
                startActivity(intent);
                finish();
            }
           });

        ImageView imageView = findViewById(R.id.imageView);

        String patch = "/sdcard/Android/data/pro.butovanton.countries/files/Flags/test.svg";
        File file = new File(patch);
        if (!file.exists()) {
            Log.d("DEBUG", "file find");
        }
        else {
            Uri nuri = Uri.fromFile(file);
            SvgLoader.pluck()
                    .with(this)
                    .load(nuri, imageView);
        }
    }
}
