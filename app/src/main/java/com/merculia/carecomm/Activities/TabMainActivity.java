package com.merculia.carecomm.Activities;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.merculia.carecomm.Adapters2.MenuItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.CommFrgment;
import com.merculia.carecomm.Frgments.EditSettingFrgment;
import com.merculia.carecomm.Frgments.SettingFrgment;
import com.merculia.carecomm.MobileFragments.ChatRoomMobileFragment;
import com.merculia.carecomm.MobileFragments.HomeFrgment;
import com.merculia.carecomm.Model.MenuItemModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import com.merculia.carecomm.Utils.StaticMethods;
import com.merculia.carecomm.interfaces.ChangeAppTheme;
import com.merculia.carecomm.interfaces.WifiName;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabMainActivity extends BaseFragmentActivity implements MenuItemAdapter.OnItemClickLinter, ChangeAppTheme, View.OnClickListener {

    private RecyclerView rvMenu;
    private RecyclerView.LayoutManager layoutManager;
    //private int lastPostion = 0;
  //  private CardView menuLayout;
    private WifiName wifiNameObject= null;
   // public SettingFrgment settingFrgment;
    private LinearLayout callLayout;
    //public   MenuItemAdapter menuItemAdapter;

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
        setContentView(R.layout.activity_main);
        setView();
        init();
        setEvents();
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
        }else {
            setAdapterMobile();
            ReplaceFragment(new HomeFrgment());
        }



    }

    @Override
    protected void setEvents() {
        if (!isTablet(context)){
            callLayout.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.ISVOICECALL){
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
    private void setAdapterMobile(){
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,5);
        rvMenu.setHasFixedSize(true);


        rvMenu.setLayoutManager(layoutManager);
        ArrayList<MenuItemModel> list = new ArrayList<>();
        list.add(new MenuItemModel(R.mipmap.comm_col_icon,"",R.mipmap.comm_white_icon));
        list.add(new MenuItemModel(R.mipmap.contacts_selected_icon,"",R.mipmap.contacts_icon));
        list.add(new MenuItemModel(R.mipmap.calendar_col_icon,"",R.mipmap.calendar_white_icon));
        list.add(new MenuItemModel(R.mipmap.camera_col_icon,"",R.mipmap.camera_white_icon));
        list.add(new MenuItemModel(R.mipmap.settings_col_icon,"",R.mipmap.settings_white_icon));

        menuItemAdapter = new MenuItemAdapter(this,context,list,-1);
        menuItemAdapter.setItemClickLinter(this);
        rvMenu.setAdapter(menuItemAdapter);
    }
    private void setAdapter(int postion){

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this){
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
        list.add(new MenuItemModel(R.mipmap.comm_col_icon,"Comm",R.mipmap.comm_white_icon));
        list.add(new MenuItemModel(R.mipmap.calendar_col_icon,"Calender",R.mipmap.calendar_white_icon));
        list.add(new MenuItemModel(R.mipmap.camera_col_icon,"Camera",R.mipmap.camera_white_icon));
        list.add(new MenuItemModel(R.mipmap.settings_col_icon,"Settings",R.mipmap.settings_white_icon));

        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this,context,list,postion);
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

    public void hideMenu(){
        if (!isTablet(context)) {
            menuLayout.setVisibility(View.GONE);
        }
    }
    public void showMenu(){
        if (!isTablet(context)) {
            menuLayout.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void changeTheme() {
        Constants.isRecreateActivity = true;
        recreate();
    }

    @Override
    public void onClick(View v) {
        if (v == callLayout){
            if (Constants.ISVOICECALL){
               finish();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
