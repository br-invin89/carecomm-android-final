package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Profile.RelativeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class RelativeProfileItemAdapter extends RecyclerView.Adapter<RelativeProfileItemAdapter.ItemView> {

    private List<RelativeModel> dataSet;
    private Context mContext;

    public OnItemClickLinter onItemClickLinter;

    public RelativeProfileItemAdapter(Context context, List<RelativeModel> dataSet) {
        this.dataSet = dataSet;
        this.mContext = context;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_family_profile, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RelativeProfileItemAdapter.ItemView holder, int position) {
        RelativeModel relativeModel = dataSet.get(position);

        if( relativeModel.userRef.photo.contentEquals("") )
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(holder.ivProfilePic);
        else
            Picasso.get().load(relativeModel.userRef.photo).into(holder.ivProfilePic);
        holder.tvName.setText(relativeModel.userRef.name);
        holder.tvRelationship.setText(relativeModel.relationship);
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {

        ImageView ivProfilePic;
        TextView tvName, tvRelationship;

        private ItemView(View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.iv_profile_pic);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRelationship = itemView.findViewById(R.id.tv_relationship);

        }
    }
}
