package com.selfapps.rav.alltogether.firebaseDao;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.BaseActivity;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.GroupReference;
import com.selfapps.rav.alltogether.model.Member;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;


public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private final String userUID;
    private final DatabaseReference db;
    private final DatabaseReference userRef;
    LinkedList<String> groupKeys = new LinkedList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
        userUID = BaseActivity.authUser.getUid();
        userRef = db.child("Users").child(userUID);
    }


    public String addGroupReference(GroupReference groupReference) {
        DatabaseReference dbRef = userRef.child("groupReferenses");
        return setValue(dbRef,groupReference);
    }


    public String addGroup(Group group){
        DatabaseReference dbRef = db.child("groups");
        String key = setValue(dbRef,group);
        //addMembers(group.getMembers(),dbRef.child(key));
        return key;
    }

    private void addMembers(ArrayList<Member> members, DatabaseReference dbRef) {
        for (Member m : members ) {
            addMember(m,dbRef);
        }
    }

    private String addMember(Member m, DatabaseReference db) {
        DatabaseReference dbRef = db.child("members");
        return setValue(dbRef,m);
    }


    private void fetchGroupData(DataSnapshot dataSnapshot)
    {
        //groupKeys.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
           //GroupReference coordinateGroup = ds.child("groupReferenses").getValue(GroupReference.class);
            String key = ds.getKey();
            if(groupKeys.contains(key))
                continue;
            groupKeys.add(key);
            Log.d(TAG,"groupKeys.getLast() = "+ groupKeys.getLast());

        }
    }


    private String setValue(DatabaseReference dbRef, Object obj) {
        if(obj==null) {
            return null;}
        else {
            try
            {
                String key = dbRef.push().getKey();
                dbRef.child(key ).setValue(obj);
                return key;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

}
