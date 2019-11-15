package com.merculia.carecomm.Model;

public class ContactModel {

    private String title;
    private  int image;
    private String buttonTitle;

    public ContactModel(String title, int image,String buttonTitle) {
        this.title = title;
        this.image = image;
        this.buttonTitle=buttonTitle;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
