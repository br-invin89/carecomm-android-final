package com.merculia.carecomm.Logics.Auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupApi {
    @POST("auth/signup")
    Call<SignupResponseModel> signup(@Body SignupRequestModel signupData);
}
