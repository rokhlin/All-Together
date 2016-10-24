package com.selfapps.rav.alltogether.model;


import java.util.ArrayList;

public class User {
    String name;
    String id;
    ArrayList<GroupReference> groupCoordinators = new ArrayList<>();

    public User() {

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

    public ArrayList<GroupReference> getGroupCoordinators() {
        return groupCoordinators;
    }

    public void setGroupCoordinators(ArrayList<GroupReference> groupCoordinators) {
        this.groupCoordinators = groupCoordinators;
    }
}
