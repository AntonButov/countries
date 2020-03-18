package pro.butovanton.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;

import java.io.File;

public class Activity3 extends AppCompatActivity {

    private TextView tname, tcapital, tcur;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        tname = findViewById(R.id.textName);
        tcapital = findViewById(R.id.textCapital);
        tcur = findViewById(R.id.textCur);
        imageView = findViewById(R.id.imageViewF);
        Intent intent = getIntent();
        tname.setText("Name: " + intent.getStringExtra("name"));
        tcapital.setText("Capital: " + intent.getStringExtra("capital"));
        tcur.setText("Currencie: " + intent.getStringExtra("currencie"));
        String patch = intent.getStringExtra("flagpatch");
        File file = new File(patch);
        if (!file.exists()) {

        } else {
            Uri nuri = Uri.fromFile(file);
            SvgLoader.pluck()
                    .with(this)
                    .load(nuri, imageView);
        }
    }
}
