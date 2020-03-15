package pro.butovanton.countries;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.ahmadrosid.svgloader.SvgLoader;
//import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

import static pro.butovanton.countries.SplashActivity.viewModelCountries;

public class Activity2 extends AppCompatActivity {
  private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        recyclerView = findViewById(R.id.reciclerView);
        final countrieRecyclerAdapter adapter = new countrieRecyclerAdapter(this, viewModelCountries.getAllCountries().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


  //      List<Countrie> countrieList = viewModelCountries.getAllCountries().getValue();


    }

}










