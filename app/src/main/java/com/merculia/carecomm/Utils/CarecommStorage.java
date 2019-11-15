package com.merculia.carecomm.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CarecommStorage {

    public static void saveData(Context context, String key, String value){
        SharedPreferences pref1 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = pref1.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getData(Context context,String key){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, "");
    }
}
