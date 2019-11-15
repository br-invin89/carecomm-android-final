package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Frgments.EditSettingFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Profile.GetInfoResponseModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingMobileFrgment extends BaseFragment implements View.OnClickListener {

    private ImageView ivClose,ivBack;
    private Button btnEdit,btnSignout;
    private TextView screenTitle;
    private CircleImageView ivMyPhoto;
    private TextView tvMyName;
    private TextView tvMyUsername;
    private TextView tvMyEmail;


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
        View view = inflater.inflate(R.layout.fragment_mobile_settings, container, false);

        setView(view);
        inits();
        setEvents();
        loadData();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setView(View view) {
        ivClose = view.findViewById(R.id.iv_close);
        screenTitle = view.findViewById(R.id.screen_title);
        ivBack = view.findViewById(R.id.iv_back);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnSignout = view.findViewById(R.id.btn_signout);
        ivMyPhoto = view.findViewById(R.id.img_my_photo);
        tvMyName = view.findViewById(R.id.txt_my_name);
        tvMyUsername = view.findViewById(R.id.txt_my_username);
        tvMyEmail = view.findViewById(R.id.txt_my_email);
    }


    @Override
    protected void inits() {
        screenTitle.setText("Settings");
        ivClose.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setEvents() {
        ivBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSignout.setOnClickListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
      if (view == ivBack){
          openHome();
      }
      if (view == btnEdit){
          getMainActivity().ReplaceFragmentWithBackstack(new EditSettingFrgment(),false,true);
      }
      if (view == btnSignout){
          logoutvalidation();
      }
    }

    private void loadData() {
        try {
            String token = Data.token;
            Call<GetInfoResponseModel> getInfoCall = ApiService.profile.getInfo(token);
            getInfoCall.enqueue(new Callback<GetInfoResponseModel>() {
                @Override
                public void onResponse(Call<GetInfoResponseModel> call, Response<GetInfoResponseModel> response) {
                    if (response.body() != null) {
                        Data.myUserData = response.body().getUser();
                        if (Data.myUserData.photo.contentEquals("")) {
                            Data.myUserData.photo = "https://static.productionready.io/images/smiley-cyrus.jpg";
                            initViewWithData();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetInfoResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }

    private void initViewWithData() {
        Picasso.get().load(Data.myUserData.photo).into(ivMyPhoto);
        tvMyName.setText(Data.myUserData.name);
        tvMyUsername.setText(Data.myUserData.username);
        tvMyEmail.setText(Data.myUserData.email);
    }
}
