package com.merculia.carecomm.BaseActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    protected Activity context = this;
    private ProgressDialog pDialog = null;
    private int currentApiVersion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setBottomNavigationBar();
        if (isTablet(context)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void makeToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    public void makeToastConnection(){
        Toast.makeText(context, "Please Check your internet connection", Toast.LENGTH_LONG).show();
    }
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public void openActivity(Context context, Class<?> cls){
        startActivity(new Intent(context,cls));
    }

    public void openActivity(Context context, Class<?> cls , Bundle bundle){
        Intent intent = new Intent(context,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void makeLog(String logName,String logValue){
        Log.d(logName,logValue);
    }

    public void showDialog(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
    }


    public void hideBottomBarAndStatus(){
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        if (isTablet(context)) {
            decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                // | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }else {
            decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                // | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }


    }

    public void setBottomNavigationBar(){
        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        hideBottomBarAndStatus();
//        if(currentApiVersion >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().getDecorView().setSystemUiVisibility(flags);
//
//            // Code below is to handle presses of Volume up or Volume down.
//            // Without this, after pressing volume buttons, the navigation bar will
//            // show up and won't hide
//            final View decorView = getWindow().getDecorView();
//            decorView
//                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//
//                        @Override
//                        public void onSystemUiVisibilityChange(int visibility) {
//                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                                decorView.setSystemUiVisibility(flags);
//                            }
//                        }
//                    });
//        }
    }
    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {

            hideBottomBarAndStatus();
        }
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
    public void hideDialog(){
        pDialog.dismiss();
    }
    abstract protected void init();
    abstract protected void setEvents();
    abstract protected void setView();
}
