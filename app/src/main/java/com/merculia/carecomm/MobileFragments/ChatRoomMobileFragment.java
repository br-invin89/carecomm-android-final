package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.merculia.carecomm.Activities.DialCallMobileActivity;
import com.merculia.carecomm.Activities.DialVideoCallMobileActivity;
import com.merculia.carecomm.Adapters2.ChatItemsGroupedByDateAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.Logics.Chat.ChatGroupedItemModel;
import com.merculia.carecomm.Logics.Chat.GetChatHistoryResponseModel;
import com.merculia.carecomm.Logics.Chat.SendMessageRequestModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Model.ChatModel;
import com.merculia.carecomm.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatRoomMobileFragment extends BaseFragment implements View.OnClickListener {

    private List<ChatGroupedItemModel> dataChats;
    private CircleImageView ivPartnerPhoto;
    private TextView tvPartnerName;
    private EditText etMessage;
    private ImageView ivClose,ivVideo,ivCall,ivSendPics,ivCloseChatScreen;
    private RecyclerView rvChat,rvFiles;
    private ImageView ivSendMsg;
    private LinearLayout sendFileLayout, chatLayout;
    private TextView screenTitle;
    private LinearLayoutManager layoutManager;
    private ChatItemsGroupedByDateAdapter chatItemsAdapter;
    private Button btnShare;
    private Socket socket;
    private String Nickname ;
    private String myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_room_mobile, container, false);

        setView(view);
        inits();
        setEvents();
        loadData();
        createSocket();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setView(View view) {
        ivPartnerPhoto = view.findViewById(R.id.iv_partner_photo);
        tvPartnerName = view.findViewById(R.id.tv_partner_name);
        ivClose = view.findViewById(R.id.iv_close);
        ivVideo = view.findViewById(R.id.iv_video);
        ivCall = view.findViewById(R.id.iv_call);
        rvChat = view.findViewById(R.id.rv_chat);
        etMessage = view.findViewById(R.id.edtInput);
        ivSendMsg = view.findViewById(R.id.btnSendMessage);
        chatLayout = view.findViewById(R.id.layout_chat_room);
        sendFileLayout = view.findViewById(R.id.send_pics);
        screenTitle = view.findViewById(R.id.screen_title);
        ivSendPics = view.findViewById(R.id.iv_send_pics);
        ivCloseChatScreen = view.findViewById(R.id.iv_close_chat_screen);
        btnShare = view.findViewById(R.id.btn_share);
        rvFiles = view.findViewById(R.id.rv_files);
    }

    @Override
    protected void inits() {
        screenTitle.setText("Share Album");
        setScreen(false);
    }

    private void loadData() {
        tvPartnerName.setText(CallData.partnerData.name);
        if (CallData.partnerData.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivPartnerPhoto);
        else
            Picasso.get().load(CallData.partnerData.photo).into(ivPartnerPhoto);

        try {
            String token = Data.token;
            String partnerRef = CallData.partnerData._id;
            Call<GetChatHistoryResponseModel> getChatHistoryCall = ApiService.chat.getChatHistory(token, partnerRef);
            getChatHistoryCall.enqueue(new Callback<GetChatHistoryResponseModel>() {
                @Override
                public void onResponse(Call<GetChatHistoryResponseModel> call, Response<GetChatHistoryResponseModel> response) {
                    if (response.isSuccessful()) {
                        dataChats = response.body().logsGrouped;
                        myRef = response.body().myRef;
                        setAdapter();
                    }
                }

                @Override
                public void onFailure(Call<GetChatHistoryResponseModel> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }


    private void setScreen(boolean isOpenGallary){
        if (isOpenGallary){
            chatLayout.setVisibility(View.GONE);
            sendFileLayout.setVisibility(View.VISIBLE);
        }else {
            chatLayout.setVisibility(View.VISIBLE);
            sendFileLayout.setVisibility(View.GONE);
        }
    }
    private void setAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity(), LinearLayoutManager.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(layoutManager);
        chatItemsAdapter = new ChatItemsGroupedByDateAdapter(context, dataChats);

        rvChat.setAdapter(chatItemsAdapter);
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        ivVideo.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        ivSendMsg.setOnClickListener(this);
        ivSendPics.setOnClickListener(this);
        ivCloseChatScreen.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    private void sendMessage(){
        if (!TextUtils.isEmpty(etMessage.getText().toString())) {
            String partnerRef = CallData.partnerData._id;
            String token = Data.token;
            SendMessageRequestModel request = new SendMessageRequestModel();
            request.messageText = etMessage.getText().toString();
            Call<Void> sendCall = ApiService.chat.sendMessage(token, partnerRef, request);
            sendCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    socket.emit("new message", myRef, partnerRef);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }

    private void createSocket() {
        try {
            socket = IO.socket("http://3.10.9.30:3030");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on("new message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String requesterRef = (String)args[0];
                String responderRef = (String)args[1];
                if ((requesterRef.contentEquals(myRef) && responderRef.contentEquals(CallData.partnerData._id)) ||
                (requesterRef.contentEquals(CallData.partnerData._id) && responderRef.contentEquals(myRef))) {
                    try {
                        String token = Data.token;
                        String partnerRef = CallData.partnerData._id;
                        Call<GetChatHistoryResponseModel> getChatHistoryCall = ApiService.chat.getChatHistory(token, partnerRef);
                        getChatHistoryCall.enqueue(new Callback<GetChatHistoryResponseModel>() {
                            @Override
                            public void onResponse(Call<GetChatHistoryResponseModel> call, Response<GetChatHistoryResponseModel> response) {
                                if (response.isSuccessful()) {
                                    dataChats.clear();
                                    dataChats.addAll(response.body().logsGrouped);
                                    chatItemsAdapter.notifyDataSetChanged();

                                    layoutManager.scrollToPosition(dataChats.size()-1);
                                    etMessage.setText("");
                                }
                            }

                            @Override
                            public void onFailure(Call<GetChatHistoryResponseModel> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view== ivClose){
           setScreen(false);
        }
        if (view== ivVideo){
            CallData.callingType = "video";
            startCall();
        }
        if (view== ivCall){
            CallData.callingType = "audio";
            startCall();
        }
        if (view == ivSendMsg){
            sendMessage();
        }
        if (view == ivCloseChatScreen){
            onBack();
        }
        if (view == ivSendPics){
            // setAdapterPictures();
            setScreen(true);

        }
        if (view == btnShare){
            setScreen(false);
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
