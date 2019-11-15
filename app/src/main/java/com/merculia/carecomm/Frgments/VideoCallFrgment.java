package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.CallItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.CallModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class VideoCallFrgment extends BaseFragment implements View.OnClickListener {

    private ImageView ivCallBtn;
    private RecyclerView rvMakeCall;
    private ImageView ivScrollUp,ivScrollDown;
    private LinearLayoutManager layoutManager;

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
        View view = inflater.inflate(R.layout.fragment_call, container, false);

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
        ivCallBtn = view.findViewById(R.id.iv_btn_makeCall);
        rvMakeCall = view.findViewById(R.id.rv_make_call);
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);
    }

    @Override
    protected void inits() {
        ivCallBtn.setImageResource(R.mipmap.makr_vid_call_btn);
        setAdapter();
        //Glide.with(context).load(R.mipmap.make_call_btn).load(ivCallBtn);
    }

    @Override
    protected void setEvents() {
        ivCallBtn.setOnClickListener(this);
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
        ArrayList<CallModel> list = new ArrayList<>();
        list.add(new CallModel("Daughter",R.drawable.main_photo_5,"Missed Call"
                ,R.mipmap.missed_video,"8:40 AM","Feb 16 2019","Video Call"));
        list.add(new CallModel("Sister",R.drawable.main_photo_6,"Missed Call"
                ,R.mipmap.incoming_video,"10:40 AM","Feb 19 2019","Video Call"));
        list.add(new CallModel("Mom",R.drawable.main_photo_6,"Missed Call"
                ,R.mipmap.outgoing_video,"11:40 AM","Feb 21 2019","Video Call"));
        list.add(new CallModel("Daughter",R.drawable.main_photo_8,"Missed Call"
                ,R.mipmap.incoming_video,"8:40 AM","Feb 16 2019","Video Call"));

        CallItemAdapter menuItemAdapter = new CallItemAdapter(context,list,getMainActivity());

        rvMakeCall.setAdapter(menuItemAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view == ivCallBtn){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.txtTitleString,"Make a Video Call");
            bundle.putString(Constants.Button_Title,"Video Call");

            getMainActivity().ReplaceFragmentWithBackstack(new SelectContactFrgment(),bundle);
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
