package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.EventItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.EventModel;
import com.merculia.carecomm.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AddEventFrgment extends BaseFragment implements View.OnClickListener {

    private TextView screenTitle;
    private ImageView ivClose;
    private Button btnDone;
    private Spinner spinnerEventCat,spinnerEventRemainder,spinnerEventRep;
    private ScrollView scrollView;
    private ImageView ivScrollUp,ivScrollDown;


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
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

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
        spinnerEventCat = view.findViewById(R.id.spinner_event_cat);
        spinnerEventRemainder = view.findViewById(R.id.spinner_event_remainder);
        spinnerEventRep = view.findViewById(R.id.spinner_event_rep);
        btnDone = view.findViewById(R.id.btn_done);
        ivClose = view.findViewById(R.id.iv_close);
        screenTitle = view.findViewById(R.id.screen_title);
        scrollView = view.findViewById(R.id.scroll_view);
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);

    }


    @Override
    protected void inits() {
        screenTitle.setText("Create an Events");
        setSpinnerAdapter(spinnerEventCat,R.array.event_cat);
        setSpinnerAdapter(spinnerEventRemainder,R.array.event_rep);
        setSpinnerAdapter(spinnerEventRep,R.array.event_rep);

    }

    @Override
    protected void setEvents() {
        btnDone.setOnClickListener(this);
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

        if (view == btnDone){
            getMainActivity().onBackPressed();
        }

        if (view == ivScrollUp){
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }

        if (view == ivScrollDown){
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);

        }
    }
}
