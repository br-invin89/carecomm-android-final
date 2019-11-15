package com.merculia.carecomm.Logics.Auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<AuthResponseModel> tryLogin(@Body AuthRequestModel user);
}
