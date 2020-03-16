package pro.butovanton.countries;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestBuilder;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

class countrieRecyclerAdapter extends RecyclerView.Adapter<countrieRecyclerAdapter.countrieViewHolder> {
    private final LayoutInflater mInflater;
    private List<Countrie> countries;
    private static Context context;

    public countrieRecyclerAdapter(Activity2 context, List<Countrie> countries) {
        this.context = (Context) context;
        mInflater = LayoutInflater.from((Context) context);
        this.countries = (List<Countrie>) countries;
    }

    @NonNull
    @Override
    public countrieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);

        return new countrieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(countrieViewHolder holder, int position) {
        holder.setName(countries.get(position).name);


    }


    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class countrieViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameT;
        private final ImageView imageView;

        public countrieViewHolder(View view) {
            super(view);
            nameT = (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.imageViewrecicler);

            String patch = "/sdcard/Android/data/pro.butovanton.countries/files/Flags/test-1.svg";
            File file = new File(patch);
            if (!file.exists()) {
                Log.d("DEBUG", "file find");
            } else {
                Uri nuri = Uri.fromFile(file);
                SvgLoader.pluck()
                        .with((Activity)context)
                        .load(nuri, imageView);
            }
        }

        public void setName(String name) {
            nameT.setText(name);
        }

        public ImageView getImageView(String patch) {
            return imageView;
        }
    }

}
