package com.merculia.carecomm.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.R;

import com.merculia.carecomm.Logics.Auth.AuthRequestModel;
import com.merculia.carecomm.Logics.Auth.AuthResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener {

    private Button btnLogin, btnSignup, btnForgotPassword;
    private ImageView imgEyeIcon;
    private LinearLayout wifiConnect;

    private boolean isPasswordShow = false;
    private EditText edtUsername, edtPassword;

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
        setContentView(R.layout.activity_login);
        edtPassword = findViewById(R.id.usr_password);
        edtUsername = findViewById(R.id.user_name);
        setView();
        init();
        setEvents();
        ApiService.initApi();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setEvents() {
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
        wifiConnect.setOnClickListener(this);
        imgEyeIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isPasswordShow = isPasswordShow ? false : true;
                if (isPasswordShow) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    imgEyeIcon.setImageResource(R.mipmap.group_413);
                }else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgEyeIcon.setImageResource(R.mipmap.pw_shown_icon);
                }
                return false;
            }
        });

    }

    @Override
    protected void setView() {
        btnLogin = findViewById(R.id.btn_sign_in);
        btnSignup = findViewById(R.id.btn_register);
        btnForgotPassword = findViewById(R.id.btn_forget_password);
        wifiConnect = findViewById(R.id.connect_wifi);
        imgEyeIcon = findViewById(R.id.eye_icon);
        edtPassword = findViewById(R.id.usr_password);

    }

    @Override
    public void onClick(View view) {
        if (view == btnSignup){
            openActivity(context,RegistrationActivity.class);
        }

        if (view == btnLogin){
            tryLogin();
        }

        if (view == wifiConnect){
            openActivity(context,WifiConnectionActivity.class);
        }
        if (view == btnForgotPassword){
            openActivity(context,ForgetPasswordActivity.class);
        }
    }

    private void tryLogin() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        if(password.equals("")){
            Toast.makeText(getApplicationContext(),"Please insert Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(username.equals("")){
            Toast.makeText(getApplicationContext(),"Please insert username", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthRequestModel.UserModel user = new AuthRequestModel.UserModel();
        user.username = username;
        user.password = password;
        AuthRequestModel request = new AuthRequestModel();
        request.setUser(user);

        try {
            Call<AuthResponseModel> authCall = ApiService.auth.tryLogin(request);
            authCall.enqueue(new Callback<AuthResponseModel>() {
                @Override
                public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {
                    if (response.body() != null) {
                        AuthResponseModel.UserModel user = response.body().getUser();
                        Data.token = "Bearer "+user.token;
                        openActivity(context, MainActivity.class);
                    } else {
                        Toast.makeText(getApplicationContext(),"Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Login failed because of server problem.", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Login failed because of server problem.", Toast.LENGTH_SHORT).show();
        }
    }
}
