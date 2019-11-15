package com.merculia.carecomm.BaseActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.merculia.carecomm.Activities.LoginActivity;
import com.merculia.carecomm.Activities.MainActivity;
import com.merculia.carecomm.Activities.TabMainActivity;
import com.merculia.carecomm.MobileFragments.HomeFrgment;
import com.merculia.carecomm.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


abstract public class BaseFragment extends Fragment {
    protected Activity context;
    private BaseFragmentActivity mainActivity;
    private Handler handler;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        handler = new Handler(Looper.getMainLooper());


//        inits();
//        setEvents();
    }
    public void makeToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (context == null)
            context = (Activity) activity;
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
        if (context instanceof TabMainActivity){
            mainActivity = (TabMainActivity) context;
        }

    }

    public BaseFragmentActivity getMainActivity() {

        if (mainActivity != null)
            return mainActivity;
        else {
            if (context instanceof MainActivity) {
                mainActivity = ((MainActivity) context);
            } else
                return null;
        }
        return mainActivity;
    }


    protected void openHome(){
        getMainActivity().clearBackStack();
        getMainActivity().ReplaceFragment(new HomeFrgment(),true);
    }
    protected void onBack(){
        getMainActivity().onBackPressed();

    }    protected void getPermissionLocation(){
        if (ActivityCompat.checkSelfPermission(
                getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getMainActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }
    protected void getPermissionReadStorgae(){
        if (ActivityCompat.checkSelfPermission(
                getMainActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        getMainActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getMainActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            return;
        }
    }
    protected void getPermissionLocationCamenra(){
        if (ActivityCompat.checkSelfPermission(
                getMainActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getMainActivity(), new String[]{Manifest.permission.CAMERA}, 2);
            return;
        }
    }
    abstract protected void setView(View view);
    abstract protected void inits();
    abstract protected void setEvents();

    @Override
    public void onResume() {
        super.onResume();
        if (getMainActivity()!=null) {
            getMainActivity().showMenu();
            getMainActivity().setBottomNavigationBar();
            if (getMainActivity().isTablet(context)) {
                getMainActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            }else {
                getMainActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    protected void setSpinnerAdapter(Spinner spinner , int recourceArray){

        ArrayAdapter<CharSequence> foodadapter = ArrayAdapter.createFromResource(
                getMainActivity(), recourceArray, R.layout.spinner_item);
        foodadapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(foodadapter);
    }

    protected void logoutvalidation() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:

                        // stopService(new Intent(MainActivity.this,MyService.class));
                        getMainActivity().openActivity(context, LoginActivity.class);
                        getMainActivity().finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Logout");
        builder.setMessage("Are you want to Logout").setPositiveButton("Ok", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }
}
