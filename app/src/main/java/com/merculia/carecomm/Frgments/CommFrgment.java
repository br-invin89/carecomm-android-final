package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.merculia.carecomm.Adapters.ViewPagerAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;


public class CommFrgment extends BaseFragment implements OnTabSelectedListener {

    private ViewPager viewPager;

    private TabLayout tabLayout;

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
        View view = inflater.inflate(R.layout.fragment_comm, container, false);

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

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tabs);
    }


    @Override
    protected void inits() {
        setViewPager();
    }

    @Override
    protected void setEvents() {
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


    private void setViewPager(){
        ViewPagerAdapter  viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFrag(new CallFrgment(),"Call");
        viewPagerAdapter.addFrag(new VideoCallFrgment(),"Video Call");
        viewPagerAdapter.addFrag(new ChatFrgment(),"Chat");
        viewPagerAdapter.addFrag(new ContactFrgment(),"Contact");
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);


        viewPager.setAdapter(viewPagerAdapter);
        tabSelection(0);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //tabSelection(tab.getPosition());
        TextView text = (TextView) tab.getCustomView();
        Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.pt_sans_caption_bold);
        }else {
            typeface = Typeface.createFromAsset(getMainActivity().getAssets(), "pt_sans_caption_bold.ttf");
        }
        text.setTypeface(typeface);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView text = (TextView) tab.getCustomView();
        Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.pt_sans_caption_regular);
        }else {
            typeface = Typeface.createFromAsset(getMainActivity().getAssets(), "pt_sans_caption_regular.ttf");
        }

        text.setTypeface(typeface);
       // text.setTypeface(null, Typeface.NORMAL);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void tabSelection(int position){
        Typeface typeface = null;
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            if (i == position){
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    typeface = getResources().getFont(R.font.pt_sans_caption_bold);
                }else {
                    typeface = Typeface.createFromAsset(getMainActivity().getAssets(), "pt_sans_caption_bold.ttf");
                }

            }else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    typeface = getResources().getFont(R.font.pt_sans_caption_regular);
                }else {
                    typeface = Typeface.createFromAsset(getMainActivity().getAssets(), "pt_sans_caption_regular.ttf");
                }
            }
            TextView tv = (TextView)LayoutInflater.from(getMainActivity()).inflate(R.layout.textview_tab,null);
            tv.setTypeface(typeface);
            tabLayout.getTabAt(i).setCustomView(tv);

        }
    }
}
