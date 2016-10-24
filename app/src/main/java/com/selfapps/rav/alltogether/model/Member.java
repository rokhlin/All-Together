package com.selfapps.rav.alltogether.model;

/**
 * Json Example:
 * "Member1":{
     "Name":"<name>",
     "Role":"owner",            ---"member","observer",
 },
 ...
 */
public class Member {
    String id; //Real User id
    String name;//Real User name
    String role;

    public Member() {

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
