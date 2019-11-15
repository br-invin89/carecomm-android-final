package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.Logics.Chat.GetConversationsResponseModel;
import com.merculia.carecomm.Logics.Contact.ContactForConversationModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.MobileFragments.ChatRoomMobileFragment;
import com.merculia.carecomm.MobileFragments.UpcomingEventFragment;
import com.merculia.carecomm.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConversationItemAdapter extends RecyclerView.Adapter<ConversationItemAdapter.ItemView> {

    private List<GetConversationsResponseModel.ConversationModel> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;
    public OnItemClickLinter onItemClickLinter;

    public ConversationItemAdapter(Context context, List<GetConversationsResponseModel.ConversationModel> dataSet, BaseFragmentActivity mainActivity) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_conversation, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ConversationItemAdapter.ItemView holder, int position) {
        GetConversationsResponseModel.ConversationModel conversation = dataSet.get(position);

        if (conversation.relative.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(holder.ivPartnerPhoto);
        else
            Picasso.get().load(conversation.relative.photo).into(holder.ivPartnerPhoto);

        holder.tvPartnerName.setText(conversation.relative.name);
        holder.tvMessageText.setText(conversation.message.messageText);
        holder.tvMessageWhen.setText(conversation.message.when);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPartnerPhoto;
        TextView tvPartnerName, tvMessageText,tvCallTime, tvMessageWhen;

        private ItemView(View itemView) {
            super(itemView);
            ivPartnerPhoto = itemView.findViewById(R.id.iv_partner_photo);
            tvPartnerName = itemView.findViewById(R.id.tv_partner_name);
            tvMessageText = itemView.findViewById(R.id.tv_message_text);
            tvMessageWhen = itemView.findViewById(R.id.tv_message_when);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == itemView){
                GetConversationsResponseModel.ConversationModel contact = dataSet.get(getAdapterPosition());
                CallData.partnerData._id = contact.relative._id;
                CallData.partnerData.photo = contact.relative.photo;
                CallData.partnerData.name = contact.relative.name;
                CallData.partnerData.email = contact.relative.email;
                CallData.partnerData.username = contact.relative.username;
                CallData.partnerData.relationship = contact.relative.relationship;
                mainActivity.ReplaceFragmentWithBackstack(new ChatRoomMobileFragment(),false,true);
            }
        }
    }
}
