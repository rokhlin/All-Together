package com.selfapps.rav.alltogether.model;

import java.util.ArrayList;

/**
 * Json Example:
 * "Group1": {
     "Name": "<group_name>",
     "GroupId" : "<group_Id>",
     "Members":{
         "M1":{
             "Name":"<name>",
             "UID" : "<user_id>",
             "Role":"owner",---"member","observer"},
         "M2":{...},
         ....},
     "Events":{
         "Event1":{
             "Event-ld":<id long>,
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
        "Event2":{...},
        ....},
    "Group-messages":{
         "M1":{
             "Name":"<name>",
             "Text":"<text>",
             "Date":<timestamp long>},
         "M2":{...},
    ....},
 },
 ...

 */
public class Group {
    String name;
    String id;
    ArrayList<Member> members = new ArrayList<>();
    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Message> groupMessages = new ArrayList<>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }


    public Group(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
    public void addMember(Member member) {
        this.members.add(member);
    }
    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Message> getGroupMessages() {
        return groupMessages;
    }

    public void setGroupMessages(ArrayList<Message> groupMessages) {
        this.groupMessages = groupMessages;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", members=" + members +
                ", events=" + events +
                ", groupMessages=" + groupMessages +
                '}';
    }
}
