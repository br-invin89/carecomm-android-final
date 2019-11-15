package com.merculia.carecomm.Activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.CameraPreview;

public class DialVideoCallActivity extends BaseFragmentActivity implements View.OnClickListener {


    private Camera mCamera;
    private LinearLayout cameraPreview;
    private CameraPreview mPreview;
    private Button btnCancel,btnMinimize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBottomNavigationBar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTheme();
        if (isTablet(context)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_dial_video_call);
        setView();
        init();
        setEvents();

    }

    @Override
    protected void init() {
        getPermissionLocationCamenra();

        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        }
    }
    private void startCamera(){
        mCamera =  Camera.open();
        mCamera.setDisplayOrientation(90);

        mPreview = new CameraPreview(context, mCamera);
        cameraPreview.addView(mPreview);
    }
    @Override
    protected void setEvents() {
        btnCancel.setOnClickListener(this);

    }

    @Override
    protected void setView() {
        cameraPreview =findViewById(R.id.cPublisher);
        btnCancel= findViewById(R.id.btn_cancel);
        btnMinimize = findViewById(R.id.btn_minimize);

    }

    @Override
    public void onClick(View view) {
        if (view == btnCancel){
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        }else {
            finish();
        }
    }
}
