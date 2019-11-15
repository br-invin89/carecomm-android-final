package com.merculia.carecomm.Logics.Auth;

public class ResetPasswordResponseModel {
    public UserModel user;

    public static class UserModel {
        public String username;
        public String email;
        public String token;
    }
}
