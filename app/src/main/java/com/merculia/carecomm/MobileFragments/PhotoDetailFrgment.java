package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.ImageViewPagerAdapter;
import com.merculia.carecomm.Adapters.ShareImageItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.FamilyModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class PhotoDetailFrgment extends BaseFragment implements View.OnClickListener,ViewPager.OnPageChangeListener {


   private ImageView imageBack,imageShare , imageDelete,ivCloseShare;
   private ViewPager galleryImage;
   private TextView tvCount, textView_count1,screenTitleShare;
   private int currentPosition = 0;
    private SlidingUpPanelLayout mLayout;
    private ImageViewPagerAdapter imageViewPagerAdapter;
    private Button btnShare;
    private RecyclerView rvUsers;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            currentPosition = getArguments().getInt(Constants.POSITION);
//            txtTitleString = getArguments().getString(Constants.txtTitleString);
//            txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_detail, container, false);

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
        imageBack = view.findViewById(R.id.image_back);
        imageShare = view.findViewById(R.id.image_share);
        tvCount = view.findViewById(R.id.textView_count);
        textView_count1 = view.findViewById(R.id.textView_count1);
        galleryImage = view.findViewById(R.id.galleryImage_vp);
        imageDelete = view.findViewById(R.id.image_delete);
        mLayout =  getMainActivity().findViewById(R.id.sliding_layout);
        screenTitleShare = getMainActivity().findViewById(R.id.screen_title_share);
        ivCloseShare = getMainActivity().findViewById(R.id.iv_close_share);
        btnShare = getMainActivity().findViewById(R.id.btn_share);
        rvUsers = getMainActivity().findViewById(R.id.rv_users);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Log.i(TAG, "onPanelSlide, offset " + slideOffset);

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState== SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                    mLayout.setPanelHeight(0);
                    mLayout.setCoveredFadeColor(ContextCompat.getColor(context,R.color.transparent));
                    try {
                      //  getMainActivity().showMenu();
                        //companiesPriceListAdapter.notifyDataSetChanged();
                    }catch (Exception ex){

                    }

                   // Log.i(TAG, "onPanelStateChanged " + newState);
                }

            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });



        //mLayout.setAnchorPoint(0.9f);

        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

    }


    @Override
    protected void inits() {
        screenTitleShare.setText("Share Picture(s)");
        setAdapter();
        setUserAdapter();
    }

    private void setAdapter(){
        ArrayList<Integer> stringArrayList = new ArrayList<>();
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        stringArrayList.add(R.drawable.main_photo_1);
        stringArrayList.add(R.drawable.main_photo_2);
        stringArrayList.add(R.drawable.main_photo_3);
        stringArrayList.add(R.drawable.main_photo_4);
        stringArrayList.add(R.drawable.main_photo_5);
        stringArrayList.add(R.drawable.main_photo_6);
        imageViewPagerAdapter = new ImageViewPagerAdapter(context, stringArrayList);
        galleryImage.setAdapter(imageViewPagerAdapter);
        galleryImage.setCurrentItem(currentPosition);
        textView_count1.setText("" + (currentPosition + 1));
        tvCount.setText("/" + stringArrayList.size());
        galleryImage.measure(0, 0);
        imageViewPagerAdapter.notifyDataSetChanged();
    }
    @Override
    protected void setEvents() {
        imageBack.setOnClickListener(this);
        imageShare.setOnClickListener(this);
        imageDelete.setOnClickListener(this);
        galleryImage.addOnPageChangeListener(this);
        ivCloseShare.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }
    private void setUserAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity(),RecyclerView.HORIZONTAL,false);
        rvUsers.setHasFixedSize(true);


        rvUsers.setLayoutManager(layoutManager);
        ArrayList<FamilyModel> list = new ArrayList<>();
        list.add(new FamilyModel(R.drawable.main_photo_1,"Alex",""));
        list.add(new FamilyModel(R.drawable.main_photo_2,"Even",""));
        list.add(new FamilyModel(R.drawable.main_photo_3,"John",""));
        list.add(new FamilyModel(R.drawable.main_photo_4,"Test",""));
        list.add(new FamilyModel(R.drawable.main_photo_5,"Ali",""));

        ShareImageItemAdapter menuItemAdapter = new ShareImageItemAdapter(context,list);
        rvUsers.setAdapter(menuItemAdapter);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
      if (imageBack == view){
          onBack();
      }
      if (imageDelete == view){
          getMainActivity().showDialog("Are sure to delete this picture?","Ok","Cancel","");
      }
      if (ivCloseShare == view){
         // getMainActivity().showMenu();
          mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
      }
      if (view == imageShare){
          //getMainActivity().hideMenu();
          mLayout.setCoveredFadeColor(0x99000000);
          mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
      }
      if (view == btnShare){
         // getMainActivity().showMenu();
          mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
      }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Article.FeaturedArticles mediaCenter = mediaCenterArrayList.get(position);
        textView_count1.setText("" + (position + 1));
        //tvCount.setText("/" + mediaCenterArrayList.size());

        imageViewPagerAdapter.setSelected(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
