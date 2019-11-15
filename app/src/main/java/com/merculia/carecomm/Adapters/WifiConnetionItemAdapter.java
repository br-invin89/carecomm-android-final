package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merculia.carecomm.Model.CallModel;
import com.merculia.carecomm.Model.WifiConectionModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class WifiConnetionItemAdapter extends RecyclerView.Adapter<WifiConnetionItemAdapter.ItemView> {

    private List<WifiConectionModel> dataSet;
    private Context mContext;

    public OnItemClickLinter onItemClickLinter;

    public WifiConnetionItemAdapter(Context context, List<WifiConectionModel> dataSet) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_wifi_conection, viewGroup, false);
        return new ItemView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WifiConnetionItemAdapter.ItemView holder, int position) {
        WifiConectionModel menuItemModel = dataSet.get(position);

        Glide.with(mContext).load(menuItemModel.getWifiIcon()).into(holder.ivWifiIcon);
        holder.tvTitle.setText(menuItemModel.getWifiName());

        if (menuItemModel.isConnected()){
            holder.ivSelectedIcon.setVisibility(View.VISIBLE);
        }else {
            holder.ivSelectedIcon.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {
        ImageView ivSelectedIcon,ivWifiIcon;
        TextView tvTitle;

        private ItemView(View itemView) {
            super(itemView);
            ivSelectedIcon = itemView.findViewById(R.id.iv_selected_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivWifiIcon = itemView.findViewById(R.id.iv_wifi_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickLinter != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickLinter.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
