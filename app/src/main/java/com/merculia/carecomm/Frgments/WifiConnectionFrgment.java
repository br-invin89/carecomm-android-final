package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.ContactItemAdapter;
import com.merculia.carecomm.Adapters.WifiConnetionItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.ContactModel;
import com.merculia.carecomm.Model.WifiConectionModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class WifiConnectionFrgment extends BaseFragment implements View.OnClickListener {


    private TextView screenTitle;
    private ImageView ivClose;
    private RecyclerView rvMakeCall;
    private ImageView ivScrollUp,ivScrollDown;
    private LinearLayoutManager layoutManager;
    private boolean isWifiActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isWifiActivity = getArguments().getBoolean(Constants.isMinimize);
           // screenTitleString = getArguments().getString(Constants.txtTitleString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_contact, container, false);

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
        rvMakeCall = view.findViewById(R.id.rv_make_call);
        screenTitle = view.findViewById(R.id.screen_title);
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);
    }

    @Override
    protected void inits() {
        screenTitle.setText("Connect to Wi-Fi");
        setAdapter();
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        ivScrollUp.setOnClickListener(this);
        ivScrollDown.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
    private void setAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity());
        rvMakeCall.setLayoutManager(layoutManager);
        ArrayList<WifiConectionModel> list = new ArrayList<>();
        list.add(new WifiConectionModel("Wi-Fi",R.mipmap.wifi_full,true));
        list.add(new WifiConectionModel("Wi-Fi 2",R.mipmap.wifi_full,false));
        list.add(new WifiConectionModel("Wi-Fi 3",R.mipmap.wifi_mid,false));
        list.add(new WifiConectionModel("Wi-Fi 4",R.mipmap.wifi_mid,false));
        list.add(new WifiConectionModel("Wi-Fi 5",R.mipmap.wifi_low,false));
        list.add(new WifiConectionModel("Wi-Fi 6",R.mipmap.wifi_low,false));
        list.add(new WifiConectionModel("Wi-Fi 7",R.mipmap.wifi_low,false));
        list.add(new WifiConectionModel("Wi-Fi 8",R.mipmap.wifi_low,false));
        list.add(new WifiConectionModel("Wi-Fi 9",R.mipmap.wifi_low,false));
        list.add(new WifiConectionModel("Wi-Fi 10",R.mipmap.wifi_no_signal,false));
        list.add(new WifiConectionModel("Wi-Fi 11",R.mipmap.wifi_no_signal,false));


        WifiConnetionItemAdapter menuItemAdapter = new WifiConnetionItemAdapter(context,list);
        rvMakeCall.addItemDecoration(new DividerItemDecoration(rvMakeCall.getContext(), DividerItemDecoration.VERTICAL));
        rvMakeCall.setAdapter(menuItemAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view == ivClose){
            if (isWifiActivity){
                getActivity().finish();

            }else {
                getActivity().onBackPressed();
            }

        }

        if (view == ivScrollUp){
            int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemIndex > 0) {
                layoutManager.smoothScrollToPosition(rvMakeCall,null,firstVisibleItemIndex-1);
            }
        }

        if (view == ivScrollDown){
            int totalItemCount = rvMakeCall.getAdapter().getItemCount();
            if (totalItemCount <= 0) return;
            int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();

            if (lastVisibleItemIndex >= totalItemCount) return;
            layoutManager.smoothScrollToPosition(rvMakeCall,null,lastVisibleItemIndex+1);

        }
    }
}
