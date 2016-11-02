package com.selfapps.rav.alltogether.firebaseDao;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.GroupReference;
import com.selfapps.rav.alltogether.model.Member;

import java.util.LinkedList;
import java.util.List;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;
import static com.selfapps.rav.alltogether.utilites.DbPath._authEmail;
import static com.selfapps.rav.alltogether.utilites.DbPath._authUserId;
import static com.selfapps.rav.alltogether.utilites.DbPath._groupReferences;
import static com.selfapps.rav.alltogether.utilites.DbPath._lastUpdate;

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private final DatabaseReference db;
    private final DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
    private List<GroupReference> groupReferences = new LinkedList<>();


    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
        db.child(_lastUpdate).setValue(System.currentTimeMillis());
    }




    private String addGroupReference(GroupReference groupReference) {
        DatabaseReference dbRef = db.child(_groupReferences);
        return setValue(dbRef,groupReference);
    }


    public String addGroup(Group group){
        group.addMember(new Member(_authUserId,_authEmail,"coordinator"));
        final DatabaseReference dbRef = dbRoot.child(_Groups);
        final String key = setValue(dbRef,group);
        addGroupReference(new GroupReference(key,group.getName(),"coordinator"));
        return key;
    }


    public List<GroupReference> retreive(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchGroupReferences(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchGroupReferences(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return groupReferences;
    }


    private void fetchGroupReferences(DataSnapshot dataSnapshot)
    {
        groupReferences.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            groupReferences.add(ds.getValue(GroupReference.class));
        }
        Log.d(TAG,"groupReferences.size() = "+ groupReferences.size());
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
