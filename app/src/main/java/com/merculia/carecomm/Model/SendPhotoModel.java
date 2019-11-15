package com.merculia.carecomm.Model;

public class SendPhotoModel {
    private int image;
    private boolean isSelected = false;
    private int selecetedCoun;

    public SendPhotoModel(int image, boolean isSelected) {
        this.image = image;
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setSelecetedCoun(int selecetedCoun) {
        this.selecetedCoun = selecetedCoun;
    }

    public int getSelecetedCoun() {
        return selecetedCoun;
    }

    public int getImage() {
        return image;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
