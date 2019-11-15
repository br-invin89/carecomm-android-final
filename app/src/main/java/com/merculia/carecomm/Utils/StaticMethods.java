package com.merculia.carecomm.Utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;

import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.merculia.carecomm.BuildConfig;

import java.util.List;

public class StaticMethods {

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
                ssid = ssid.replace("\"", "");
            }
        }
        return ssid;
    }

    public static boolean isLocnEnabled(Context context) {
        List locnProviders = null;
        try {
            LocationManager lm =(LocationManager) context.getApplicationContext().getSystemService(Activity.LOCATION_SERVICE);
            locnProviders = lm.getProviders(true);

            return (locnProviders.size() != 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (BuildConfig.DEBUG) {
                if ((locnProviders == null) || (locnProviders.isEmpty()))

                    Log.d("CheckPermission", "Location services disabled");
                else
                    Log.d("CheckPermission", "locnProviders: " + locnProviders.toString());
            }
        }
        return(false);
    }
}
