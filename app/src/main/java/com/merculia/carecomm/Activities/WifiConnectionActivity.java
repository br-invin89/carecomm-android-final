package com.merculia.carecomm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.WifiConnectionFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

public class WifiConnectionActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBottomNavigationBar();
        setTheme();
        if (isTablet(context)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_wifi_connection);

        init();
    }

    @Override
    protected void init() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.isMinimize,true);
        ReplaceFragment(new WifiConnectionFrgment(),bundle);
    }

    @Override
    protected void setEvents() {

    }

    @Override
    protected void setView() {

    }


}
