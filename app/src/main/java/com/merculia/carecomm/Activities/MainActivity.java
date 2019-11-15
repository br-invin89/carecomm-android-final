package com.merculia.carecomm.Activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.merculia.carecomm.Adapters2.MenuItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.CommFrgment;
import com.merculia.carecomm.Frgments.EditSettingFrgment;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.Logics.Chat.CheckComingRequestResponseModel;
import com.merculia.carecomm.Logics.Chat.CheckReceivingRequestResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.MobileFragments.ChatRoomMobileFragment;
import com.merculia.carecomm.MobileFragments.HomeFrgment;
import com.merculia.carecomm.Frgments.SettingFrgment;
import com.merculia.carecomm.Model.MenuItemModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import com.merculia.carecomm.Utils.StaticMethods;
import com.merculia.carecomm.interfaces.ChangeAppTheme;
import com.merculia.carecomm.interfaces.WifiName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseFragmentActivity implements MenuItemAdapter.OnItemClickLinter, ChangeAppTheme, View.OnClickListener {

    private RecyclerView rvMenu;
    private RecyclerView.LayoutManager layoutManager;
    //private int lastPostion = 0;

    private WifiName wifiNameObject = null;

    private LinearLayout callLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setBottomNavigationBar();
        setTheme();
        if (isTablet(context)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main);
        setView();
        init();
        setEvents();
        startCheckComingRequest();
    }

    @Override
    protected void init() {
        settingFrgment = new SettingFrgment();
        wifiNameObject = settingFrgment;
        if (isTablet(context)) {
            if (Constants.isRecreateActivity) {
                Constants.isRecreateActivity = false;
                setAdapter(3);
                ReplaceFragment(new EditSettingFrgment());
            } else {
                setAdapter(0);
                ReplaceFragment(new CommFrgment());
            }
        } else {
            setAdapterMobile();
            ReplaceFragment(new HomeFrgment());
        }
    }

    @Override
    protected void setEvents() {
        if (!isTablet(context)) {
            callLayout.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.ISVOICECALL) {
            callLayout.setVisibility(View.VISIBLE);
            ReplaceFragmentWithBackstack(new ChatRoomMobileFragment());
        }
    }

    @Override
    protected void setView() {
        rvMenu = findViewById(R.id.rv_menu);

        if (!isTablet(context)) {
            menuLayout = findViewById(R.id.menu_layout);
            callLayout = findViewById(R.id.call_layout);
        }
    }

    @Override
    public void onItemClick(int postion) {


    }

    private void setAdapterMobile() {
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this, 5);
        rvMenu.setHasFixedSize(true);


        rvMenu.setLayoutManager(layoutManager);
        ArrayList<MenuItemModel> list = new ArrayList<>();
        list.add(new MenuItemModel(R.mipmap.comm_col_icon, "", R.mipmap.comm_white_icon));
        list.add(new MenuItemModel(R.mipmap.contacts_selected_icon, "", R.mipmap.contacts_icon));
        list.add(new MenuItemModel(R.mipmap.calendar_col_icon, "", R.mipmap.calendar_white_icon));
        list.add(new MenuItemModel(R.mipmap.camera_col_icon, "", R.mipmap.camera_white_icon));
        list.add(new MenuItemModel(R.mipmap.settings_col_icon, "", R.mipmap.settings_white_icon));

        menuItemAdapter = new MenuItemAdapter(this, context, list, -1);
        menuItemAdapter.setItemClickLinter(this);
        rvMenu.setAdapter(menuItemAdapter);
    }

    private void setAdapter(int postion) {

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.height = getHeight() / 4;
                lp.width = lp.height;
                return true;
            }
        };
        rvMenu.setHasFixedSize(true);


        rvMenu.setLayoutManager(layoutManager);
        ArrayList<MenuItemModel> list = new ArrayList<>();
        list.add(new MenuItemModel(R.mipmap.comm_col_icon, "Comm", R.mipmap.comm_white_icon));
        list.add(new MenuItemModel(R.mipmap.calendar_col_icon, "Calender", R.mipmap.calendar_white_icon));
        list.add(new MenuItemModel(R.mipmap.camera_col_icon, "Camera", R.mipmap.camera_white_icon));
        list.add(new MenuItemModel(R.mipmap.settings_col_icon, "Settings", R.mipmap.settings_white_icon));

        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, context, list, postion);
        menuItemAdapter.setItemClickLinter(this);
        rvMenu.setAdapter(menuItemAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (StaticMethods.isLocnEnabled(context)) {
                wifiNameObject.getWifiName(StaticMethods.getCurrentSsid(context));
            }
        }
        //makeToast(context,""+requestCode);
    }

    @Override
    public void changeTheme() {
        Constants.isRecreateActivity = true;
        recreate();
    }

    @Override
    public void onClick(View v) {
        if (v == callLayout) {
            if (Constants.ISVOICECALL) {
                finish();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    private void startCheckComingRequest() {
        Handler handler = new Handler();
        Runnable checkCallRunnable = new Runnable() {
            @Override
            public void run() {
                String token = Data.token;
                handler.postDelayed(this, 2000);
                Log.i("calling data", CallData.callingStatus);
                Log.i("calling data", CallData.currentScreen);
                if (CallData.callingStatus.contentEquals("responding")) {
                    if (CallData.callingType.contentEquals("video") && !CallData.currentScreen.contentEquals("Video Call Screen")){
                        CallData.currentScreen = "Video Call Screen";
                        openActivity(context, DialVideoCallMobileActivity.class);
                    }
                    if (CallData.callingType.contentEquals("audio") && !CallData.currentScreen.contentEquals("Audio Call Screen")){
                        CallData.currentScreen = "Audio Call Screen";
                        openActivity(context, DialCallMobileActivity.class);
                    }
                }
                if (CallData.callingStatus.contentEquals("be-cancelled")) {
                    if (!CallData.currentScreen.contentEquals("Main Screen")) {
                        CallData.callingStatus = "none";
                        CallData.currentScreen = "Main Screen";
                        openActivity(context, MainActivity.class);
                    }
                }
                if (CallData.callingStatus.contentEquals("none") ||
                    CallData.callingStatus.contentEquals("responding")
                ) {
                    Call<CheckComingRequestResponseModel> checkCall = ApiService.call.checkComingRequest(token);
                    checkCall.enqueue(new Callback<CheckComingRequestResponseModel>() {
                        @Override
                        public void onResponse(Call<CheckComingRequestResponseModel> call, Response<CheckComingRequestResponseModel> response) {
                            if (response.body().exists) {
                                CallData.callingStatus = "responding";
                                CallData.callingType = response.body().callingType;
                                CallData.partnerData._id = response.body().requesterRef._id;
                                CallData.partnerData.email = response.body().requesterRef.email;
                                CallData.partnerData.name = response.body().requesterRef.name;
                                CallData.partnerData.photo = response.body().requesterRef.photo;
                                CallData.partnerData.username = response.body().requesterRef.username;
                                CallData.partnerData.relationship = response.body().relationship;
                            } else {
                                if (CallData.callingStatus.contentEquals("responding")) {
                                    CallData.callingStatus = "be-cancelled";
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckComingRequestResponseModel> call, Throwable t) {
                        }
                    });
                }
                if (CallData.callingStatus.contentEquals("be-refused")) {
                    if (!CallData.currentScreen.contentEquals("Main Screen")) {
                        CallData.currentScreen = "Main Screen";
                        CallData.callingStatus = "none";
                        openActivity(context, MainActivity.class);
                    }
                }
                if (CallData.callingStatus.contentEquals("requesting")) {
                    Call<CheckReceivingRequestResponseModel> checkReceivingCall = ApiService.call.checkReceivingRequest(token);
                    checkReceivingCall.enqueue(new Callback<CheckReceivingRequestResponseModel>() {
                        @Override
                        public void onResponse(Call<CheckReceivingRequestResponseModel> call, Response<CheckReceivingRequestResponseModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().status.contentEquals("waiting")) {

                                } else if (response.body().status.contentEquals("call-started")) {
                                    CallData.callingType = response.body().callingType;
                                    CallData.opentokApiKey = response.body().apiKey;
                                    CallData.opentokSessionId = response.body().sessionId;
                                    CallData.opentokToken = response.body().token;
                                    CallData.callingStatus = "calling";
                                    if (CallData.callingType.contentEquals("video")){
                                        CallData.currentScreen = "Video Call Screen";
                                        openActivity(context, DialVideoCallMobileActivity.class);
                                    }
                                    if (CallData.callingType.contentEquals("audio")) {
                                        CallData.currentScreen = "Audio Call Screen";
                                        openActivity(context, DialCallMobileActivity.class);
                                    }
                                }
                            } else {
                                CallData.callingStatus = "be-refused";
                                if (CallData.callingType.contentEquals("video"))
                                    CallData.currentScreen = "Video Call Screen";
                                if (CallData.callingType.contentEquals("audio"))
                                    CallData.currentScreen = "Audio Call Screen";
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckReceivingRequestResponseModel> call, Throwable t) {

                        }
                    });
                }
            }
        };
        handler.postDelayed(checkCallRunnable, 5000);
    }
}
