package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merculia.carecomm.Activities.DialVideoCallActivity;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.ChatRoomFrgment;
import com.merculia.carecomm.Frgments.ContactDetailFrgment;
import com.merculia.carecomm.Frgments.DialCallFrgment;
import com.merculia.carecomm.Model.ContactModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ContactItemAdapter extends RecyclerView.Adapter<ContactItemAdapter.ItemView> {

    private List<ContactModel> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;

    public OnItemClickLinter onItemClickLinter;

    public ContactItemAdapter(BaseFragmentActivity mainActivity,Context context, List<ContactModel> dataSet) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_contact, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ContactItemAdapter.ItemView holder, int position) {
        ContactModel menuItemModel = dataSet.get(position);

        Glide.with(mContext).load(menuItemModel.getImage()).into(holder.imageProfile);
        holder.tvTitle.setText(menuItemModel.getTitle());
        holder.btnView.setText(menuItemModel.getButtonTitle());
        if (!holder.btnView.getText().toString().equalsIgnoreCase("View")) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(10, 10, 5, 10);
            holder.cardView.requestLayout();
        }

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageProfile;
        TextView tvTitle;
        Button btnView;
        CardView cardView;

        private ItemView(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.profile_image);
            cardView = itemView.findViewById(R.id.cardView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            btnView = itemView.findViewById(R.id.btn_view);

            btnView.setOnClickListener(this);


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

        @Override
        public void onClick(View view) {
            if (view == btnView){
                if (dataSet.get(getAdapterPosition()).getButtonTitle().equalsIgnoreCase("Chat")){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.isMinimize,false);
                    mainActivity.ReplaceFragmentWithBackstack(new ChatRoomFrgment(),bundle);
                }else if (dataSet.get(getAdapterPosition()).getButtonTitle().equalsIgnoreCase("Call")){
                    mainActivity.ReplaceFragmentWithBackstack(new DialCallFrgment(), false, true);
                }else if (dataSet.get(getAdapterPosition()).getButtonTitle().equalsIgnoreCase("Video Call")){
                    mainActivity.openActivity(mContext, DialVideoCallActivity.class);
                }else if (dataSet.get(getAdapterPosition()).getButtonTitle().equalsIgnoreCase("View")){
                    mainActivity.ReplaceFragmentWithBackstack(new ContactDetailFrgment(), false, true);
                }
            }
        }
    }
}
