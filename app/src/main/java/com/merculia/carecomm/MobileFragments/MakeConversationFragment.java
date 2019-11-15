package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.ContactsForConversationItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Contact.GetContactsForConversationResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Contact.ContactForConversationModel;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MakeConversationFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivBack,ivClose;
    private TextView screenTitle;
    private RecyclerView rvMakeCon;
    private RecyclerView.LayoutManager layoutManager;
    private List<ContactForConversationModel> dataConversations = new ArrayList<ContactForConversationModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_conservation, container, false);

        setView(view);
        inits();
        setEvents();
        loadData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setView(View view) {
        ivBack = view.findViewById(R.id.iv_back);
        ivClose = view.findViewById(R.id.iv_close);
        screenTitle = view.findViewById(R.id.screen_title);
        rvMakeCon = view.findViewById(R.id.rv_make_con);
    }


    @Override
    protected void inits() {
        ivClose.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        screenTitle.setText("Make a Conversation");
//        initViewWithData();
    }

    @Override
    protected void setEvents() {
        ivBack.setOnClickListener(this);
    }

    private void initViewWithData(){
        layoutManager = new GridLayoutManager(getMainActivity(),2);
        rvMakeCon.addItemDecoration(new SpacesItemDecoration(35));
        rvMakeCon.setLayoutManager(layoutManager);

        ContactsForConversationItemAdapter menuItemAdapter = new ContactsForConversationItemAdapter(context, dataConversations, getMainActivity());

        rvMakeCon.setAdapter(menuItemAdapter);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
      if (ivBack == view){
          onBack();
      }
    }

    private void loadData() {
        try {
            String token = Data.token;
            Call<GetContactsForConversationResponseModel> getContactsCall = ApiService.chat.getContactsForConversation(token);
            getContactsCall.enqueue(new Callback<GetContactsForConversationResponseModel>() {
                @Override
                public void onResponse(Call<GetContactsForConversationResponseModel> call, Response<GetContactsForConversationResponseModel> response) {
                    if (response.body() != null) {
                        dataConversations = response.body().contacts;
                        initViewWithData();
                    }
                }

                @Override
                public void onFailure(Call<GetContactsForConversationResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }
}
