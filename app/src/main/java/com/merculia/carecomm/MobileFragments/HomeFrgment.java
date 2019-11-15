package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.RelativeItemAdapter;
import com.merculia.carecomm.Adapters2.UpcomingEventItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Event.UpcomingEventResponseModel;
import com.merculia.carecomm.Logics.Profile.GetInfoResponseModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFrgment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rvFamily,rvUpcomingEvents;
    private Button btnViewProfile;
    private RecyclerView.LayoutManager layoutManager;
    private CircleImageView imgMyPhoto;
    private TextView txtMyName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // txtTitleString = getArguments().getString(Constants.txtTitleString);
            // txtValueString = getArguments().getString(Constants.txtValueString);
            // inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setView(view);
        inits();
        setEvents();
        loadData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setView(View view) {
        rvFamily = view.findViewById(R.id.rv_faimly);
        btnViewProfile = view.findViewById(R.id.btn_view_profile);
        rvUpcomingEvents = view.findViewById(R.id.rv_upcoming_events);
        imgMyPhoto = view.findViewById(R.id.user_photo);
        txtMyName = view.findViewById(R.id.user_name);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().menuItemAdapter.setSelectedPosition(-1);
    }

    @Override
    protected void inits() {
        rvUpcomingEvents.setNestedScrollingEnabled(false);
        rvUpcomingEvents.setHasFixedSize(false);

        setUpcomingEventsAdapter();
    }

    private void setRelativeAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvFamily.setHasFixedSize(true);

        rvFamily.setLayoutManager(layoutManager);
        /*
        ArrayList<FamilyModel> list = new ArrayList<>();
        list.add(new FamilyModel(R.drawable.main_photo_1,"Alex","Brother"));
        list.add(new FamilyModel(R.drawable.main_photo_2,"Even Morgan","Brother"));
        list.add(new FamilyModel(R.drawable.main_photo_3,"John Wick","Brother"));
        list.add(new FamilyModel(R.drawable.main_photo_4,"Test 12","Brother"));
        list.add(new FamilyModel(R.drawable.main_photo_5,"Ali Haider","Brother"));
         */

        RelativeItemAdapter relativeItemAdapter = new RelativeItemAdapter(context, Data.myUserData.relatives);
        rvFamily.setAdapter(relativeItemAdapter);
    }

    private void setUpcomingEventsAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity());
        rvUpcomingEvents.setLayoutManager(layoutManager);
        getUpcomingEvents();
    }

    private void initViewWithData() {
        Picasso.get().load(Data.myUserData.photo).into(imgMyPhoto);
        txtMyName.setText(Data.myUserData.name);

        //load relative data
        setRelativeAdapter();
    }

    private void loadData() {
        try {
            String token = Data.token;
            Call<GetInfoResponseModel> getInfoCall = ApiService.profile.getInfo(token);
            getInfoCall.enqueue(new Callback<GetInfoResponseModel>() {
                @Override
                public void onResponse(Call<GetInfoResponseModel> call, Response<GetInfoResponseModel> response) {
                    if (response.body() != null) {
                        Data.myUserData = response.body().getUser();
                        if (Data.myUserData.photo.contentEquals("")) {
                            Data.myUserData.photo = "https://static.productionready.io/images/smiley-cyrus.jpg";
                        }
                        initViewWithData();
                    }
                }

                @Override
                public void onFailure(Call<GetInfoResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void setEvents() {
        btnViewProfile.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == btnViewProfile){
           getMainActivity().ReplaceFragmentWithBackstack(new ViewProfileFrgment(),false,true);
        }
    }

    private void getUpcomingEvents() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.add(Calendar.DAY_OF_YEAR, -14);
        String strFromDate = dateFormat.format(c.getTime());
        c.add(Calendar.DAY_OF_YEAR, +28);
        String strToDate = dateFormat.format(c.getTime());
        String token = Data.token;
        Call<UpcomingEventResponseModel> upcomingEventsCall = ApiService.event.listBetween(token, strFromDate, strToDate);
        upcomingEventsCall.enqueue(new Callback<UpcomingEventResponseModel>() {
            @Override
            public void onResponse(Call<UpcomingEventResponseModel> call, Response<UpcomingEventResponseModel> response) {
                UpcomingEventItemAdapter adapter = new UpcomingEventItemAdapter(context, response.body().events, getMainActivity());
                rvUpcomingEvents.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UpcomingEventResponseModel> call, Throwable t) {
            }
        });
    }
}
