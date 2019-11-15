package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;


public class RegistrationFrgment extends BaseFragment {


    private TextView txtTitle,txtValue;
    private EditText etInputValue;

    private String txtTitleString,txtValueString;
   private int inputType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            txtTitleString = getArguments().getString(Constants.txtTitleString);
            txtValueString = getArguments().getString(Constants.txtValueString);
            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

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
        txtTitle = view.findViewById(R.id.txt_title);
        txtValue = view.findViewById(R.id.txt_value);
        etInputValue = view.findViewById(R.id.et_input_data);
    }

    @Override
    protected void inits() {
        txtTitle.setText(txtTitleString);
        txtValue.setText(txtValueString);
        etInputValue.setInputType(inputType);
    }

    @Override
    protected void setEvents() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }



}
