package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Contact.SearchResponseModel;
import com.merculia.carecomm.Logics.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchContactFrgment extends BaseFragment implements View.OnClickListener {
    private Button btnSearch;
    private ImageView ivClose;
    private EditText etUsername;

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
        View view = inflater.inflate(R.layout.fragment_search_contact, container, false);

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
        ivClose = view.findViewById(R.id.iv_close);
        btnSearch = view.findViewById(R.id.btn_search);
        etUsername = view.findViewById(R.id.et_input_data);
    }

    @Override
    protected void inits() {


    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getMainActivity().isTablet(context)){
            getMainActivity().hideMenu();
        }
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }


    @Override
    public void onClick(View view) {
        if (view == ivClose){
            getMainActivity().onBackPressed();
        }

        if (view == btnSearch){
            searchByUsername();
        }
    }

    private void searchByUsername() {
        String username = etUsername.getText().toString();
        String token = Data.token;
        Call<SearchResponseModel> searchCall = ApiService.contact.searchByUsername(token, username);
        searchCall.enqueue(new Callback<SearchResponseModel>() {
            @Override
            public void onResponse(Call<SearchResponseModel> call, Response<SearchResponseModel> response) {
                if (response.body() != null){
                    Data.searchedUser = response.body().user;
                    getMainActivity().ReplaceFragmentWithBackstack(new AddContactFrgment(),false,true);
                } else {
                    Toast.makeText(getContext(), "Can't search by that username or already linked.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponseModel> call, Throwable t) {

            }
        });
    }
}
