package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.merculia.carecomm.Activities.MainActivity;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.MobileFragments.PhotoDetailFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosItemAdapter extends RecyclerView.Adapter<PhotosItemAdapter.ItemView> {

    private List<Integer> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;

    public OnItemClickLinter onItemClickLinter;

    public PhotosItemAdapter(BaseFragmentActivity mainActivity, Context context, List<Integer> dataSet) {
        this.dataSet = dataSet;
        this.mContext = context;
        this.mainActivity = mainActivity;

    }

    public interface OnItemClickLinter{
        public void onItemClick(int postion);
    }


    public void setItemClickLinter(OnItemClickLinter onItemClickLinter){
        this.onItemClickLinter = onItemClickLinter;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_photos, viewGroup, false);
        return new ItemView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PhotosItemAdapter.ItemView holder, int position) {
        int menuItemModel = dataSet.get(position);
        holder.imageProfile.setImageResource(menuItemModel);
    //Glide.with(mContext).load(Uri.fromFile(new File(menuItemModel))).into(holder.imageProfile);

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageProfile;


        private ItemView(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.image_view_photos);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (!mainActivity.isTablet(mContext)){
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.POSITION,getAdapterPosition());

                mainActivity.ReplaceFragmentWithBackstack(new PhotoDetailFrgment(),bundle);
            }
        }
    }
}
