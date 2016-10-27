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
    String userId; //Real User id
    String name;//Real User name
    String role;

    public Member() {

    }

    public Member(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
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
