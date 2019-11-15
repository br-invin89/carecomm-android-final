package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.merculia.carecomm.Activities.DialVideoCallActivity;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;


public class ContactDetailFrgment extends BaseFragment implements View.OnClickListener {

    private ImageView ivClose;
    private Button btnVoiceCall,btnVideoCall,btnChat;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //isMinimize = getArguments().getBoolean(Constants.isMinimize);
//            txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);

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


        ivClose = view.findViewById(R.id.iv_close);
        btnVoiceCall = view.findViewById(R.id.btn_voice_call);
        btnVideoCall = view.findViewById(R.id.btn_video_call);
        btnChat = view.findViewById(R.id.btn_chat);



    }

    @Override
    protected void inits() {



        //Glide.with(context).load(R.mipmap.make_call_btn).load(ivCallBtn);
    }

    @Override
    protected void setEvents() {

        ivClose.setOnClickListener(this);
        btnVoiceCall.setOnClickListener(this);
        btnChat.setOnClickListener(this);
        btnVideoCall.setOnClickListener(this);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }








    @Override
    public void onClick(View view) {


        if (view == ivClose){
            getMainActivity().onBackPressed();
        }
        if (view == btnVideoCall){
            getMainActivity().openActivity(context, DialVideoCallActivity.class);
        }
        if (view == btnChat){
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.isMinimize,false);
            getMainActivity().ReplaceFragmentWithBackstack(new ChatRoomFrgment(),bundle);
        }
        if (view == btnVoiceCall){
            getMainActivity().ReplaceFragmentWithBackstack(new DialCallFrgment(), false, true);
        }


    }
}
