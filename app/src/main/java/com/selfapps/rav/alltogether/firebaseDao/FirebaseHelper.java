package com.selfapps.rav.alltogether.firebaseDao;


import com.google.firebase.database.DatabaseReference;
import com.selfapps.rav.alltogether.model.GroupReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<GroupReference> coordinatorGroups=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }



}
