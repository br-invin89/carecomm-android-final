package com.merculia.carecomm.Logics.Auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ForgotPasswordApi {
    @POST("auth/forgot-password")
    Call<ForgotPasswordResponseModel> forgotPassword(@Body ForgotPasswordRequestModel data);

    @POST("auth/check-confirm-code")
    Call<CheckConfirmCodeResponseModel> checkConfirmCode(@Body CheckConfirmCodeRequestModel data);

    @POST("auth/reset-password")
    Call<ResetPasswordResponseModel> resetPassword(@Body ResetPasswordRequestModel data);

}
