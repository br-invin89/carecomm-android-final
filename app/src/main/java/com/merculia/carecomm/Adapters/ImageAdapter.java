package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.merculia.carecomm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    ArrayList<Integer> imageUrl;
    Context mContext;

    public ImageAdapter(ArrayList<Integer> imageUrl,Context context) {
        this.imageUrl = imageUrl;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view_item, parent, false);

        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
//        Glide.with(mContext).load(imageUrl.get(position)).into(holder.other);
        holder.other.setImageResource(imageUrl.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }


    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView other;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            other = itemView.findViewById(R.id.images);
        }
    }
}
