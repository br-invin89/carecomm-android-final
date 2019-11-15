package com.merculia.carecomm.Model;

public class EventModel {
    private String eventTitle;
    private String eventStart;
    private String eventEnd;

    public EventModel(String eventTitle, String eventStart, String eventEnd) {
        this.eventTitle = eventTitle;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventStart() {
        return eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }
}
