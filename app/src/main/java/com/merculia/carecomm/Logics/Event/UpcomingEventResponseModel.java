package com.merculia.carecomm.Logics.Event;

import java.util.List;

public class UpcomingEventResponseModel {
    public List<EventModel> events;

    public static class EventModel {
        public UserModel relativeRef;
        public String relationship;
        public String name;
        public String category;
        public String startDate;
        public String startTime;
        public String endDate;
        public String endTime;
        public String repeat;
        public String notes;
        public boolean isAllDay;
    }

    public static class UserModel {
        public String _id;
        public String name;
        public String username;
        public String email;
        public String photo;
    }
}
