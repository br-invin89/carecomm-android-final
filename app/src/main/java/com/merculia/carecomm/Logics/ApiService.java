package com.merculia.carecomm.Logics;

import com.merculia.carecomm.Logics.Auth.AuthApi;
import com.merculia.carecomm.Logics.Auth.ForgotPasswordApi;
import com.merculia.carecomm.Logics.Auth.SignupApi;
import com.merculia.carecomm.Logics.Chat.CallApi;
import com.merculia.carecomm.Logics.Chat.ChatApi;
import com.merculia.carecomm.Logics.Contact.ContactApi;
import com.merculia.carecomm.Logics.Event.EventApi;
import com.merculia.carecomm.Logics.Profile.ProfileApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static Retrofit retrofit;
    private static String apiUrl = "http://3.10.9.30:3000/api/";
    private static String socketioUrl = "http://3.10.9.30:3700";

    public static AuthApi auth;
    public static SignupApi signup;
    public static ProfileApi profile;
    public static ForgotPasswordApi forgotPassword;
    public static ContactApi contact;
    public static EventApi event;
    public static CallApi call;
    public static ChatApi chat;

    public static void initApi() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Content-type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(interceptor);
        OkHttpClient okHttpClient = okHttpBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        auth = retrofit.create(AuthApi.class);
        signup = retrofit.create(SignupApi.class);
        forgotPassword = retrofit.create(ForgotPasswordApi.class);
        profile = retrofit.create(ProfileApi.class);
        contact = retrofit.create(ContactApi.class);
        event = retrofit.create(EventApi.class);
        call = retrofit.create(CallApi.class);
        chat = retrofit.create(ChatApi.class);
    }
}
