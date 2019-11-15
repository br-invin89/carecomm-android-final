package com.merculia.carecomm.Model;

public class CallModel {
    private String title;
    private int profilePic;
    private String callType;
    private  int callPhoto;
    private String callTime;
    private String callDate;
    private String screenName;

    public CallModel(String title, int profilePic, String callType, int callPhoto, String callTime
            , String callDate ,String screenName) {
        this.title = title;
        this.profilePic = profilePic;
        this.callType = callType;
        this.callPhoto = callPhoto;
        this.callTime = callTime;
        this.callDate = callDate;
        this.screenName = screenName;
    }
    public String getScreenName () {
        return screenName;
    }
    public String getTitle() {
        return title;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getCallType() {
        return callType;
    }

    public int getCallPhoto() {
        return callPhoto;
    }

    public String getCallTime() {
        return callTime;
    }

    public String getCallDate() {
        return callDate;
    }
}
