package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.RelativeProfileItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Profile.GetInfoResponseModel;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewProfileFrgment extends BaseFragment implements View.OnClickListener {
    private ImageView ivClose;
    private RecyclerView rvFamily;
    private RecyclerView.LayoutManager layoutManager;
    private CircleImageView ivMyPhoto;
    private TextView tvMyName;
    private TextView tvMyUsername;
    private TextView tvMyEmail;

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
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

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
        ivClose = view.findViewById(R.id.iv_close);
        rvFamily = view.findViewById(R.id.rv_family);
        rvFamily.setNestedScrollingEnabled(false);
        rvFamily.setHasFixedSize(false);
        ivMyPhoto = view.findViewById(R.id.my_photo);
        tvMyName = view.findViewById(R.id.my_name);
        tvMyUsername = view.findViewById(R.id.my_username);
        tvMyEmail = view.findViewById(R.id.my_email);
    }


    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    protected void inits() {
        setFamilyAdapter();
    }

    @Override
    protected void setEvents() {
    ivClose.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    private void setFamilyAdapter(){
        layoutManager = new GridLayoutManager(getMainActivity(),2);
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

        RelativeProfileItemAdapter menuItemAdapter = new RelativeProfileItemAdapter(context, Data.myUserData.relatives);
        rvFamily.setAdapter(menuItemAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            getMainActivity().onBackPressed();
        }
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
        }catch(Exception e) {
        }
    }

    private void initViewWithData() {
        Picasso.get().load(Data.myUserData.photo).into(ivMyPhoto);
        tvMyName.setText(Data.myUserData.name);
        tvMyUsername.setText(Data.myUserData.username);
        tvMyEmail.setText(Data.myUserData.email);
    }
}
