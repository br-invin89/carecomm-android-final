package com.merculia.carecomm.Logics.Chat;

import java.util.List;

public class ChatGroupedItemModel {
    public String date;
    public String dateExpression;
    public List<ChatLogModel> logs;

    public static class ChatLogModel {
        public String time;
        public String type;
        public String messageText; // type == "message"
        public String senderRef; // type == "message"
        public String url; // type == "shared image"
        public String elapsedTime; // type=="video call" or "audio call"
    }

}
