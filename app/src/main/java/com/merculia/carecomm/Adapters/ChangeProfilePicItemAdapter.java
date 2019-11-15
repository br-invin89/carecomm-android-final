package com.merculia.carecomm.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.merculia.carecomm.Activities.MainActivity;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.ChangProfilePicFragment;
import com.merculia.carecomm.Frgments.OpenCamerFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ChangeProfilePicItemAdapter extends RecyclerView.Adapter<ChangeProfilePicItemAdapter.ItemView> {

    private List<String> dataSet;
    private Context mContext;
    private int selectedPosition;
    private BaseFragmentActivity mainActivity;

    public OnItemClickLinter onItemClickLinter;
    private Dialog dialog;

    public ChangeProfilePicItemAdapter(Context context, List<String> dataSet, BaseFragmentActivity mainActivity, Dialog dialog) {
        this.dataSet = dataSet;
        this.mContext = context;
        this.selectedPosition = selectedPosition;
        this.mainActivity = mainActivity;
        this.dialog = dialog;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_change_profile, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChangeProfilePicItemAdapter.ItemView holder, int position) {
        String categoryModel = dataSet.get(position);
        if (position == dataSet.size()-1){
            holder.tvEventTitle.setTextColor(ContextCompat.getColor(mContext,R.color.remove_pic_color));
        }else {
            holder.tvEventTitle.setTextColor(ContextCompat.getColor(mContext,R.color.black_dull));
        }
        holder.tvEventTitle.setText(categoryModel);



    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvEventTitle;


        private ItemView(View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.idOption);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (itemView == v){
                int postion = getAdapterPosition();

                Bundle bundle = new Bundle();
                if (postion == 0){

                    bundle.putBoolean(Constants.isVideoRecording,false);
                    mainActivity.ReplaceFragmentWithBackstack(new OpenCamerFrgment(),bundle);
                }else
                if (postion == 1){
                    mainActivity.ReplaceFragmentWithBackstack(new ChangProfilePicFragment(),false,true);
                }

                dialog.dismiss();
            }
        }
    }
}
