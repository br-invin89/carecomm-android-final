package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.merculia.carecomm.Activities.DialCallMobileActivity;
import com.merculia.carecomm.Activities.DialVideoCallMobileActivity;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Contact.ContactModel;
import com.merculia.carecomm.Logics.Data;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactDetailMobileFrgment extends BaseFragment implements View.OnClickListener {

    private ImageView ivClose,ivChat,ivCall,ivVideoCall;
    private Button btnDelete;

    private CircleImageView ivMyPhoto;
    private TextView tvMyName, tvMyEmail, tvMyUsername, tvMyRelationship;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            txtTitleString = getArguments().getString(Constants.txtTitleString);
//            txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_detail_mobile, container, false);

        setView(view);
        inits();
        setEvents();
        initViewWithData();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void setView(View view) {
        ivClose = view.findViewById(R.id.iv_close);
        ivChat = view.findViewById(R.id.iv_chat);
        ivVideoCall = view.findViewById(R.id.iv_video_call);
        ivCall = view.findViewById(R.id.iv_call);
        btnDelete = view.findViewById(R.id.btn_delete);

        ivMyPhoto = view.findViewById(R.id.img_my_photo);
        tvMyName = view.findViewById(R.id.txt_my_name);
        tvMyEmail = view.findViewById(R.id.txt_my_email);
        tvMyUsername = view.findViewById(R.id.txt_my_username);
        tvMyRelationship = view.findViewById(R.id.txt_my_relationship);
    }


    @Override
    protected void inits() {
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        ivChat.setOnClickListener(this);
        ivVideoCall.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            onBack();
        }
        if (view == btnDelete){
            removeContact();
        }
        if (view == ivCall){
            CallData.callingType = "audio";
            startCall();
        }

        if (view==ivVideoCall){
            CallData.callingType = "video";
            startCall();
        }
        if (view == ivChat){
            CallData.partnerData._id = Data.selectedContact.userRef._id;
            CallData.partnerData.photo = Data.selectedContact.userRef.photo;
            CallData.partnerData.name = Data.selectedContact.userRef.name;
            CallData.partnerData.email = Data.selectedContact.userRef.email;
            CallData.partnerData.username = Data.selectedContact.userRef.username;
            CallData.partnerData.relationship = Data.selectedContact.relationship;
            getMainActivity().ReplaceFragmentWithBackstack(new ChatRoomMobileFragment(),false);
        }
    }

    private void initViewWithData() {
        ContactModel data = Data.selectedContact;
        if (data.userRef.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivMyPhoto);
        else
            Picasso.get().load(data.userRef.photo).into(ivMyPhoto);
        tvMyName.setText(data.userRef.name);
        tvMyUsername.setText(data.userRef.username);
        tvMyEmail.setText(data.userRef.email);
        tvMyRelationship.setText(data.relationship);
    }

    private void removeContact() {
        ContactModel data = Data.selectedContact;
        String userId = data.userRef._id;
        String token = Data.token;
        Call<Void> removeCall = ApiService.contact.removeContact(token, userId);
        removeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onBack();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void startCall() {
        String token = Data.token;
        String responderRef = Data.selectedContact.userRef._id;
        Call<Void> startCall = ApiService.call.requestConnect(token, CallData.callingType, responderRef);
        startCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    CallData.callingStatus = "requesting";
                    CallData.partnerData._id = Data.selectedContact.userRef._id;
                    CallData.partnerData.name = Data.selectedContact.userRef.name;
                    CallData.partnerData.username = Data.selectedContact.userRef.username;
                    CallData.partnerData.email = Data.selectedContact.userRef.email;
                    CallData.partnerData.photo = Data.selectedContact.userRef.photo;
                    CallData.partnerData.relationship = Data.selectedContact.relationship;
                    if (CallData.callingType.contentEquals("video")) {
                        getMainActivity().openActivity(context, DialVideoCallMobileActivity.class);
                    } else {
                        getMainActivity().openActivity(context, DialCallMobileActivity.class);
                    }

                } else {
                    try {
                        JSONObject errorsJson = new JSONObject(response.errorBody().string());
                        JSONArray errors = (JSONArray) errorsJson.get("errors");
                        for (int i=0; i<errors.length(); i++) {
                            String errorMsg = errors.getString(i);
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
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
