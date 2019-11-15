package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.DailyEventItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Event.DailyEventResponseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventMobileFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivBack,ivClose;
    private TextView screenTitle;
    private LinearLayout layoutCreateEvent;
    private RecyclerView rvEvents;
    private LinearLayoutManager layoutManager;
    private CalendarView calendarMonth;
    private String strSelectedDate;

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
        View view = inflater.inflate(R.layout.fragment_event_mobile, container, false);

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
        ivBack = view.findViewById(R.id.iv_back);
        ivClose = view.findViewById(R.id.iv_close);
        screenTitle = view.findViewById(R.id.screen_title);
        layoutCreateEvent = view.findViewById(R.id.layout_create_event);
        rvEvents = view.findViewById(R.id.rv_events);

        rvEvents.setNestedScrollingEnabled(false);
        rvEvents.setHasFixedSize(false);
        calendarMonth = view.findViewById(R.id.calendar_month);
        calendarMonth.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView calendarView, int y, int m, int d) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
                try {
                    Date date = formatter.parse(String.format("%d-%d-%d", y, m+1, d));
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                    strSelectedDate = formatter2.format(date);
                    getEventsByDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void inits() {
        ivClose.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        screenTitle.setText("Calendar");
        setAdapter();
    }

    @Override
    protected void setEvents() {
        ivBack.setOnClickListener(this);
        layoutCreateEvent.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
    private void setAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity());
        rvEvents.setLayoutManager(layoutManager);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        strSelectedDate = dateFormat.format(c.getTime());

        getEventsByDate();
    }
    @Override
    public void onClick(View view) {
        if (ivBack == view){
            openHome();
        }
        if (layoutCreateEvent == view){
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventSelectFamilyFragment(),false, true);
        }

    }

    private void getEventsByDate() {
        String token = Data.token;
        Call<DailyEventResponseModel> dailyEventsCall = ApiService.event.listByDate(token, strSelectedDate);
        dailyEventsCall.enqueue(new Callback<DailyEventResponseModel>() {
            @Override
            public void onResponse(Call<DailyEventResponseModel> call, Response<DailyEventResponseModel> response) {
                DailyEventItemAdapter adapter = new DailyEventItemAdapter(context, response.body().events, getMainActivity());

                rvEvents.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DailyEventResponseModel> call, Throwable t) {

            }
        });

    }
}

