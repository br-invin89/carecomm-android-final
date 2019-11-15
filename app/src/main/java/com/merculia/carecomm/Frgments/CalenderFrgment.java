package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.merculia.carecomm.Adapters.ViewPagerAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.CalendarView;
import com.merculia.carecomm.interfaces.OpenEventsScreen;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class CalenderFrgment extends BaseFragment implements OpenEventsScreen {


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
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

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
        calendarView = view.findViewById(R.id.calender_view);



    }


    @Override
    protected void inits() {

    }

    @Override
    protected void setEvents() {
        calendarView.setOnOpenFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }



    @Override
    public void openEventsFragment(Fragment fragment, Bundle bundle) {
        if (bundle == null) {
            getMainActivity().ReplaceFragmentWithBackstack(fragment,false,true);
        }else {
            getMainActivity().ReplaceFragmentWithBackstack(fragment,bundle);
        }
    }
}
