package pro.butovanton.countries;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;


public class Activity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
//    public static List<Countrie> countries;
    private ViewModelCountries viewModelCountries2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        recyclerView = findViewById(R.id.reciclerView);
        viewModelCountries2 = new ViewModelProvider(this).get(ViewModelCountries.class);
        List<Countrie> countrieList =  viewModelCountries2.getAllCountries().getValue();
        final countrieRecyclerAdapter adapter = new countrieRecyclerAdapter(this,  countrieList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}










