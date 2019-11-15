package com.merculia.carecomm.Frgments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.PhotosItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PhotosFrgment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rvPhotos;

    private ImageView ivScrollUp,ivScrollDown;
    private GridLayoutManager layoutManager;
    private TextView screenTitle;
    private ImageView ivBack,ivClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            txtTitleString = getArguments().getString(Constants.txtTitleString);
//            txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

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
        rvPhotos = view.findViewById(R.id.rv_photos);
        if (!getMainActivity().isTablet(context)){
            screenTitle = view.findViewById(R.id.screen_title);
            ivBack = view.findViewById(R.id.iv_back);
            ivClose = view.findViewById(R.id.iv_close);
        }else {
            ivScrollUp = view.findViewById(R.id.iv_sv_up);
            ivScrollDown = view.findViewById(R.id.iv_sv_down);
        }


    }

    private void setAdapter(){
        layoutManager = new GridLayoutManager(getMainActivity(),3);
        rvPhotos.setLayoutManager(layoutManager);
        ArrayList<Integer> stringArrayList = new ArrayList<>();
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        PhotosItemAdapter menuItemAdapter = new PhotosItemAdapter(getMainActivity(),context,stringArrayList);

        rvPhotos.setAdapter(menuItemAdapter);
    }
    @Override
    protected void inits() {
        if (!getMainActivity().isTablet(context)){
            screenTitle.setText("Album");
            ivBack.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.GONE);
        }
        setAdapter();
    }

    @Override
    protected void setEvents() {
        if (!getMainActivity().isTablet(context)){

            ivBack.setOnClickListener(this);
        }else {
            ivScrollUp.setOnClickListener(this);
            ivScrollDown.setOnClickListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }

    @Override
    public void onClick(View view) {
        if (view == ivScrollUp){
            int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemIndex > 0) {
                layoutManager.smoothScrollToPosition(rvPhotos,null,firstVisibleItemIndex-1);
            }
        }

        if (view == ivScrollDown){
            int totalItemCount = rvPhotos.getAdapter().getItemCount();
            if (totalItemCount <= 0) return;
            int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();

            if (lastVisibleItemIndex >= totalItemCount) return;
            layoutManager.smoothScrollToPosition(rvPhotos,null,lastVisibleItemIndex+1);

        }

        if (view == ivBack){
            onBack();
        }

    }
}
