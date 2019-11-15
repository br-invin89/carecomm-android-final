package com.merculia.carecomm.Logics.Chat;

import com.merculia.carecomm.Logics.Contact.GetContactsForConversationResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatApi {
    @GET("chat/get-contacts-for-conversation")
    Call<GetContactsForConversationResponseModel> getContactsForConversation(@Header("Authorization") String token);

    @GET("chat/chat-history/with/{partnerRef}")
    Call<GetChatHistoryResponseModel> getChatHistory(@Header("Authorization") String token, @Path("partnerRef") String partnerRef);

    @POST("chat/send-message/to/{partnerRef}")
    Call<Void> sendMessage(@Header("Authorization") String token, @Path("partnerRef") String partnerRef, @Body SendMessageRequestModel request);

    @GET("chat/get-conversations")
    Call<GetConversationsResponseModel> getConversations(@Header("Authorization") String token);
}
