package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Profile.ChangePasswordRequestModel;
import com.merculia.carecomm.Logics.Profile.ChangePasswordResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangPasswordFrgment extends BaseFragment implements View.OnClickListener {
    private ImageView ivClose;
    private TextView screenTitle;
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnDone;

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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

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
        screenTitle = view.findViewById(R.id.screen_title);
        etOldPassword = view.findViewById(R.id.et_old_pass);
        etNewPassword = view.findViewById(R.id.et_new_pass);
        etConfirmPassword = view.findViewById(R.id.et_confirm_pass);
        btnDone = view.findViewById(R.id.btn_done);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    protected void inits() {

        screenTitle.setText("Change Password");

        //Glide.with(context).load(R.mipmap.make_call_btn).load(ivCallBtn);
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnDone.setOnClickListener(this);
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

        if (view == btnDone) {
            changePassword();
        }

    }

    private void changePassword() {
        if ((etOldPassword.getText().toString()).contentEquals("")) {
            Toast.makeText(context, "Old password can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((etNewPassword.getText().toString()).contentEquals("")) {
            Toast.makeText(context, "New password can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((etConfirmPassword.getText().toString()).contentEquals("")) {
            Toast.makeText(context, "Confirm password can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(etNewPassword.getText().toString()).contentEquals(etConfirmPassword.getText().toString())) {
            Toast.makeText(context, "Password is not matching", Toast.LENGTH_SHORT).show();
            return;
        }
        ChangePasswordRequestModel request = new ChangePasswordRequestModel();
        ChangePasswordRequestModel.UserModel user = new ChangePasswordRequestModel.UserModel();
        request.user = user;
        user.password = etOldPassword.getText().toString();
        user.newPassword = etNewPassword.getText().toString();
        String token = Data.token;
        try {
            Call<ChangePasswordResponseModel> changePasswordCall = ApiService.profile.changePassword(token, request);
            changePasswordCall.enqueue(new Callback<ChangePasswordResponseModel>() {
                @Override
                public void onResponse(Call<ChangePasswordResponseModel> call, Response<ChangePasswordResponseModel> response) {
                    if (response.body() != null) {
                        Toast.makeText(context, "Password has changed.", Toast.LENGTH_SHORT).show();
                        getMainActivity().ReplaceFragmentWithBackstack(new EditSettingFrgment(),false,true);
                    }
                }

                @Override
                public void onFailure(Call<ChangePasswordResponseModel> call, Throwable t) {

                }
            });
        } catch ( Exception e) {

        }
    }
}
