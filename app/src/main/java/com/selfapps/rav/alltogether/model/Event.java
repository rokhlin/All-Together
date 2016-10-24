package com.selfapps.rav.alltogether.model;

import java.util.ArrayList;

/**
 * Json example:
 * "Event1":{
     "Event-ld":<id>,
     "Event-Name":"<event_name>",
     "Start-Date":<timestamp long>,
     "End-Date":<timestamp long>,
     "Status":<value boolean>,
     "Message": "<message>",
     "Geo": "<coordinates>",
     "EventMembers":{
         "M1":{
             "Name":"<name>",
             "IsHere" : "<boolean>" },
         "M2":{...},
     ...}
 },
 */
public class Event {
    String id;
    String name;
    long startDate;
    long endDate;
    boolean status;
    String message;
    String geo;
    ArrayList<EventMember> eventMembers = new ArrayList<>();

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public ArrayList<EventMember> getEventMembers() {
        return eventMembers;
    }

    public void setEventMembers(ArrayList<EventMember> eventMembers) {
        this.eventMembers = eventMembers;
    }
}
