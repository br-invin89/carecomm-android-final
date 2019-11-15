package com.merculia.carecomm.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.RegistrationFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Auth.SignupRequestModel;
import com.merculia.carecomm.Logics.Auth.SignupResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageView ivClose;
    private Button btnNext;
    private EditText tvEdit;
    int screenCount = 1;
    Bundle bundle;
    SignupRequestModel.UserModel dataUser = new SignupRequestModel.UserModel();

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
        setContentView(R.layout.activity_registration);
        setView();
        init();
        setEvents();
    }

    @Override
    protected void init() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.txtTitleString,"Enter your Name");
        bundle.putString(Constants.txtValueString,"Full Name");
        bundle.putInt(Constants.inputType, InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        ReplaceFragment(new RegistrationFrgment(),true,bundle);
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    protected void setView() {
        ivClose = findViewById(R.id.iv_close);
        btnNext = findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            finish();
        }
        if (view == btnNext){
            if (screenCount<=5){
                tvEdit = findViewById(R.id.et_input_data);
                bundle = new Bundle();
                switch (screenCount){
                    case 1:
                        if((tvEdit.getText().toString()).equals("")){
                            showDialog("Please insert your name!" , " OK","");
                            return;
                        }
                        screenCount++;
                        dataUser.name = tvEdit.getText().toString();
                        bundle.putString(Constants.txtTitleString,"Create your E-Mail Address");
                        bundle.putString(Constants.txtValueString,"E-Mail Address");
                        bundle.putInt(Constants.inputType, InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        break;
                    case 2:
                        if((tvEdit.getText().toString()).equals("")){
                            showDialog("Please insert your email!" , " OK","");
                            return;
                        }
                        if(!(tvEdit.getText().toString().matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"))){
                            showDialog("Please insert correct Email!" , " OK","");
                            return;
                        }

                        screenCount++;
                        dataUser.email = tvEdit.getText().toString();
                        bundle.putString(Constants.txtTitleString, "Create Your Username");
                        bundle.putString(Constants.txtValueString, "Carecomm Username");
                        bundle.putInt(Constants.inputType, InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        break;
                    case 3:
                        // Constants.register_password = tvEdit.getText().toString();
                        if((tvEdit.getText().toString()).equals("")){
                            showDialog("Please insert your username!" , " OK","");
                            return;
                        }
                        screenCount++;
                        dataUser.username = tvEdit.getText().toString();
                        bundle.putString(Constants.txtTitleString,"Create Your Password");
                        bundle.putString(Constants.txtValueString,"Password");
                        bundle.putInt(Constants.inputType, InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    case 4:
                        if((tvEdit.getText().toString()).equals("")){
                            showDialog("Please insert your password!" , " OK","");
                            return;
                        }

                        screenCount++;
                        dataUser.password = tvEdit.getText().toString();
                        bundle.putString(Constants.txtTitleString,"Confirm Your Password");
                        bundle.putString(Constants.txtValueString,"Password");
                        bundle.putInt(Constants.inputType, InputType.TYPE_CLASS_TEXT  |  InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        btnNext.setText("Done");
                        break;
                    case 5:
                        String confirmPassword = tvEdit.getText().toString();
                        if (confirmPassword.contentEquals("")) {
                            showDialog("Please insert your password", "OK", "");
                            return;
                        }
                        if (!confirmPassword.contentEquals(dataUser.password)){
                            showDialog("Please check your password again!" , " OK","");
                            return;
                        }

                        signup();
                        return;
                        /*

                        if (isTablet(context)) {
                            register_Tablet();

                        }else {
                            //openActivity(context,MainActivity.class);
                            register();
                        }
                        */
                }
                ReplaceFragment(new RegistrationFrgment(),true, bundle);
            }
        }
    }

    private void signup() {
        try {
            SignupRequestModel request = new SignupRequestModel();
            request.user = dataUser;
            Call<SignupResponseModel> signupCall = ApiService.signup.signup(request);
            signupCall.enqueue(new Callback<SignupResponseModel>() {
                @Override
                public void onResponse(Call<SignupResponseModel> call, Response<SignupResponseModel> response) {
                    if (response.body() != null) {
                        showDialog("User registration is success!" , " OK","");
                        SignupResponseModel.UserModel user = response.body().user;
                        Data.token = "Bearer "+user.token;
                        openActivity(context, MainActivity.class);
                    } else {
                        showDialog("User registration is failed!" , " OK","");
                    }
                }

                @Override
                public void onFailure(Call<SignupResponseModel> call, Throwable t) {

                }
            });
        }catch( Exception e) {

        }
    }

    /*
    private void register() {

        try {
            userModel= new JSONObject();
            userModel.put("username", Constants.register_username);
            userModel.put("name",Constants.register_fullname);
            userModel.put("email", Constants.register_email);
            userModel.put("password", Constants.register_password);
        }catch (Exception e){

        }
        try {
            JsonObject_User = new JSONObject();
            JsonObject_User.put("user", userModel);
        }catch (Exception e){

        }
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObject =new JsonObjectRequest(
                Request.Method.POST,
                register_url,
                JsonObject_User, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Constants.register_username = response.getJSONObject("user").getString("username");
                    Constants.register_email = response.getJSONObject("user").getString("email");
                    Constants.token = response.getJSONObject("user").getString("token");
                    openActivity(context,MainActivity.class);
                    finish();


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    error_message_Json =new JSONObject(new String(error.networkResponse.data));
                    error_message_String = new String(new String(error.networkResponse.data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String error_username = error_message_Json.getString("errors");
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(error_username);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        int custom_timeout_ms = 50000;
        DefaultRetryPolicy policy = new DefaultRetryPolicy(custom_timeout_ms,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObject.setRetryPolicy(policy);
        requestQueue.add(jsonObject);

    }

    private void register_Tablet() {

        try {
            userModel= new JSONObject();
            userModel.put("username", Constants.register_username);
            userModel.put("name",Constants.register_fullname);
            userModel.put("email", Constants.register_email);
            userModel.put("password", Constants.register_password);
        }catch (Exception e){

        }
        try {
            JsonObject_User = new JSONObject();
            JsonObject_User.put("user", userModel);
        }catch (Exception e){

        }
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObject =new JsonObjectRequest(
                Request.Method.POST,
                register_url,
                JsonObject_User, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Constants.register_username = response.getJSONObject("user").getString("username");
                    Constants.register_email = response.getJSONObject("user").getString("email");
                    Constants.token = response.getJSONObject("user").getString("token");
                    openActivity(context,TabMainActivity.class);
                    finish();


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    error_message_Json =new JSONObject(new String(error.networkResponse.data));
                    error_message_String = new String(new String(error.networkResponse.data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String error_username = error_message_Json.getString("errors");
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(error_username);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        int custom_timeout_ms = 50000;
        DefaultRetryPolicy policy = new DefaultRetryPolicy(custom_timeout_ms,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObject.setRetryPolicy(policy);
        requestQueue.add(jsonObject);

    }

     */


}
