package com.merculia.carecomm.Logics;

import com.merculia.carecomm.Logics.Contact.ContactModel;
import com.merculia.carecomm.Logics.Contact.SearchResponseModel;
import com.merculia.carecomm.Logics.Event.DailyEventResponseModel;
import com.merculia.carecomm.Logics.Event.EventModel;
import com.merculia.carecomm.Logics.Event.UpcomingEventResponseModel;
import com.merculia.carecomm.Logics.Profile.UserModel;

public class Data {
    public static String token = "";
    public static UserModel myUserData;

    public static ContactModel selectedContact;
    public static SearchResponseModel.User searchedUser;

    public static EventModel creatingEvent = new EventModel();

    public static DailyEventResponseModel.EventModel selectedDailyEvent = null;
    public static UpcomingEventResponseModel.EventModel selectedUpcomingEvent = null;
}
