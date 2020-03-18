package pro.butovanton.countries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.io.File;
import java.util.List;

class countrieRecyclerAdapter extends RecyclerView.Adapter<countrieRecyclerAdapter.countrieViewHolder> {
    private final LayoutInflater mInflater;
    private List<Countrie> countries;
    private static Context context;

    public countrieRecyclerAdapter(Context context, List<Countrie> countries) {
        this.context = context;
        mInflater = LayoutInflater.from((Context) context);
        this.countries = countries;
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
        String patch = countries.get(position).flagpatch;
        File file = new File(patch);
        if (!file.exists()) {
            // Log.d("DEBUG", "file find");
        } else {
            Uri nuri = Uri.fromFile(file);
            SvgLoader.pluck()
                    .with((Activity)context)
                    .load(nuri, holder.imageView);
        }
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Activity3.class);
                    context.startActivity(intent);
                    Activity2.countries = countries;

                    Log.d("DEBUG","click " + getAdapterPosition());
                }
            });
        }

        public void setName(String name) {
            nameT.setText(name);
        }

        public ImageView getImageView(String patch) {
            return imageView;
        }
    }

}
