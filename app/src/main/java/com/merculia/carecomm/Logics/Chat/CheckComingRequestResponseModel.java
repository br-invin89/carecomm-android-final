package com.merculia.carecomm.Logics.Chat;

public class CheckComingRequestResponseModel {
    public boolean exists;
    public UserModel requesterRef;
    public String relationship;
    public String callingType;
    public static class UserModel {
        public String _id;
        public String name;
        public String username;
        public String email;
        public String photo;
    }
}
