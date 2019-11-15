package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import com.merculia.carecomm.Utils.StaticMethods;


public class CameraFrgment extends BaseFragment implements View.OnClickListener {

    private LinearLayout btnCamera,btnVideo,btnAlbum;
    private TextView screenTitle;
    private ImageView ivBack,ivClose;
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
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

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
        btnAlbum = view.findViewById(R.id.btn_album);
        btnCamera = view.findViewById(R.id.btn_camera);
        btnVideo = view.findViewById(R.id.btn_video);
        if (!getMainActivity().isTablet(context)){
            screenTitle = view.findViewById(R.id.screen_title);
            ivBack = view.findViewById(R.id.iv_back);
            ivClose = view.findViewById(R.id.iv_close);
        }

    }


    @Override
    protected void inits() {
        if (!getMainActivity().isTablet(context)){
            screenTitle.setText("Camera");
            ivBack.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setEvents() {
    btnVideo.setOnClickListener(this);
    btnCamera.setOnClickListener(this);
    btnAlbum.setOnClickListener(this);
        if (!getMainActivity().isTablet(context)){

            ivBack.setOnClickListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        if (view == btnCamera){

            bundle.putBoolean(Constants.isVideoRecording,false);
            getMainActivity().ReplaceFragmentWithBackstack(new OpenCamerFrgment(),bundle);
        }
        if (view==btnVideo){
            bundle.putBoolean(Constants.isVideoRecording,true);
            getMainActivity().ReplaceFragmentWithBackstack(new OpenCamerFrgment(),bundle);
        }

        if (view == btnAlbum){
            if (getMainActivity().isTablet(context)) {
                getMainActivity().ReplaceFragmentWithBackstack(new GallaryFrgment());
            }else {
                getMainActivity().ReplaceFragmentWithBackstack(new PhotosFrgment());
            }
        }
        if (view == ivBack){
            openHome();
        }
    }
}
