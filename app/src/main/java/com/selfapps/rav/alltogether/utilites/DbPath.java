package com.selfapps.rav.alltogether.utilites;


import com.google.firebase.database.DatabaseReference;
import com.selfapps.rav.alltogether.BaseActivity;

public abstract class DbPath {

    public static final String _authUserId = BaseActivity.authUser.getUid();
    public static final String _authUserName = BaseActivity.authUser.getDisplayName();
    public static final String _authEmail= BaseActivity.authUser.getEmail();
    public static final String _Users = "Users";
    public static final String _Groups = "Groups";
    public static final String _groupReferences = "groupReferences";
    public static final String _members = "members";
    public static final String _name = "name";
    public static final String _id = "id";
    public static final String _role = "role";
    public static final String _lastUpdate = "lastUpdate";




}
