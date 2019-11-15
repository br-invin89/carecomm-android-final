package com.merculia.carecomm.BaseActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.merculia.carecomm.Adapters2.MenuItemAdapter;
import com.merculia.carecomm.Frgments.SettingFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.CarecommStorage;
import com.merculia.carecomm.Utils.Constants;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public abstract class BaseFragmentActivity extends BaseActivity {

    private Handler handler;
    //public ReturnFromCall returnFromCall;
    public SettingFrgment settingFrgment;
    public CardView menuLayout;
    public MenuItemAdapter menuItemAdapter;
    public void ReplaceFragment(Fragment fragment) {
        clearBackStack();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_content, fragment, fragment.getClass().getSimpleName());
        trans.commit();
    }

    public void clearBackStack() {
        try {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void actionBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
    }

    public void ReplaceFragmentWithBackstack(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        // trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
        trans.replace(R.id.main_content, fragment);
        trans.addToBackStack(fragment.getClass().getName());
        trans.commit();
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_content);
    }

    public void ReplaceFragmentWithBackstack(Fragment fragment, Bundle argument) {
        try {
            fragment.setArguments(argument);
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
            trans.replace(R.id.main_content, fragment);
            trans.addToBackStack(fragment.getClass().getName());
            trans.commit();
        }catch (Exception e){e.printStackTrace();}
    }


    public void ReplaceFragmentWithBackstack(final Fragment fragment, boolean delay) {
        if (delay) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
                    trans.replace(R.id.main_content, fragment);
                    trans.addToBackStack(fragment.getClass().getName());
                    trans.commit();
                }
            }, 400);
        } else {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
            trans.replace(R.id.main_content, fragment);
            trans.addToBackStack(fragment.getClass().getName());
            trans.commit();
        }
    }

    public void ReplaceFragmentWithBackstack(final Fragment fragment, boolean delay, final boolean animate) {
        if (delay) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    if (animate)
                        trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
                    trans.replace(R.id.main_content, fragment);
                    trans.addToBackStack(fragment.getClass().getName());
                    trans.commit();
                }
            }, 400);
        } else {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (animate)
                trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
            trans.replace(R.id.main_content, fragment);
            trans.addToBackStack(fragment.getClass().getName());
            trans.commit();
        }
    }
    public void ReplaceFragmentWithBackstackSlideUp(final Fragment fragment, boolean delay, final boolean animate) {
        if (delay) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    if (animate)
                        trans.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down);
                    trans.replace(R.id.main_content, fragment);
                    trans.addToBackStack(fragment.getClass().getName());
                    trans.commit();
                }
            }, 400);
        } else {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (animate)
                trans.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down);
            trans.replace(R.id.main_content, fragment);
            trans.addToBackStack(fragment.getClass().getName());
            trans.commit();
        }
    }

    public void ReplaceFragment(Fragment fragment, boolean animation, Bundle argument) {

        try {
            clearBackStack();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            fragment.setArguments(argument);
            if (animation)
                trans.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out);
            trans.addToBackStack(null);
            trans.replace(R.id.main_content, fragment);
            trans.commit();
        }catch (Exception ee){ee.printStackTrace();}
    }


    public void ReplaceFragment(Fragment fragment, Bundle argument) {

        clearBackStack();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(argument);
        trans.addToBackStack(null);
        trans.replace(R.id.main_content, fragment);
        trans.commit();
    }

    public void ReplaceFragment(Fragment fragment, boolean animation) {

        clearBackStack();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (animation)
            trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
        trans.addToBackStack(null);
        trans.replace(R.id.main_content, fragment);
        trans.commit();

    }
    protected void getPermissionLocationCamenra(){
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    context, new String[]{Manifest.permission.CAMERA}, 2);
            return;
        }
    }

    protected void setTheme(){
        if (CarecommStorage.getData(getApplicationContext(), Constants.SharePrefrence.THEME_NAME).equalsIgnoreCase(Constants.ThemesName.THEME_TAN)){
            getTheme().applyStyle(R.style.AppThemeTan_NoActionBar,true);
        }else
        if (CarecommStorage.getData(getApplicationContext(), Constants.SharePrefrence.THEME_NAME).equalsIgnoreCase(Constants.ThemesName.THEME_BLUE)){
            getTheme().applyStyle(R.style.AppThemeBlue_NoActionBar,true);
        }else
        if (CarecommStorage.getData(getApplicationContext(), Constants.SharePrefrence.THEME_NAME).equalsIgnoreCase(Constants.ThemesName.THEME_CREAM)){
            getTheme().applyStyle(R.style.AppThemeCream_NoActionBar,true);
        }
    }

    public void showDialog(String msg,String btnTitle,String dialogTitle ) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:

                        // stopService(new Intent(MainActivity.this,MyService.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dialogTitle);
        builder.setMessage(msg).setPositiveButton(btnTitle, dialogClickListener).show();
    }
    public void showDialog(String msg,String btnPositive,String btnNegative,String dialogTitle ) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:

                        // stopService(new Intent(MainActivity.this,MyService.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dialogTitle);

        builder.setMessage(msg).setPositiveButton(btnPositive, dialogClickListener).setNegativeButton(btnNegative,dialogClickListener).show();

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

}
