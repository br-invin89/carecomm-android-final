package com.merculia.carecomm.Model;

public class UpcomingEnventModel {
    private String title;
    private int profilePic;
    private String enentType;
    private String eventTime;
    private String eventDate;
    private boolean isChatScreen;

    public UpcomingEnventModel(String title, int profilePic, String enentType, String eventTime, String eventDate,boolean isChatScreen) {
        this.title = title;
        this.profilePic = profilePic;
        this.enentType = enentType;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
        this.isChatScreen = isChatScreen;
    }

    public boolean isChatScreen() {
        return isChatScreen;
    }

    public String getTitle() {
        return title;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getEnentType() {
        return enentType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }
}
