package com.merculia.carecomm.Logics.Chat;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CallApi {
    @POST("call/request-connect/{callingType}/{responderRef}")
    Call<Void> requestConnect(@Header("Authorization") String token, @Path("callingType") String callingType, @Path("responderRef") String responderRef);

    @POST("call/check-coming-request")
    Call<CheckComingRequestResponseModel> checkComingRequest(@Header("Authorization") String token);

    @POST("call/cancel-requesting")
    Call<Void> cancelRequesting(@Header("Authorization") String token);

    @POST("call/refuse-calling")
    Call<Void> refuseCalling(@Header("Authorization") String token);

    @POST("call/check-receiving-request")
    Call<CheckReceivingRequestResponseModel> checkReceivingRequest(@Header("Authorization") String token);

    @POST("call/receive-connect/{requesterRef}")
    Call<ReceiveConnectResponseModel> receiveConnect(@Header("Authorization") String token, @Path("requesterRef") String requesterRef);

    @POST("call/close-connect")
    Call<Void> closeConnect(@Header("Authorization") String token);

}
