package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.merculia.carecomm.R;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Integer> resourceArray;
    int selectedPosition;


    public ImageViewPagerAdapter(Context context, ArrayList<Integer> markets) {
        // TODO Auto-generated constructor stub
        this.context = context;
        resourceArray = markets;


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return resourceArray.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.item_image_slider, null);

        Log.d("SizemediaArrayList", "inits: "+ resourceArray.size());

        int resource = resourceArray.get(position);

        final ImageView imageMediaDetail= layout.findViewById(R.id.image_media_detail);
        final FrameLayout framOpacity=layout.findViewById(R.id.fram_opacity);


        imageMediaDetail.setImageResource(resource);

        ((ViewPager) container).addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }



    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void setSelected(int selected) {
        this.selectedPosition = selected;
        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
