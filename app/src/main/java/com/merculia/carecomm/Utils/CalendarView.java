package com.merculia.carecomm.Utils;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.merculia.carecomm.Adapters.CalendarAdapter;
import com.merculia.carecomm.Frgments.AddEventFrgment;
import com.merculia.carecomm.Frgments.EventDetailFrgment;
import com.merculia.carecomm.Frgments.ViewAllEventFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.interfaces.OpenEventsScreen;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.cardview.widget.CardView;

public class CalendarView extends LinearLayout implements View.OnClickListener {
    // calendar components
    private LinearLayout header;
    private OpenEventsScreen openEventsScreen;
    private ImageView btnPrev;
    private  ImageView btnNext;
    private  TextView txtDateDay;
    private  GridView gridView;
    private Button btnViewAll;
    private CardView addEvent;
    private LinearLayout firstEvent;
    private LinearLayout secondEvent;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initControl(context, attrs);
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);
        txtDateDay = findViewById(R.id.date_display_day);
        gridView = findViewById(R.id.calendar_grid);

        addEvent = findViewById(R.id.add_event);
        btnViewAll = findViewById(R.id.btn_view_all);
        firstEvent = findViewById(R.id.first_event);
        secondEvent = findViewById(R.id.second_event);
        txtDateDay = findViewById(R.id.date_display_day);
        gridView = findViewById(R.id.calendar_grid);

        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        addEvent.setOnClickListener(this);
        firstEvent.setOnClickListener(this);
        secondEvent.setOnClickListener(this);
    }

    /**
     * Load control xml layout
     */
    public void setOnOpenFragment(OpenEventsScreen onOpenFragment){
        this.openEventsScreen = onOpenFragment;
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_layout, this);
        assignUiElements();
        updateCalendar();
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {
        //Date c = Calendar.getInstance().getTime();

       // Calendar calendarClone = Calendar.getInstance().get(Calendar.MONTH)-1;
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)Calendar.getInstance().clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < 35)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        gridView.setAdapter(new CalendarAdapter(getContext(), cells));

        // update title
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,d,M,yyyy");
//        String[] dateToday = sdf.format(calendar.getTime()).split(",");

        txtDateDay.setText(getMonthForInt(Calendar.getInstance().get(Calendar.MONTH))+" "
                +Calendar.getInstance().get(Calendar.YEAR));

    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    @Override
    public void onClick(View view) {
        if (view == btnNext){

        }
        if (view == btnPrev){

        }
        if (openEventsScreen != null){
            if (view == btnViewAll){
                openEventsScreen.openEventsFragment(new ViewAllEventFrgment(),null);
            }

            if (view == addEvent){
                openEventsScreen.openEventsFragment(new AddEventFrgment(),null);
            }
            if (view == firstEvent){
                Bundle bundle = new Bundle();
                bundle.putString(Constants.txtTitleString,"Test");
                openEventsScreen.openEventsFragment(new EventDetailFrgment(),bundle);
            }
            if (view == secondEvent){
                Bundle bundle = new Bundle();
                bundle.putString(Constants.txtTitleString,"Hospital");
                openEventsScreen.openEventsFragment(new EventDetailFrgment(),bundle);
            }
        }
    }
}
