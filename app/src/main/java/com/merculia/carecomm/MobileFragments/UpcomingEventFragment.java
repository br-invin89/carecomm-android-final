package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class UpcomingEventFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvScreentitle;
    private ImageView ivClose;
    private Button btnEdit;
    CircleImageView ivRelativePhoto;
    TextView tvRelativeName, tvRelationship, tvEventName, tvEventCategory,tvEventStartTime, tvEventEndTime, tvEventNotes;

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
        View view = inflater.inflate(R.layout.fragment_event, container, false);

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
        tvScreentitle = view.findViewById(R.id.screen_title);
        ivClose = view.findViewById(R.id.iv_close);
        btnEdit = view.findViewById(R.id.btn_edit);
        ivRelativePhoto = view.findViewById(R.id.iv_relative_photo);
        tvRelativeName = view.findViewById(R.id.tv_relative_name);
        tvRelationship = view.findViewById(R.id.tv_relationship);
        tvEventCategory = view.findViewById(R.id.tv_event_category);
        tvEventName = view.findViewById(R.id.tv_event_name);
        tvEventStartTime = view.findViewById(R.id.tv_event_start_time);
        tvEventEndTime = view.findViewById(R.id.tv_event_end_time);
        tvEventNotes = view.findViewById(R.id.tv_event_notes);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    protected void inits() {
        tvScreentitle.setText("Upcoming Events");
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (ivClose == view) {
            getMainActivity().onBackPressed();
        }
        if (view == btnEdit){
           getMainActivity().ReplaceFragmentWithBackstack(new UpcomingEventDetailFragment(),false,true);
        }
    }

    private void initViewWithData() {
        if (Data.selectedUpcomingEvent.relativeRef.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(ivRelativePhoto);
        else
            Picasso.get().load(Data.selectedUpcomingEvent.relativeRef.photo).into(ivRelativePhoto);
        tvRelativeName.setText(Data.selectedUpcomingEvent.relativeRef.name);
        tvRelationship.setText(Data.selectedUpcomingEvent.relationship);
        tvEventName.setText(Data.selectedUpcomingEvent.name);
        tvEventCategory.setText(Data.selectedUpcomingEvent.category);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM d");
        Date dStartDate = null, dEndDate = null;

        try {
            dStartDate = dateFormat.parse(Data.selectedUpcomingEvent.startDate);
            dEndDate = dateFormat.parse(Data.selectedUpcomingEvent.endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startDate = dateFormat1.format(dStartDate);
        String endDate = dateFormat1.format(dEndDate);

        tvEventStartTime.setText(startDate+" "+Data.selectedUpcomingEvent.startTime);
        tvEventEndTime.setText(endDate+" "+Data.selectedUpcomingEvent.endTime);
        tvEventNotes.setText(Data.selectedUpcomingEvent.notes);
    }
}
