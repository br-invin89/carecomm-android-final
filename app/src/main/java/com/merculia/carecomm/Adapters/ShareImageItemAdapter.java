package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Model.FamilyModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShareImageItemAdapter extends RecyclerView.Adapter<ShareImageItemAdapter.ItemView> {

    private List<FamilyModel> dataSet;
    private Context mContext;

    public OnItemClickLinter onItemClickLinter;
    private int selectedPosition = 0;

    public ShareImageItemAdapter(Context context, List<FamilyModel> dataSet) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_share_user, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ShareImageItemAdapter.ItemView holder, int position) {
        FamilyModel familyModel = dataSet.get(position);


        holder.ivProfilePic.setImageResource(familyModel.getProfilePic());
        holder.tvName.setText(familyModel.getName());

        if (position == selectedPosition){
            holder.ivProfilePic.setBorderColor(ContextCompat.getColor(mContext,R.color.tan));
            holder.ivProfilePic.setBorderWidth(10);
        }else {
            holder.ivProfilePic.setBorderColor(ContextCompat.getColor(mContext,R.color.white));
            holder.ivProfilePic.setBorderWidth(1);
        }


    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView ivProfilePic;
        TextView tvName;

        private ItemView(View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.iv_profile_pic);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            selectedPosition = getAdapterPosition();
            notifyDataSetChanged();
        }
    }
}
