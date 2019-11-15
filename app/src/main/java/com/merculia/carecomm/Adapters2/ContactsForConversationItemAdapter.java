package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.merculia.carecomm.Activities.DialCallMobileActivity;
import com.merculia.carecomm.Activities.DialVideoCallMobileActivity;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.MobileFragments.ChatRoomMobileFragment;
import com.merculia.carecomm.MobileFragments.ContactDetailMobileFrgment;
import com.merculia.carecomm.Logics.Contact.ContactForConversationModel;
import com.merculia.carecomm.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsForConversationItemAdapter extends RecyclerView.Adapter<ContactsForConversationItemAdapter.ItemView> {

    private List<ContactForConversationModel> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;
    public OnItemClickLinter onItemClickLinter;

    public ContactsForConversationItemAdapter(Context context, List<ContactForConversationModel> dataSet, BaseFragmentActivity mainActivity) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_select_contact, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ContactsForConversationItemAdapter.ItemView holder, int position) {
        ContactForConversationModel contact = dataSet.get(position);

        if (contact.userRef.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(holder.imageProfile);
        else
            Picasso.get().load(contact.userRef.photo).into(holder.imageProfile);
        holder.tvTitle.setText(contact.userRef.name);
        if (contact.isContacted){
            holder.layoutChat.setVisibility(View.GONE);
            holder.layoutContact.setVisibility(View.VISIBLE);
        } else {
            holder.layoutChat.setVisibility(View.VISIBLE);
            holder.layoutContact.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageProfile,ivChat,ivCallContact,ivVideoContact,ivChatContact;
        LinearLayout layoutChat,layoutContact;
        TextView tvTitle,tvChat;

        private ItemView(View itemView) {
            super(itemView);
            ivCallContact = itemView.findViewById(R.id.iv_call_contact);
            ivVideoContact = itemView.findViewById(R.id.iv_video_contact);
            ivChatContact = itemView.findViewById(R.id.iv_chat_contact);

            imageProfile = itemView.findViewById(R.id.profile_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvChat = itemView.findViewById(R.id.tv_chat);
            ivChat = itemView.findViewById(R.id.iv_chat);
            layoutContact = itemView.findViewById(R.id.layout_contact);
            layoutChat = itemView.findViewById(R.id.layout_chat);

            itemView.setOnClickListener(this);
            ivCallContact.setOnClickListener(this);
            ivChatContact.setOnClickListener(this);
            ivVideoContact.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ContactForConversationModel contact = dataSet.get(getAdapterPosition());
            CallData.partnerData._id = contact.userRef._id;
            CallData.partnerData.photo = contact.userRef.photo;
            CallData.partnerData.name = contact.userRef.name;
            CallData.partnerData.email = contact.userRef.email;
            CallData.partnerData.username = contact.userRef.username;
            CallData.partnerData.relationship = contact.relationship;

            if (view == itemView){
                if (contact.isContacted){
                    mainActivity.ReplaceFragmentWithBackstack(new ContactDetailMobileFrgment(),false,true);
                }else {
                    mainActivity.ReplaceFragmentWithBackstack(new ChatRoomMobileFragment(),false,true);
                }
            }

            if (view == ivCallContact){
                CallData.callingType = "audio";
                startCall();
            }

            if (view==ivVideoContact){
                CallData.callingType = "video";
                startCall();
            }
            if (view == ivChatContact){
                mainActivity.ReplaceFragmentWithBackstack(new ChatRoomMobileFragment(),false);
            }
        }

        private void startCall() {
            String token = Data.token;
            String responderRef = CallData.partnerData._id;
            Call<Void> startCall = ApiService.call.requestConnect(token, CallData.callingType, responderRef);
            startCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        CallData.callingStatus = "requesting";
                        if (CallData.callingType.contentEquals("video")) {
                            mainActivity.openActivity(mContext, DialVideoCallMobileActivity.class);
                        } else {
                            mainActivity.openActivity(mContext, DialCallMobileActivity.class);
                        }

                    } else {
                        try {
                            JSONObject errorsJson = new JSONObject(response.errorBody().string());
                            JSONArray errors = (JSONArray) errorsJson.get("errors");
                            for (int i=0; i<errors.length(); i++) {
                                String errorMsg = errors.getString(i);
                                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }
}
