package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;


public class CreateEventInputNameFragment extends BaseFragment implements View.OnClickListener {
    private ImageView ivClose;
    private Button btnNext;
    private TextView screenTitle,txtValue,txtTitle;
    private EditText etInputData;
    private RelativeLayout layoutFamilyView;
    private String txtTitleString,txtValueString;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtTitleString = "Event Name";
        txtValueString = "Enter Event Name";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

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
        etInputData = view.findViewById(R.id.et_input_data);
        layoutFamilyView = view.findViewById(R.id.layout_family_view);
        txtValue = view.findViewById(R.id.txt_value);
        txtTitle = view.findViewById(R.id.txt_title);
    }

    @Override
    protected void inits() {
        screenTitle.setText("Create Event");
        txtTitle.setText(txtTitleString);
        txtValue.setText(txtValueString);
        layoutFamilyView.setVisibility(View.GONE);
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
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    public void onClick(View view) {
        if (view == ivClose){
            onBack();
        }
        if (view == btnNext){
            if (etInputData.getText().toString().equals("")){
                Toast.makeText(getMainActivity(), "Please insert event name.", Toast.LENGTH_SHORT).show();
                return;
            }
            Data.creatingEvent.name = etInputData.getText().toString();
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventSelectCatagoryFragment(),false,true);
        }
    }
}
