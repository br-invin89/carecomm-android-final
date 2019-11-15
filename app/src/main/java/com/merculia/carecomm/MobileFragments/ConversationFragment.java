package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.ConversationItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Chat.GetConversationsResponseModel;
import com.merculia.carecomm.Logics.Contact.GetContactsForConversationResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Utils.SpacesItemDecoration;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConversationFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivBack,ivClose;
    private LinearLayout ivMakeconv;
    private TextView screenTitle;
    private RecyclerView rvMakeCon;
    private RecyclerView.LayoutManager layoutManager;
    private List<GetConversationsResponseModel.ConversationModel> dataConversations = new ArrayList<GetConversationsResponseModel.ConversationModel>();

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
        View view = inflater.inflate(R.layout.fragment_conservation, container, false);

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
        ivMakeconv = view.findViewById(R.id.iv_makeconv);

    }


    @Override
    protected void inits() {
        ivClose.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        screenTitle.setText("Comm");
    }

    @Override
    protected void setEvents() {
        ivBack.setOnClickListener(this);
        ivMakeconv.setOnClickListener(this);
    }

    private void initViewWithData(){
        layoutManager = new LinearLayoutManager(getMainActivity());
        rvMakeCon.setLayoutManager(layoutManager);

        ConversationItemAdapter conversationItemAdapter = new ConversationItemAdapter(context, dataConversations, getMainActivity());

        rvMakeCon.setAdapter(conversationItemAdapter);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View view) {
      if (ivBack == view){
          openHome();
      }
      if (view == ivMakeconv){
          getMainActivity().ReplaceFragmentWithBackstack(new MakeConversationFragment(),false,true);
      }
    }

    private void loadData() {
        try {
            String token = Data.token;
            Call<GetConversationsResponseModel> getConversationsCall = ApiService.chat.getConversations(token);
            getConversationsCall.enqueue(new Callback<GetConversationsResponseModel>() {
                @Override
                public void onResponse(Call<GetConversationsResponseModel> call, Response<GetConversationsResponseModel> response) {
                    if (response.body() != null) {
                        dataConversations = response.body().conversations;
                        initViewWithData();
                    }
                }

                @Override
                public void onFailure(Call<GetConversationsResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }
}
