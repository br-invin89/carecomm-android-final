package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.ContactItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.ContactModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import com.merculia.carecomm.Utils.SpacesItemDecoration;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SelectContactFrgment extends BaseFragment implements View.OnClickListener {


    private TextView screenTitle;
    private ImageView ivClose;
    private RecyclerView rvMakeCall;
    private ImageView ivScrollUp,ivScrollDown;
    private GridLayoutManager layoutManager;
    private String buttonTitle,screenTitleString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            buttonTitle = getArguments().getString(Constants.Button_Title);
            screenTitleString = getArguments().getString(Constants.txtTitleString);
//            inputType = getArguments().getInt(Constants.inputType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_contact, container, false);

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
        rvMakeCall = view.findViewById(R.id.rv_make_call);
        screenTitle = view.findViewById(R.id.screen_title);
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);
    }

    @Override
    protected void inits() {
        screenTitle.setText(screenTitleString);
        setAdapter();
    }

    @Override
    protected void setEvents() {
        ivClose.setOnClickListener(this);
        ivScrollUp.setOnClickListener(this);
        ivScrollDown.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
    private void setAdapter(){
        layoutManager = new GridLayoutManager(getMainActivity(),3);
        rvMakeCall.addItemDecoration(new SpacesItemDecoration(15));
        rvMakeCall.setLayoutManager(layoutManager);
        ArrayList<ContactModel> list = new ArrayList<>();
        list.add(new ContactModel("Daughter",R.drawable.main_photo_9,buttonTitle));
        list.add(new ContactModel("Mom",R.drawable.main_photo_8,buttonTitle));
        list.add(new ContactModel("Aunty",R.drawable.main_photo_6,buttonTitle));
        list.add(new ContactModel("Mother Law",R.drawable.main_photo_7,buttonTitle));
        list.add(new ContactModel("Daughter",R.drawable.main_photo_5,buttonTitle));
        list.add(new ContactModel("Wife",R.drawable.main_photo_1,buttonTitle));

        ContactItemAdapter menuItemAdapter = new ContactItemAdapter(getMainActivity(),context,list);

        rvMakeCall.setAdapter(menuItemAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view == ivClose){
            getMainActivity().onBackPressed();
        }
        if (view == ivScrollUp){
            int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemIndex > 0) {
                layoutManager.smoothScrollToPosition(rvMakeCall,null,firstVisibleItemIndex-1);
            }
        }

        if (view == ivScrollDown){
            int totalItemCount = rvMakeCall.getAdapter().getItemCount();
            if (totalItemCount <= 0) return;
            int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();

            if (lastVisibleItemIndex >= totalItemCount) return;
            layoutManager.smoothScrollToPosition(rvMakeCall,null,lastVisibleItemIndex+1);

        }
    }
}
