package com.merculia.carecomm.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.RegistrationFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Auth.CheckConfirmCodeRequestModel;
import com.merculia.carecomm.Logics.Auth.CheckConfirmCodeResponseModel;
import com.merculia.carecomm.Logics.Auth.ForgotPasswordRequestModel;
import com.merculia.carecomm.Logics.Auth.ForgotPasswordResponseModel;
import com.merculia.carecomm.Logics.Auth.ResetPasswordRequestModel;
import com.merculia.carecomm.Logics.Auth.ResetPasswordResponseModel;
import com.merculia.carecomm.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ImageView ivClose;
    private TextView screenTitle;
    private EditText tvEdit;
    private Button btnNext;
    int screenCount = 1;
    String strEmail = ""; String strPassword = ""; String strConfirmCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBottomNavigationBar();
        setTheme();
        if (isTablet(context)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_forget_password);

        setView();
        init();
        setEvents();
    }

    @Override
    protected void init() {
        screenTitle.setText("Find Password");

        Bundle bundle = new Bundle();
        bundle.putString(Constants.txtTitleString,"Enter Your E-mail Address");
        bundle.putString(Constants.txtValueString,"E-Mail Address");
        bundle.putInt(Constants.inputType, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ReplaceFragment(new RegistrationFrgment(),true, bundle);
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    protected void setView() {
        ivClose = findViewById(R.id.iv_close);
        screenTitle = findViewById(R.id.screen_title);
        btnNext = findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            finish();
        }
        if (view == btnNext){
            if(screenCount==1){
                tvEdit = findViewById(R.id.et_input_data);
                strEmail = tvEdit.getText().toString();

                if( strEmail.equals("")){
                    showDialog("Please insert email address.","Ok","");
                    return;
                }else if(!(strEmail.contains("@gmail.com") || strEmail.contains("@email.com"))){
                    showDialog("Please input correct email address.","Ok","");
                    return;
                }
                forgotPassword(strEmail);

                return;
            } else if (screenCount == 2){
                tvEdit = findViewById(R.id.et_input_data);
                strConfirmCode = tvEdit.getText().toString();
                if( strConfirmCode.equals("")) {
                    showDialog("Please insert confirm code.", "Ok", "");
                    return;
                }
                else {
                    checkConfirmCode(strConfirmCode, strEmail);
                }
            } else if(screenCount == 3){
                tvEdit = findViewById(R.id.et_input_data);
                strPassword = tvEdit.getText().toString();
                if(strPassword.contentEquals("")){
                    showDialog("Please Insert New Password", "Ok", "");
                    return;
                }else {
                    screenCount++;
                    Bundle bundle = new Bundle();
                    screenTitle.setText("Set Password");
                    bundle.putString(Constants.txtTitleString,"Confirm New Password");
                    bundle.putString(Constants.txtValueString,"Confirm password");
                    bundle.putInt(Constants.inputType, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnNext.setText("Done");
                    ReplaceFragment(new RegistrationFrgment(),true,bundle);
                }
            } else if(screenCount == 4){
                tvEdit = findViewById(R.id.et_input_data);
                String confirmPassword = tvEdit.getText().toString();

                if(confirmPassword.equals("")){
                    showDialog("Please Insert New Password", "Ok", "");
                    return;
                }

                if(!confirmPassword.contentEquals(strPassword)) {
                    showDialog("Confirm password is mismatching with password.", "OK", "");
                    return;
                }
                resetPassword(strEmail, strPassword);
            }
        }
    }

    private void forgotPassword(String email) {
        try {
            ForgotPasswordRequestModel request = new ForgotPasswordRequestModel();
            request.email = email;
            Call<ForgotPasswordResponseModel> forgotPasswordCall = ApiService.forgotPassword.forgotPassword(request);
            forgotPasswordCall.enqueue(new Callback<ForgotPasswordResponseModel>() {
                @Override
                public void onResponse(Call<ForgotPasswordResponseModel> call, Response<ForgotPasswordResponseModel> response) {
                    if (response.body() != null) {
                        String confirmCode = response.body().confirmCode;

                        showDialog("The codes have been sent to your e-mail", "OK" ,"");
                        SmsManager smgr = SmsManager.getDefault();
                        // smgr.sendTextMessage("8617624152773", null, "Test Message", null, null);

                        screenCount++;
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.txtTitleString, "Enter Codes");
                        bundle.putString(Constants.txtValueString, "Codes");
                        bundle.putInt(Constants.inputType, InputType.TYPE_CLASS_TEXT);
                        btnNext.setText("Enter");
                        ReplaceFragment(new RegistrationFrgment(),true, bundle);
                    } else {
                        showDialog("Email is not correct.", "OK", "");
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordResponseModel> call, Throwable t) {

                }
            });
        } catch (Exception e) {
        }
    }
    private void checkConfirmCode(String confirmCode, String email) {
        try {
            CheckConfirmCodeRequestModel request = new CheckConfirmCodeRequestModel();
            request.confirmCode = confirmCode;
            request.email = email;
            Call<CheckConfirmCodeResponseModel> checkConfirmCodeCall = ApiService.forgotPassword.checkConfirmCode(request);
            checkConfirmCodeCall.enqueue(new Callback<CheckConfirmCodeResponseModel>() {
                @Override
                public void onResponse(Call<CheckConfirmCodeResponseModel> call, Response<CheckConfirmCodeResponseModel> response) {
                    if (response.body() != null) {
                        screenCount++;
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.txtTitleString, "Enter New Password");
                        bundle.putString(Constants.txtValueString, "New Password");
                        bundle.putInt(Constants.inputType, InputType.TYPE_CLASS_TEXT  |  InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        btnNext.setText("Next");
                        ReplaceFragment(new RegistrationFrgment(),true, bundle);
                    } else {
                        showDialog("Your confirm code is wrong.", "OK", "");
                    }
                }

                @Override
                public void onFailure(Call<CheckConfirmCodeResponseModel> call, Throwable t) {

                }
            });
        } catch(Exception e) {
        }
    }
    private void resetPassword(String email , String password) {
        ResetPasswordRequestModel request = new ResetPasswordRequestModel();
        request.email = email;
        request.password = password;
        Call<ResetPasswordResponseModel> resetPasswordCall = ApiService.forgotPassword.resetPassword(request);
        resetPasswordCall.enqueue(new Callback<ResetPasswordResponseModel>() {
            @Override
            public void onResponse(Call<ResetPasswordResponseModel> call, Response<ResetPasswordResponseModel> response) {
                if (response.body() != null) {
                    showDialog("Password is reset successfully.", "Ok", "");
                } else {
                    showDialog("Password reset failed.", "Ok", "");
                }
                openActivity(context, LoginActivity.class);
            }

            @Override
            public void onFailure(Call<ResetPasswordResponseModel> call, Throwable t) {

            }
        });
    }

}
