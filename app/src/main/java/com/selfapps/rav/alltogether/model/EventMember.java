package com.selfapps.rav.alltogether.model;

/**
 * Json example:
 * "EventMember":{
     "Name":"<name>",
     "IsHere" : "<boolean>"
   },
 */
public class EventMember {
    String id; // real User Id from Group Members
    String name; // real User name from Group Members
    boolean isHere;

    public EventMember() {
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

    public boolean isHere() {
        return isHere;
    }

    public void setHere(boolean here) {
        isHere = here;
    }
}
