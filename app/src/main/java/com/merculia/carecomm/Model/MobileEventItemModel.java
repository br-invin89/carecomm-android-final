package com.merculia.carecomm.Model;

public class MobileEventItemModel {
    private String month;
    private String date;
    private String title;
    private String time;
    private int profilePic;

    public MobileEventItemModel(String month, String date, String title, String time, int profilePic) {
        this.month = month;
        this.date = date;
        this.title = title;
        this.time = time;
        this.profilePic = profilePic;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getProfilePic() {
        return profilePic;
    }
}
