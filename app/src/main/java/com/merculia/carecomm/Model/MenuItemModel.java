package com.merculia.carecomm.Model;

public class MenuItemModel {
    private int image;
    private String title;
    private int imageSeleted;

    public MenuItemModel(int image, String title,int imageSeleted) {
        this.image = image;
        this.title = title;
        this.imageSeleted= imageSeleted;
    }

    public int getImage() {
        return image;
    }

    public int getImageSeleted() {
        return imageSeleted;
    }

    public String getTitle() {
        return title;
    }
}
