package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Profile.RelativeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CreateEventSelectFamilyFragment extends BaseFragment implements View.OnClickListener, Spinner.OnItemSelectedListener {
    private ImageView ivClose;
    private Button btnNext;
    private TextView screenTitle,txtValue,txtTitle;
    private EditText etInputData;
    private RelativeLayout layoutFamilyView;
    private Spinner spFamily;
    private CircleImageView ivPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

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
        btnNext = view.findViewById(R.id.btn_next);
        screenTitle = view.findViewById(R.id.screen_title);
        etInputData = view.findViewById(R.id.et_input_data);
        layoutFamilyView = view.findViewById(R.id.layout_family_view);
        txtValue = view.findViewById(R.id.txt_value);
        txtTitle = view.findViewById(R.id.txt_title);
        spFamily = view.findViewById(R.id.sp_family);
        ivPhoto = view.findViewById(R.id.iv_photo);
    }

    @Override
    protected void inits() {
        screenTitle.setText("Create Event");
        etInputData.setVisibility(View.GONE);
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        spFamily.setOnItemSelectedListener(this);
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
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventInputNameFragment(), false, true);
        }
    }

    private void initViewWithData() {
        // family spinner
        ArrayList<String> items = new ArrayList<String>();
        for (RelativeModel relative: Data.myUserData.relatives) {
            items.add(relative.userRef.name+" ("+relative.relationship+")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getMainActivity(), R.layout.spinner_item, items);
        spFamily.setAdapter(adapter);
        // if relative doesn't exists, not able to go next
        if (Data.myUserData.relatives.isEmpty()) {
            btnNext.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spFamily) {
            RelativeModel relative = Data.myUserData.relatives.get(i);
            Data.creatingEvent.relativeRef = relative.userRef._id;
            Data.creatingEvent.relationship = relative.relationship;
            if (relative.userRef.photo.equals(""))
                Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivPhoto);
            else
                Picasso.get().load(relative.userRef.photo).into(ivPhoto);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
