package com.selfapps.rav.alltogether.firebaseDao;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.selfapps.rav.alltogether.model.GroupReference;

import java.util.ArrayList;

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    DatabaseReference db;

    ArrayList<GroupReference> coordinatorGroups=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }


    public boolean addGroupReference(GroupReference groupReference) {
        Boolean saved=null;
        DatabaseReference dbRef = db.child("groupCoordinators");
        if(groupReference==null) {
            return false;}
        else {
            try
            {
                dbRef.push().setValue(groupReference);
                return true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }


    public ArrayList<GroupReference> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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
        return coordinatorGroups;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        coordinatorGroups.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
           GroupReference coordinateGroup = ds.child("0b7BDBFNWvXnt2h380VP8tZPotE2").child("groupCoordinators").getValue(GroupReference.class);
            Log.d(TAG,"coordinatorGroups.size() = "+coordinatorGroups.size());
            coordinatorGroups.add(coordinateGroup);
        }
    }

}
