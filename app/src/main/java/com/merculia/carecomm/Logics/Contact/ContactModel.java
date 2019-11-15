package com.merculia.carecomm.Logics.Contact;

public class ContactModel {
    public UserRef userRef;
    public String relationship;
    public boolean isContacted;

    public static class UserRef {
        public String _id;
        public String name;
        public String photo;
        public String username;
        public String email;
    }
}
