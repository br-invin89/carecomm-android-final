package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.ContactItemAdapter;
import com.merculia.carecomm.Adapters.EventItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.ContactModel;
import com.merculia.carecomm.Model.EventModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.CalendarView;
import com.merculia.carecomm.interfaces.OpenEventsScreen;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ViewAllEventFrgment extends BaseFragment implements View.OnClickListener {

    private TextView screenTitle;
    private ImageView ivClose;

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
        View view = inflater.inflate(R.layout.fragment_view_all, container, false);

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
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);
        ivClose = view.findViewById(R.id.iv_close);
        rvMakeCall = view.findViewById(R.id.rv_all_events);
        screenTitle = view.findViewById(R.id.screen_title);

    }


    @Override
    protected void inits() {
        screenTitle.setText("View Events");
        setAdapter();
    }
    private void setAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity());
        rvMakeCall.setLayoutManager(layoutManager);
        ArrayList<EventModel> list = new ArrayList<>();
        list.add(new EventModel("Test","8:00 AM","9:00 AM"));
        list.add(new EventModel("Hospital","8:00 AM","9:00 AM"));
        list.add(new EventModel("Tution","8:00 AM","9:00 AM"));
        list.add(new EventModel("Movie","8:00 AM","9:00 AM"));
        list.add(new EventModel("Launch","8:00 AM","9:00 AM"));


        EventItemAdapter menuItemAdapter = new EventItemAdapter(context,list);

        rvMakeCall.setAdapter(menuItemAdapter);
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

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            getMainActivity().onBackPressed();
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
