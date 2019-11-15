package com.merculia.carecomm.Model;

import java.util.ArrayList;

public class ChatModel {
    private String messgae;
    private boolean isMine;
    private int messageCode;
    private ArrayList<Integer> arrayListImages;
//    public ChatModel(String messgae, boolean isMine) {
//        this.messgae = messgae;
//        this.isMine = isMine;
//    }


    public ChatModel(String messgae, int messageCode,ArrayList<Integer> arrayListImages) {
        this.messgae = messgae;
        this.messageCode = messageCode;
        this.arrayListImages = arrayListImages;
    }

    public ArrayList<Integer> getArrayListImages() {
        return arrayListImages;
    }

    public String getMessgae() {
        return messgae;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getMessageCode(){
        return messageCode;
    }
}
