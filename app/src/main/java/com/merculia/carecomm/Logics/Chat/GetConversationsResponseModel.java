package com.merculia.carecomm.Logics.Chat;

import java.util.List;

public class GetConversationsResponseModel {
    public List<ConversationModel> conversations;

    public static class ConversationModel {
        public RelativeModel relative;
        public MessageModel message;
    }

    public static class RelativeModel {
        public String _id;
        public String name;
        public String photo;
        public String username;
        public String relationship;
        public String email;
    }

    public static class MessageModel {
        public String messageText;
        public String when;
    }
}
