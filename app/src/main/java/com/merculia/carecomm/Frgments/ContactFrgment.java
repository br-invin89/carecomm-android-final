package com.merculia.carecomm.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters.CallItemAdapter;
import com.merculia.carecomm.Adapters.ContactItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Model.CallModel;
import com.merculia.carecomm.Model.ContactModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.Constants;
import com.merculia.carecomm.Utils.SpacesItemDecoration;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ContactFrgment extends BaseFragment implements View.OnClickListener {


    private ImageView ivCallBtn;
    private RecyclerView rvMakeCall;
    private ImageView ivScrollUp,ivScrollDown;
    private GridLayoutManager layoutManager;



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
        View view = inflater.inflate(R.layout.fragment_call, container, false);

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
        ivCallBtn = view.findViewById(R.id.iv_btn_makeCall);
        rvMakeCall = view.findViewById(R.id.rv_make_call);
        ivScrollUp = view.findViewById(R.id.iv_sv_up);
        ivScrollDown = view.findViewById(R.id.iv_sv_down);
    }

    @Override
    protected void inits() {
        ivCallBtn.setImageResource(R.mipmap.add_contact_btn);
        setAdapter();
    }

    @Override
    protected void setEvents() {
        ivCallBtn.setOnClickListener(this);
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
        list.add(new ContactModel("Daughter",R.drawable.main_photo_9,"View"));
        list.add(new ContactModel("Mom",R.drawable.main_photo_8,"View"));
        list.add(new ContactModel("Aunty",R.drawable.main_photo_6,"View"));
        list.add(new ContactModel("Mother Law",R.drawable.main_photo_7,"View"));
        list.add(new ContactModel("Daughter",R.drawable.main_photo_5,"View"));
        list.add(new ContactModel("Wife",R.drawable.main_photo_1,"View"));

        ContactItemAdapter menuItemAdapter = new ContactItemAdapter(getMainActivity(),context,list);

        rvMakeCall.setAdapter(menuItemAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view == ivCallBtn){
            getMainActivity().ReplaceFragmentWithBackstack(new SearchContactFrgment(),false,true);
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
