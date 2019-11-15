package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;

import java.util.ArrayList;


public class CreateEventTimeDateFragment extends BaseFragment implements View.OnClickListener, Spinner.OnItemSelectedListener, Switch.OnCheckedChangeListener {

    private ImageView ivClose;
    private Button btnNext,btnBack;
    private TextView screenTitle;
    private Switch switchAllDay;
    private EditText etStartTime, etEndTime;
    private Spinner spRepeat;

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
        View view = inflater.inflate(R.layout.fragment_event_date_time, container, false);

        setView(view);
        inits();
        setEvents();
        initViewWithData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void setView(View view) {
        ivClose = view.findViewById(R.id.iv_close);
        btnNext = view.findViewById(R.id.btn_next);
        screenTitle = view.findViewById(R.id.screen_title);
        btnBack = view.findViewById(R.id.btn_back);
        switchAllDay = view.findViewById(R.id.switch_all_day);
        etStartTime = view.findViewById(R.id.et_start_time);
        etEndTime = view.findViewById(R.id.et_end_time);
        spRepeat = view.findViewById(R.id.sp_repeat);
    }


    @Override
    protected void inits() {
        screenTitle.setText("Create Event");
    }

    @Override
    protected void setEvents() {
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        spRepeat.setOnItemSelectedListener(this);
        switchAllDay.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == btnBack || view == ivClose){
            onBack();
        }
        if (view == btnNext){
            getDataFromView();
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventNoteFrgment(),false,true);
        }
    }

    private void getDataFromView() {
        Data.creatingEvent.startTime = etStartTime.getText().toString();
        Data.creatingEvent.endTime = etEndTime.getText().toString();
    }

    private void initViewWithData() {
        // repeat spinner
        ArrayList<String> items = new ArrayList<String>();
        items.add("Never"); items.add("Yes"); items.add("2 Times");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getMainActivity(), R.layout.spinner_item, items);
        spRepeat.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spRepeat) {
            Data.creatingEvent.repeat = adapterView.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        if (buttonView==switchAllDay) {
            Data.creatingEvent.isAllDay = b;
        }
    }
}
