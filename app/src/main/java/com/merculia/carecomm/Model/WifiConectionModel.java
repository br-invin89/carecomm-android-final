package com.merculia.carecomm.Model;

public class WifiConectionModel {
    private String wifiName;
    private int wifiIcon;
    private boolean isConnected;

    public WifiConectionModel(String wifiName, int wifiIcon, boolean isConnected) {
        this.wifiName = wifiName;
        this.wifiIcon = wifiIcon;
        this.isConnected = isConnected;
    }

    public String getWifiName() {
        return wifiName;
    }

    public int getWifiIcon() {
        return wifiIcon;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
