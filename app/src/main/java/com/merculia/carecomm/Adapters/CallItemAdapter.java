package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merculia.carecomm.Activities.DialVideoCallActivity;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.ChatRoomFrgment;
import com.merculia.carecomm.Frgments.DialCallFrgment;
import com.merculia.carecomm.Model.CallModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class CallItemAdapter extends RecyclerView.Adapter<CallItemAdapter.ItemView> {

    private List<CallModel> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;
    public OnItemClickLinter onItemClickLinter;

    public CallItemAdapter(Context context, List<CallModel> dataSet,BaseFragmentActivity mainActivity) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_call_item, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CallItemAdapter.ItemView holder, int position) {
        CallModel menuItemModel = dataSet.get(position);

        Glide.with(mContext).load(menuItemModel.getProfilePic()).into(holder.imageProfile);
        holder.tvTitle.setText(menuItemModel.getTitle());
        holder.tvCallType.setText(menuItemModel.getCallType());
        holder.tvCallDate.setText(menuItemModel.getCallDate());
        holder.tvCallDate.setText(menuItemModel.getCallDate());
        if (menuItemModel.getCallPhoto() == 0){
            holder.ivCallImage.setVisibility(View.GONE);
        }else {
            Glide.with(mContext).load(menuItemModel.getCallPhoto()).into(holder.ivCallImage);
        }

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {
        ImageView imageProfile,ivCallImage;
        TextView tvTitle,tvCallType,tvCallTime,tvCallDate;

        private ItemView(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.profile_image);
            ivCallImage = itemView.findViewById(R.id.iv_call_type);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCallType = itemView.findViewById(R.id.tv_call_title);
            tvCallTime = itemView.findViewById(R.id.tv_call_time);
            tvCallDate = itemView.findViewById(R.id.tv_call_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dataSet.get(getAdapterPosition()).getScreenName().equalsIgnoreCase("Chat")){
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(Constants.isMinimize,false);
                        mainActivity.ReplaceFragmentWithBackstack(new ChatRoomFrgment(),bundle);
                    }else if (dataSet.get(getAdapterPosition()).getScreenName().equalsIgnoreCase("Call")){
                        mainActivity.ReplaceFragmentWithBackstack(new DialCallFrgment(), false, true);
                    }else if (dataSet.get(getAdapterPosition()).getScreenName().equalsIgnoreCase("Video Call")){
                        mainActivity.openActivity(mContext, DialVideoCallActivity.class);
                    }
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
