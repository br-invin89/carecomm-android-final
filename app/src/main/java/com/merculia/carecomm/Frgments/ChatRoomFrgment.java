package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.merculia.carecomm.Activities.DialVideoCallActivity;
import com.merculia.carecomm.Adapters2.ChatLogsAdapter;
import com.merculia.carecomm.Adapters.SendFileItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.ChatModel;
import com.merculia.carecomm.Model.SendPhotoModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import java.util.ArrayList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ChatRoomFrgment extends BaseFragment implements View.OnClickListener {


    private boolean isMinimize = false;
    private RecyclerView rvFiles,rvChat;
    private LinearLayout layoutBackCallScreen;
    private ImageView ivClose,ivProfilePic,ivSendPics,ivSendVideo;
    private TextView tvUserName;
    private Button btnVoiceCall,btnVideoCall,btnCancel,btnSend,btnSendMessage;
    private RelativeLayout relativeChatRoom;
    private FrameLayout linearSendFile;
    private ChatLogsAdapter chatLogsAdapter;
    private ArrayList<ChatModel> messageList;
    private EditText etMessage;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager layoutManager;
    private ImageView ivScrollUp,ivScrollDown;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isMinimize = getArguments().getBoolean(Constants.isMinimize);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_room, container, false);

        setView(view);
        inits();
        setEvents();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void setView(View view) {
        layoutBackCallScreen = view.findViewById(R.id.layout_backCallScreen);

        ivClose = view.findViewById(R.id.iv_close);
        ivProfilePic = view.findViewById(R.id.iv_profile);
        btnVideoCall = view.findViewById(R.id.btn_video_call);
        btnVoiceCall = view.findViewById(R.id.btn_voice_call);
        tvUserName = view.findViewById(R.id.tv_user_name);
        ivSendPics = view.findViewById(R.id.iv_send_pics);
        ivSendVideo = view.findViewById(R.id.iv_send_video);
        relativeChatRoom = view.findViewById(R.id.relative_chat_room);
        linearSendFile = view.findViewById(R.id.linear_send_file);
        btnSend = view.findViewById(R.id.btn_send);
        btnCancel = view.findViewById(R.id.btn_cancel);
        rvChat = view.findViewById(R.id.rv_chat);
        rvFiles = view.findViewById(R.id.rv_files);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);
        etMessage = view.findViewById(R.id.edtInput);
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);
        setScreen(false);
    }

    @Override
    protected void inits() {

        if (isMinimize){
            layoutBackCallScreen.setVisibility(View.VISIBLE);
        }
        setChatAdapoter();
        //Glide.with(context).load(R.mipmap.make_call_btn).load(ivCallBtn);
    }

    @Override
    protected void setEvents() {
        layoutBackCallScreen.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnVoiceCall.setOnClickListener(this);
        btnVideoCall.setOnClickListener(this);
        ivSendVideo.setOnClickListener(this);
        ivSendPics.setOnClickListener(this);
        btnSendMessage.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        ivScrollUp.setOnClickListener(this);
        ivScrollDown.setOnClickListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    private void setScreen(boolean isOpenGallary){

        if (isOpenGallary){
            relativeChatRoom.setVisibility(View.GONE);
            linearSendFile.setVisibility(View.VISIBLE);
        }else {
            relativeChatRoom.setVisibility(View.VISIBLE);
            linearSendFile.setVisibility(View.GONE);
        }
    }

    private void setChatAdapoter(){

        /*
        messageList = new ArrayList();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(R.drawable.main_photo_1);
        integerArrayList.add(R.drawable.main_photo_2);
        integerArrayList.add(R.drawable.main_photo_3);
        integerArrayList.add(R.drawable.main_photo_4);
        linearLayoutManager = new LinearLayoutManager(getMainActivity(),
                LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(linearLayoutManager);

        messageList.add(new ChatModel("this is my message ...How are you I am waiting for you what are you doing " , 0,null));
        messageList.add(new ChatModel("this is your message ... " , 1,null));
        messageList.add(new ChatModel("this is my message ... " , 0,null));
        messageList.add(new ChatModel("this is your message ... " , 1,null));
        messageList.add(new ChatModel("",2,integerArrayList));
        chatLogsAdapter = new ChatLogsAdapter(messageList,context);
        rvChat.setAdapter(chatLogsAdapter);

         */
    }


    private void setAdapterPictures(){
        layoutManager = new GridLayoutManager(getMainActivity(),3);
        rvFiles.setLayoutManager(layoutManager);
        ArrayList<SendPhotoModel> stringArrayList = new ArrayList<>();
        stringArrayList.add(new SendPhotoModel(R.drawable.main_photo_1,false));
        stringArrayList.add(new SendPhotoModel(R.drawable.main_photo_2,false));
        stringArrayList.add(new SendPhotoModel(R.drawable.main_photo_3,false));
        stringArrayList.add(new SendPhotoModel(R.drawable.main_photo_4,false));
        stringArrayList.add(new SendPhotoModel(R.drawable.main_photo_5,false));
        stringArrayList.add(new SendPhotoModel(R.drawable.main_photo_6,false));
        SendFileItemAdapter menuItemAdapter = new SendFileItemAdapter(getMainActivity(),context,stringArrayList);

        rvFiles.setAdapter(menuItemAdapter);
    }

    private void sendMessage(){
        if (!TextUtils.isEmpty(etMessage.getText().toString())) {
            messageList.add(new ChatModel(etMessage.getText().toString(), 0,null));

            chatLogsAdapter.notifyDataSetChanged();
            linearLayoutManager.scrollToPosition(messageList.size()-1);
            etMessage.setText("");
        }

    }

    @Override
    public void onClick(View view) {
        if (view == layoutBackCallScreen){
            getMainActivity().onBackPressed();
        }
        if (view == ivSendPics){
            setAdapterPictures();
            setScreen(true);
        }
        if (view == ivSendVideo){
            setAdapterPictures();
            setScreen(true);
        }
        if (view == ivClose){
            getMainActivity().onBackPressed();
        }
        if (view == btnVideoCall){
            getMainActivity().openActivity(context, DialVideoCallActivity.class);
        }
        if (view == btnVoiceCall){
            getMainActivity().ReplaceFragmentWithBackstack(new DialCallFrgment());
        }

        if (view == btnCancel){
            setScreen(false);
        }
        if (view == btnSend){
            setScreen(false);
        }

        if (view == btnSendMessage){
            sendMessage();

        }

        if (view == ivScrollUp){
            int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemIndex > 0) {
                layoutManager.smoothScrollToPosition(rvFiles,null,firstVisibleItemIndex-1);
            }
        }

        if (view == ivScrollDown){
            int totalItemCount = rvFiles.getAdapter().getItemCount();
            if (totalItemCount <= 0) return;
            int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();

            if (lastVisibleItemIndex >= totalItemCount) return;
            layoutManager.smoothScrollToPosition(rvFiles,null,lastVisibleItemIndex+1);

        }

    }
}
