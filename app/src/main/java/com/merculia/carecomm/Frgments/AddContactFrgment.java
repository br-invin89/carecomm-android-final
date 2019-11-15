package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.MobileFragments.ContactMobileFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Contact.SearchResponseModel;
import com.merculia.carecomm.Logics.Contact.SendContactRequestModel;
import com.merculia.carecomm.Logics.Data;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddContactFrgment extends BaseFragment implements View.OnClickListener {


    private Button btnAdd;

    private ImageView ivClose;
    private TextView screenTitle;
    private Spinner spinner;

    private CircleImageView ivMyPhoto;
    private TextView tvMyName, tvMyUsername, tvMyUsername1;

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
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        setView(view);
        inits();
        setEvents();
        initViewWithData();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void setView(View view) {
        ivClose = view.findViewById(R.id.iv_close);
        btnAdd = view.findViewById(R.id.btn_add);
        screenTitle = view.findViewById(R.id.screen_title);
        spinner = view.findViewById(R.id.spinner_contact);
        ivMyPhoto = view.findViewById(R.id.img_my_photo);
        tvMyName = view.findViewById(R.id.txt_my_name);
        tvMyUsername = view.findViewById(R.id.txt_my_username);
        tvMyUsername1 = view.findViewById(R.id.txt_my_username1);
    }


    @Override
    protected void inits() {
        setSpinnerAdapter(spinner,R.array.add_contact_selection);
        screenTitle.setText("Add a Contact");
        if (getMainActivity().isTablet(context)){
            btnAdd.setText("Add");
        }else {
            btnAdd.setText("Send a Request");
        }

    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getMainActivity().isTablet(context)){
            getMainActivity().hideMenu();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            getMainActivity().onBackPressed();
        }

        if (view == btnAdd){
            sendContactRequest();
        }
    }

    private void initViewWithData() {
        SearchResponseModel.User user = Data.searchedUser;
        if (user.photo.equals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivMyPhoto);
        else
            Picasso.get().load(user.photo).into(ivMyPhoto);
        tvMyName.setText(user.name);
        tvMyUsername.setText(user.username);
        tvMyUsername1.setText(user.username);
    }

    private void sendContactRequest() {
        String relationship = spinner.getSelectedItem().toString();
        String token = Data.token;
        SendContactRequestModel request = new SendContactRequestModel();
        request.userId = Data.searchedUser._id;
        request.relationship = relationship;
        Call<Void> sendContactRequestCall = ApiService.contact.sendContactRequest(token, request);
        sendContactRequestCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (getMainActivity().isTablet(context)) {
                    getMainActivity().onBackPressed();
                }else {
                    getMainActivity().ReplaceFragment(new ContactMobileFragment());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}
