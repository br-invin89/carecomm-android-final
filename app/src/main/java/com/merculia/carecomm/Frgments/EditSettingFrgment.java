package com.merculia.carecomm.Frgments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.merculia.carecomm.Adapters.ChangeProfilePicItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Profile.GetInfoResponseModel;
import com.merculia.carecomm.Logics.Profile.UpdateRequestModel;
import com.merculia.carecomm.Logics.Profile.UpdateResponseModel;
import com.merculia.carecomm.Utils.CarecommStorage;
import com.merculia.carecomm.Utils.Constants;
import com.merculia.carecomm.interfaces.ChangeAppTheme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditSettingFrgment extends BaseFragment implements View.OnClickListener {
    //private EditText wifibtn;
    private Button btnCancel, btnDone;
    private Spinner spinnerRing;
    private View viewTan,viewBlue,viewCream;
    private TextView tvChangeProfile,tvChangePassword;
    private ChangeAppTheme changeAppTheme = null;
    private TextView screenTitle;
    private ImageView ivBack,ivClose;
    private CircleImageView ivMyPhoto;
    private EditText etMyEmail, etMyName;

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
        View view = inflater.inflate(R.layout.fragment_edit_setting, container, false);

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
    public void onResume() {
        super.onResume();
       // getMainActivity().hideMenu();
    }

    @Override
    protected void setView(View view) {

        btnCancel = view.findViewById(R.id.btn_cancel);
        btnDone = view.findViewById(R.id.btn_done);
        spinnerRing = view.findViewById(R.id.spinner_ring);
        tvChangePassword = view.findViewById(R.id.tv_change_password);
        viewTan = view.findViewById(R.id.view_tan);
        viewBlue = view.findViewById(R.id.view_blue);
        viewCream = view.findViewById(R.id.view_cream);
        tvChangeProfile = view.findViewById(R.id.tv_change_profile);

        if (!getMainActivity().isTablet(context)){
            screenTitle = view.findViewById(R.id.screen_title);
            ivBack = view.findViewById(R.id.iv_back);
            ivClose = view.findViewById(R.id.iv_close);
        }
        ivMyPhoto = view.findViewById(R.id.img_my_photo);
        etMyName = view.findViewById(R.id.et_my_name);
        etMyEmail = view.findViewById(R.id.et_my_email);
    }

    @Override
    protected void inits() {
        if (!getMainActivity().isTablet(context)){
            screenTitle.setText("Edit Profile");
            ivBack.setVisibility(View.VISIBLE);
        }
        changeAppTheme = (ChangeAppTheme) getMainActivity();
        setSpinnerAdapter(spinnerRing,R.array.select_bell);

    }

    @Override
    protected void setEvents() {
        btnDone.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        viewTan.setOnClickListener(this);
        viewBlue.setOnClickListener(this);
        viewCream.setOnClickListener(this);
        tvChangeProfile.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);

        if (!getMainActivity().isTablet(context)){

            ivBack.setOnClickListener(this);
            ivClose.setOnClickListener(this);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


    @Override
    public void onClick(View view) {
        if (view == btnCancel){

        getMainActivity().ReplaceFragmentWithBackstack(new SettingFrgment());
        }
        if (view == btnDone){
            updateInfo();
            if (getMainActivity().isTablet(context)) {
                //getMainActivity().ReplaceFragmentWithBackstack(new SettingFrgment());
            }else {
               onBack();
            }
        }
        if (view == tvChangeProfile){
            if (getMainActivity().isTablet(context)){
                getMainActivity().ReplaceFragmentWithBackstack(new ChangProfilePicFragment(),false,true);
            }else {
                listofDialog();

            }

           //
        }
        if (view == tvChangePassword){
            getMainActivity().ReplaceFragmentWithBackstack(new ChangPasswordFrgment(),false,true);

        }
        if (view== viewTan){
            CarecommStorage.saveData(context, Constants.SharePrefrence.THEME_NAME, Constants.ThemesName.THEME_TAN);
                changeAppTheme.changeTheme();

        }

        if (view== viewBlue){
            CarecommStorage.saveData(context, Constants.SharePrefrence.THEME_NAME, Constants.ThemesName.THEME_BLUE);
            changeAppTheme.changeTheme();
//            getMainActivity().setTheme(R.style.AppThemeBlue);
//            getMainActivity().recreate();
            //getMainActivity().setContentView(R.layout.activity_main);

        }

        if (view== viewCream){
            CarecommStorage.saveData(context, Constants.SharePrefrence.THEME_NAME, Constants.ThemesName.THEME_CREAM);
            changeAppTheme.changeTheme();
//            getMainActivity().setTheme(R.style.AppThemeCream);
//            getMainActivity().recreate();
            //getMainActivity().setContentView(R.layout.activity_main);

        }

        if (view == ivClose){
            onBack();
        }
        if (view == ivBack ){
            openHome();

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
                            initViewWithData();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetInfoResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }

    private void initViewWithData() {
        Picasso.get().load(Data.myUserData.photo).into(ivMyPhoto);
        etMyName.setText(Data.myUserData.name);
        etMyEmail.setText(Data.myUserData.email);
    }


    private void listofDialog(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);

        TextView text =  dialog.findViewById(R.id.dialogButtonOK);
        //text.setText("Android custom dialog example!");
        RecyclerView rvOption =  dialog.findViewById(R.id.rv_options);
        rvOption.setLayoutManager(layoutManager);

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getMainActivity());
    //    builderSingle.setIcon(R.mipmap.ic_launcher);
    //    builderSingle.setTitle("Select One Name:-");

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Take Picture");
        arrayList.add("Chose From Album");
        arrayList.add("Remove Current Picture");



        rvOption.setAdapter(new ChangeProfilePicItemAdapter(context,arrayList,getMainActivity(),dialog));

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void updateInfo() {
        UpdateRequestModel request = new UpdateRequestModel();
        UpdateRequestModel.UserModel user = new UpdateRequestModel.UserModel();
        user.email = etMyEmail.getText().toString();
        user.name = etMyName.getText().toString();
        request.user = user;
        String token = Data.token;
        Call<UpdateResponseModel> updateCall = ApiService.profile.update(token, request);
        updateCall.enqueue(new Callback<UpdateResponseModel>() {
            @Override
            public void onResponse(Call<UpdateResponseModel> call, Response<UpdateResponseModel> response) {
                if (response.body() != null) {
                    Toast.makeText(context, "User info updated succesfully.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateResponseModel> call, Throwable t) {

            }
        });
    }

}
