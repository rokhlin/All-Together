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

    public Member(String userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return userId.equals(member.userId);

    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
