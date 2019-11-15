package com.merculia.carecomm.Frgments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.CameraPreview;
import com.merculia.carecomm.Utils.Constants;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class OpenCamerFrgment extends BaseFragment implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Camera mCamera;
    private LinearLayout cameraPreview;
    private CameraPreview mPreview;
    private boolean isVideoFragment = false;
    private TextView tvCamera,tvVideo;
    private ImageView ivFlipCamera,ivCapturePhoto,ivOpenGallary;
    private Button btnVideo,btnCamera;
    private TextView screenTitle;
    private ImageView ivClose;
    private boolean isRecording = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            isVideoFragment = getArguments().getBoolean(Constants.isVideoRecording);
//            txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_open_camera, container, false);

        setView(view);
        inits();
        setEvents();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setView(View view) {

        cameraPreview = view.findViewById(R.id.cPublisher);
        ivCapturePhoto = view.findViewById(R.id.iv_capture_photo);
        ivFlipCamera = view.findViewById(R.id.iv_flip_camera);
        ivOpenGallary = view.findViewById(R.id.iv_open_gallary);

        if (!getMainActivity().isTablet(context)){
            screenTitle = view.findViewById(R.id.screen_title);
            ivClose = view.findViewById(R.id.iv_close);
        }else {
            tvCamera = view.findViewById(R.id.tv_camera);
            tvVideo = view.findViewById(R.id.tv_video);
            btnCamera = view.findViewById(R.id.btn_camera);
            btnVideo = view.findViewById(R.id.btn_video);
        }
    }


    @Override
    protected void inits() {
        getPermissionLocationCamenra();

        if (!getMainActivity().isTablet(context)){
            if (isVideoFragment) {
                screenTitle.setText("Video");
            }else {
                screenTitle.setText("Camera");
            }

        }
        if (isVideoFragment){
            setVideoView();
        }else {
            setCameraView();
        }
        if (ActivityCompat.checkSelfPermission(
                getMainActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        }
    }
    private void setCameraView(){
       if (getMainActivity().isTablet(context)){
           tvCamera.setVisibility(View.GONE);
           btnCamera.setVisibility(View.VISIBLE);
           btnVideo.setVisibility(View.GONE);
           tvVideo.setVisibility(View.VISIBLE);
       }
       if (getMainActivity().isTablet(context)) {
           ivCapturePhoto.setImageResource(R.mipmap.camera_shutter_btn);
       }else {
           ivCapturePhoto.setImageResource(R.mipmap.group_378);

       }

    }
    private void setVideoView(){
        if (getMainActivity().isTablet(context)){
            tvCamera.setVisibility(View.VISIBLE);
            btnCamera.setVisibility(View.GONE);
            btnVideo.setVisibility(View.VISIBLE);
            tvVideo.setVisibility(View.GONE);
        }
        if (getMainActivity().isTablet(context)) {
            ivCapturePhoto.setImageResource(R.mipmap.camera_record_btn);
        }else {
            ivCapturePhoto.setImageResource(R.mipmap.record_video);

        }

    }

    private void setRecordingVideo(boolean isRecording){
        if (isRecording) {
            ivCapturePhoto.setImageResource(R.mipmap.stop_recording);
        }else {
            ivCapturePhoto.setImageResource(R.mipmap.record_video);
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
    ivOpenGallary.setOnClickListener(this);
    ivCapturePhoto.setOnClickListener(this);
        ivCapturePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (ActivityCompat.checkSelfPermission(context,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }


        });

        if (!getMainActivity().isTablet(context)){
            ivClose.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
    if (ivOpenGallary == view){
        if (getMainActivity().isTablet(context)) {
            getMainActivity().ReplaceFragmentWithBackstack(new GallaryFrgment());
        }else {
            getMainActivity().ReplaceFragmentWithBackstack(new PhotosFrgment());
        }
    }
    if (view == ivCapturePhoto){
        if (getMainActivity().isTablet(context)) return;
        if (isVideoFragment){
            if (isRecording){
                isRecording = false;
            }else {
                isRecording = true;
            }
           setRecordingVideo(isRecording);
        }
    }
    if (view == ivClose){
            onBack();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//            imageView.setImageDrawable(drawable);
            String  filename = "test.png";
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            OutputStream outStream = null;

            File file = new File(filename);
            if (file.exists()) {
                file.delete();
                file = new File(extStorageDirectory, filename);
                Log.e("file exist", "" + file + ",Bitmap= " + filename);
            }
            try {
                // make a new bitmap from your file
                bitmap = BitmapFactory.decodeFile(file.getName());

                outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
                System.out.println("closed");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // imageView.setImageBitmap(photo);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!getMainActivity().isTablet(context)){
            getMainActivity().hideMenu();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}
