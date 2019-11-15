package com.merculia.carecomm.MobileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merculia.carecomm.Adapters2.ContactsItemAdapter;
import com.merculia.carecomm.BaseActivity.BaseFragment;
import com.merculia.carecomm.Frgments.SearchContactFrgment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.ApiService;
import com.merculia.carecomm.Logics.Contact.ContactModel;
import com.merculia.carecomm.Logics.Contact.GetContactsResponseModel;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Utils.SpacesItemDecoration;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactMobileFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivBack,ivClose;
    private LinearLayout ivAddContact;
    private TextView screenTitle;
    private RecyclerView rvMakeCon;
    private RecyclerView.LayoutManager layoutManager;
    private List<ContactModel> dataContacts;

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
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

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
        ivAddContact = view.findViewById(R.id.iv_add_contact);
    }


    @Override
    protected void inits() {
        ivClose.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        screenTitle.setText("Contacts");
        // setAdapter();
    }

    @Override
    protected void setEvents() {
        ivBack.setOnClickListener(this);
        ivAddContact.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initViewWithData(){
        layoutManager = new GridLayoutManager(getMainActivity(),2);
        rvMakeCon.addItemDecoration(new SpacesItemDecoration(35));
        rvMakeCon.setLayoutManager(layoutManager);
        /*
        ArrayList<ContactForConversationModel> list = new ArrayList<>();
        list.add(new ContactForConversationModel("Daughter",R.drawable.main_photo_9,true));
        list.add(new ContactForConversationModel("Mom",R.drawable.main_photo_8,true));
        list.add(new ContactForConversationModel("Aunty",R.drawable.main_photo_6,true));
        list.add(new ContactForConversationModel("Mother Law",R.drawable.main_photo_7,true));
        list.add(new ContactForConversationModel("Daughter",R.drawable.main_photo_5,true));
        list.add(new ContactForConversationModel("Wife",R.drawable.main_photo_1,true));
        list.add(new ContactForConversationModel("Wife223",R.drawable.main_photo_1,true));
         */

        ContactsItemAdapter contactsAdapter = new ContactsItemAdapter(context, dataContacts, getMainActivity());

        rvMakeCon.setAdapter(contactsAdapter);
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
        if (ivAddContact == view){
            getMainActivity().ReplaceFragmentWithBackstack(new SearchContactFrgment(),false,true);
        }
    }

    private void loadData() {
        try {
            String token = Data.token;
            Call<GetContactsResponseModel> getContactsCall = ApiService.contact.getContacts(token);
            getContactsCall.enqueue(new Callback<GetContactsResponseModel>() {
                @Override
                public void onResponse(Call<GetContactsResponseModel> call, Response<GetContactsResponseModel> response) {
                    if (response.body() != null) {
                        dataContacts = response.body().contacts;
                        initViewWithData();
                    }
                }

                @Override
                public void onFailure(Call<GetContactsResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }
}
