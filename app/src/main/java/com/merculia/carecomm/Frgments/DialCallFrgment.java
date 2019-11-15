package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;


public class DialCallFrgment extends BaseFragment implements View.OnClickListener {

    private Button btnCancel,btnMinimize;
    private SeekBar sbVolume;
    private ImageView ivMute,ivSpeaker,ivVolume;
    private TextView tvMute,tvSpeaker,tvVolume,tvTile,tvCalling;
    private CircleImageView ivProfile;

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
        View view = inflater.inflate(R.layout.fragment_dial_call, container, false);

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
        ivMute = view.findViewById(R.id.iv_mute);
        ivProfile=view.findViewById(R.id.iv_profile);
        ivSpeaker = view.findViewById(R.id.iv_speaker);
        ivVolume = view.findViewById(R.id.iv_volume);

        btnCancel= view.findViewById(R.id.btn_cancel);
        btnMinimize = view.findViewById(R.id.btn_minimize);
        sbVolume = view.findViewById(R.id.sb_volume);

         tvMute = view.findViewById(R.id.txt_mute);
        tvSpeaker = view.findViewById(R.id.tv_speaker);
        tvVolume = view.findViewById(R.id.txt_volume);
        tvTile = view.findViewById(R.id.tv_title);
        tvCalling = view.findViewById(R.id.tv_calling);
    }

    @Override
    protected void inits() {

        //Glide.with(context).load(R.mipmap.make_call_btn).load(ivCallBtn);
    }

    @Override
    protected void setEvents() {
        btnCancel.setOnClickListener(this);
        btnMinimize.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }



    @Override
    public void onClick(View view) {
        if (view == btnCancel){
            getMainActivity().onBackPressed();
        }
        if (view == btnMinimize){
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.isMinimize,true);
            getMainActivity().ReplaceFragmentWithBackstack(new ChatRoomFrgment(),bundle);
        }
    }
}
