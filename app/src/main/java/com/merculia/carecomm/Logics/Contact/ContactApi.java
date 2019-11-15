package com.merculia.carecomm.Logics.Contact;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ContactApi {
    @GET("contact/get-contacts")
    Call<GetContactsResponseModel> getContacts(@Header("Authorization") String token);

    @DELETE("contact/remove/{userId}")
    Call<Void> removeContact(@Header("Authorization") String token, @Path("userId") String userId);

    @GET("contact/search/username/{username}")
    Call<SearchResponseModel> searchByUsername(@Header("Authorization") String token, @Path("username") String username);

    @POST("contact/send-contact-request")
    Call<Void> sendContactRequest(@Header("Authorization") String token, @Body SendContactRequestModel request);

}
