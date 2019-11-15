package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateEventCalendarFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivClose;
    private Button btnNext,btnBack;
    private TextView screenTitle;
    private CalendarView calendarView;

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
        View view = inflater.inflate(R.layout.fragment_event_calender, container, false);

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
        btnNext = view.findViewById(R.id.btn_next);
        screenTitle = view.findViewById(R.id.screen_title);
        btnBack = view.findViewById(R.id.btn_back);
        calendarView = view.findViewById(R.id.calendar_month);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
                try {
                    Date date = formatter.parse(String.format("%d-%d-%d", y, m+1, d));
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

                    Data.creatingEvent.startDate = formatter2.format(date);
                    Data.creatingEvent.endDate = formatter2.format(date);
                } catch(ParseException e) {
                }
            }
        });
    }

    @Override
    protected void inits() {
        screenTitle.setText("Create Event");
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    protected void setEvents() {
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivClose.setOnClickListener(this);
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
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventTimeDateFragment(),false,true);
        }
    }
}
