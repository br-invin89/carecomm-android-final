package com.merculia.carecomm.Logics.Event;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventApi {
    @POST("event/create")
    Call<Void> createEvent(@Header("Authorization") String token, @Body CreateEventRequestModel request);

    @GET("event/list-by-date/date/{date}")
    Call<DailyEventResponseModel> listByDate(@Header("Authorization") String token, @Path("date") String date);

    @GET("event/list-between/from-date/{fromDate}/to-date/{toDate}")
    Call<UpcomingEventResponseModel> listBetween(@Header("Authorization") String token, @Path("fromDate") String fromDate, @Path("toDate") String toDate);

}
