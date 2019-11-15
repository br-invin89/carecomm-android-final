package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.EventCategoryItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.EventCategoryModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateEventSelectCatagoryFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivClose;
    private Button btnNext,btnBack;
    private TextView screenTitle;
    private RecyclerView rvCategory;
    private LinearLayoutManager layoutManager;
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
        View view = inflater.inflate(R.layout.fragment_event_catagory, container, false);

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
        btnBack = view.findViewById(R.id.btn_back);
        rvCategory = view.findViewById(R.id.rv_category);

    }


    @Override
    protected void inits() {
        screenTitle.setText("Create Event");
        setAdapter();
    }

    @Override
    protected void setEvents() {
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }
    private void setAdapter(){
        layoutManager = new LinearLayoutManager(getMainActivity());
        rvCategory.setLayoutManager(layoutManager);
        final ArrayList<EventCategoryModel> list = new ArrayList<>();
        list.add(new EventCategoryModel("Medical"));
        list.add(new EventCategoryModel("Travel"));
        list.add(new EventCategoryModel("Task"));
        list.add(new EventCategoryModel("Meeting"));
        EventCategoryItemAdapter menuItemAdapter = new EventCategoryItemAdapter(context,list,0);
        menuItemAdapter.setItemClickListener(new EventCategoryItemAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventCategoryModel cat = list.get(position);
                Data.creatingEvent.category = cat.getEventCategory();
            }
        });

        rvCategory.setAdapter(menuItemAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideMenu();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == btnBack || view == ivClose){
            onBack();
        }
        if (view == btnNext){
            getMainActivity().ReplaceFragmentWithBackstack(new CreateEventCalendarFragment(),false,true);
        }
    }
}
