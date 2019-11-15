package com.merculia.carecomm.Logics.Profile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ProfileApi {
    @GET("profile/info")
    Call<GetInfoResponseModel> getInfo(@Header("Authorization") String token);

    @PUT("profile/update")
    Call<UpdateResponseModel> update(@Header("Authorization") String token, @Body UpdateRequestModel data);

    @PUT("profile/change-password")
    Call<ChangePasswordResponseModel> changePassword(@Header("Authorization") String token, @Body ChangePasswordRequestModel data);
}
