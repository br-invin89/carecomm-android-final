package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
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


public class ChangProfilePicFragment extends BaseFragment implements View.OnClickListener {



    private RecyclerView rvFiles;
    private ImageView ivClose;
    private TextView screenTitle;
    private GridLayoutManager layoutManager;
    private ImageView ivScrollUp,ivScrollDown;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //isMinimize = getArguments().getBoolean(Constants.isMinimize);
//            txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_profile, container, false);

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


        ivClose = view.findViewById(R.id.iv_close);
        screenTitle = view.findViewById(R.id.screen_title);
        rvFiles = view.findViewById(R.id.rv_files);
        if (getMainActivity().isTablet(context)) {
            ivScrollUp = view.findViewById(R.id.iv_sv_up);
            ivScrollDown = view.findViewById(R.id.iv_sv_down);
        }

    }

    @Override
    protected void inits() {
        if (getMainActivity().isTablet(context)) {
            screenTitle.setText("Change Profile Picture");
        }else {
            screenTitle.setText("Edit Profile");
        }
        setAdapter();
        //Glide.with(context).load(R.mipmap.make_call_btn).load(ivCallBtn);
    }

    @Override
    protected void setEvents() {

        ivClose.setOnClickListener(this);
        if (getMainActivity().isTablet(context)) {
            ivScrollUp.setOnClickListener(this);
            ivScrollDown.setOnClickListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }



    private void setAdapter(){
        layoutManager = new GridLayoutManager(getMainActivity(),3);
        rvFiles.setLayoutManager(layoutManager);
        ArrayList<Integer> stringArrayList = new ArrayList<>();
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        PhotosItemAdapter menuItemAdapter = new PhotosItemAdapter(getMainActivity(),context,stringArrayList);

        rvFiles.setAdapter(menuItemAdapter);
    }




    @Override
    public void onClick(View view) {


        if (view == ivClose){
            getMainActivity().onBackPressed();
        }

        if (view == ivScrollUp){
            int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemIndex > 0) {
                layoutManager.smoothScrollToPosition(rvFiles,null,firstVisibleItemIndex-1);
            }
        }

        if (view == ivScrollDown){
            int totalItemCount = rvFiles.getAdapter().getItemCount();
            if (totalItemCount <= 0) return;
            int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemIndex >= totalItemCount) return;
            layoutManager.smoothScrollToPosition(rvFiles,null,lastVisibleItemIndex+1);

        }

    }
}
