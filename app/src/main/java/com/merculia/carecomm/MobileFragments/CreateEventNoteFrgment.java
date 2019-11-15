package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;


public class CreateEventNoteFrgment extends BaseFragment implements View.OnClickListener {


    private ImageView ivClose;
    private Button btnNext;
    private TextView screenTitle,txtValue,txtTitle;
    private EditText etInputData;
   // private boolean isFamily;

   /// private String txtTitleString,txtValueString;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // isFamily = getArguments().getBoolean(Constants.isFaimly);
            //txtTitleString = getArguments().getString(Constants.txtTitleString);
           // txtValueString = getArguments().getString(Constants.txtValueString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_note, container, false);

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
        btnNext = view.findViewById(R.id.btn_next);
        screenTitle = view.findViewById(R.id.screen_title);
        etInputData = view.findViewById(R.id.et_memo);
        txtValue = view.findViewById(R.id.txt_value);
        txtTitle = view.findViewById(R.id.txt_title);

    }
    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    protected void inits() {
        screenTitle.setText("Create Event");
      //  txtTitle.setText(txtTitleString);
       // txtValue.setText(txtValueString);

    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            onBack();
        }
        if (view == btnNext){
            loadDataFromView();
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventReviewFrgment(),false,true);
        }
    }

    private void loadDataFromView(){
        Data.creatingEvent.notes = etInputData.getText().toString();
    }
}
