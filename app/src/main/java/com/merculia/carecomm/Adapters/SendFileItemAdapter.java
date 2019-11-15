package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merculia.carecomm.Activities.MainActivity;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Model.SendPhotoModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SendFileItemAdapter extends RecyclerView.Adapter<SendFileItemAdapter.ItemView> {

    private List<SendPhotoModel> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;
    private int selectCount = 0;
    public OnItemClickLinter onItemClickLinter;

    public SendFileItemAdapter(BaseFragmentActivity mainActivity, Context context, List<SendPhotoModel> dataSet) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_send_photos, viewGroup, false);
        return new ItemView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SendFileItemAdapter.ItemView holder, int position) {
        SendPhotoModel menuItemModel = dataSet.get(position);
        if (menuItemModel.isSelected()){
            holder.linearSelection.setVisibility(View.VISIBLE);
            holder.tvSelectionCount.setText(""+menuItemModel.getSelecetedCoun());

        }else {
            holder.linearSelection.setVisibility(View.GONE);

        }
        holder.imageProfile.setImageResource(menuItemModel.getImage());
    //Glide.with(mContext).load(Uri.fromFile(new File(menuItemModel))).into(holder.imageProfile);

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {
        ImageView imageProfile;
        LinearLayout linearSelection;
        TextView tvSelectionCount;


        private ItemView(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.image_view_photos);
            linearSelection = itemView.findViewById(R.id.layout_selection);
            tvSelectionCount = itemView.findViewById(R.id.tv_selection_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendPhotoModel sendPhotoModel = dataSet.get(getAdapterPosition());
                    if (sendPhotoModel.isSelected()) {
                        selectCount = selectCount-1;
                        sendPhotoModel.setSelected(false);
                        sendPhotoModel.setSelecetedCoun(selectCount);
                    }else {
                        selectCount = selectCount+1;
                        sendPhotoModel.setSelected(true);
                        sendPhotoModel.setSelecetedCoun(selectCount);
                    }
                    notifyDataSetChanged();
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
