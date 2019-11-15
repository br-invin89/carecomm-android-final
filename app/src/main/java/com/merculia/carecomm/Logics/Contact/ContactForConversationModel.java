package com.merculia.carecomm.Logics.Contact;

public class ContactForConversationModel {
    public boolean isContacted;
    public UserRef userRef;
    public String relationship;

    public static class UserRef {
        public String _id;
        public String name;
        public String photo;
        public String username;
        public String email;
    }
}
