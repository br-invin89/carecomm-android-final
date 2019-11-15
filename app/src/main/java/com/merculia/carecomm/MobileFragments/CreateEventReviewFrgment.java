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
import android.widget.Spinner;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Event.CreateEventRequestModel;
import com.merculia.carecomm.Logics.Profile.RelativeModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventReviewFrgment extends BaseFragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    private ImageView ivClose;
    private Button btnNext;
    private TextView screenTitle;
    private Spinner spFamily, spCategory, spRepeat;
    private EditText etName, etStartTime, etEndTime, etNotes;

    private String[] dataCategories = { "Medical", "Travel", "Task", "Meeting" };
    private String[] dataRepeats = { "Never", "Yes", "2 Times" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_event, container, false);

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
        btnNext = view.findViewById(R.id.btn_edit);
        screenTitle = view.findViewById(R.id.screen_title);
        spFamily = view.findViewById(R.id.sp_family);
        spCategory = view.findViewById(R.id.sp_category);
        spRepeat = view.findViewById(R.id.sp_repeat);
        etName = view.findViewById(R.id.et_name);
        etStartTime = view.findViewById(R.id.et_start_time);
        etEndTime = view.findViewById(R.id.et_end_time);
        etNotes = view.findViewById(R.id.et_notes);
    }

    @Override
    protected void inits() {
        screenTitle.setText("Create Event");

    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
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
            createEvent();

        }
    }

    private void initViewWithData() {
        etName.setText(Data.creatingEvent.name);
        etStartTime.setText(Data.creatingEvent.startTime);
        etEndTime.setText(Data.creatingEvent.endTime);
        etNotes.setText(Data.creatingEvent.notes);

        // family spinner
        int selectedPosition = 0; int i =0;
        ArrayList<String> items = new ArrayList<String>();
        for (RelativeModel relative: Data.myUserData.relatives) {
            if (relative.userRef._id.equals(Data.creatingEvent.relativeRef))
                selectedPosition = i;
            i++;
            items.add(relative.userRef.name+" ("+relative.relationship+")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getMainActivity(), R.layout.spinner_item, items);
        spFamily.setAdapter(adapter);
        spFamily.setSelection(selectedPosition);

        // category spinner
        selectedPosition = 0; i =0;
        items = new ArrayList<String>();
        for (String category: dataCategories) {
            if (category.equals(Data.creatingEvent.category))
                selectedPosition = i;
            i++;
            items.add(category);
        }
        adapter = new ArrayAdapter<String>(getMainActivity(), R.layout.spinner_item, items);
        spCategory.setAdapter(adapter);
        spCategory.setSelection(selectedPosition);

        // repeat spinner
        selectedPosition = 0; i =0;
        items = new ArrayList<String>();
        for (String repeat: dataRepeats) {
            if (repeat.equals(Data.creatingEvent.repeat))
                selectedPosition = i;
            i++;
            items.add(repeat);
        }
        adapter = new ArrayAdapter<String>(getMainActivity(), R.layout.spinner_item, items);
        spRepeat.setAdapter(adapter);
        spRepeat.setSelection(selectedPosition);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spFamily) {
            RelativeModel relative = Data.myUserData.relatives.get(i);
            Data.creatingEvent.relativeRef = relative.userRef._id;
        } else if (adapterView == spCategory) {
            Data.creatingEvent.category = dataCategories[i];
        } else if (adapterView == spRepeat) {
            Data.creatingEvent.repeat = dataRepeats[i];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void createEvent() {
        CreateEventRequestModel request = new CreateEventRequestModel();
        request.event = Data.creatingEvent;
        String token = Data.token;
        Call<Void> createEventCall = ApiService.event.createEvent(token, request);
        createEventCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getMainActivity().ReplaceFragment(new EventMobileFragment());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
