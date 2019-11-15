package com.merculia.carecomm.Logics.Auth;

public class AuthRequestModel {
    UserModel user;

    public static class UserModel {
        public String username;
        public String password;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
