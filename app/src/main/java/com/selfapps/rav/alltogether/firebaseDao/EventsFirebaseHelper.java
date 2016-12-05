package com.selfapps.rav.alltogether.firebaseDao;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selfapps.rav.alltogether.model.Event;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.GroupReference;
import com.selfapps.rav.alltogether.model.Member;

import java.util.LinkedList;
import java.util.List;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;
import static com.selfapps.rav.alltogether.utilites.DbPath._authEmail;
import static com.selfapps.rav.alltogether.utilites.DbPath._authUserId;
import static com.selfapps.rav.alltogether.utilites.DbPath._events;
import static com.selfapps.rav.alltogether.utilites.DbPath._groupReferences;
import static com.selfapps.rav.alltogether.utilites.DbPath._lastUpdate;

public class EventsFirebaseHelper {

    private final String TAG = this.getClass().getName();
    private final DatabaseReference db;
    private final DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
    private List<Event> events = new LinkedList<>();


    public EventsFirebaseHelper(DatabaseReference db) {
        this.db = db;
        db.child(_lastUpdate).setValue(System.currentTimeMillis());
    }




//    private String addGroupReference(GroupReference groupReference) {
//        DatabaseReference dbRef = db.child(_groupReferences);
//        return setValue(dbRef,groupReference);
//    }


    public String addEvent(Event event, String groupId){

        final DatabaseReference dbRef = dbRoot.child(_Groups).child(groupId).child(_events);
        final String key = setValue(dbRef,event);
        return key;
    }


    public List<Event> retreive(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"Event retreive dataSnapshot = "+dataSnapshot);
                fetchReferences(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"Event retreive dataSnapshot = "+dataSnapshot);
                fetchReferences(dataSnapshot);
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
        return events;
    }


    private void fetchReferences(DataSnapshot dataSnapshot)
    {
        events.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.d(TAG,"DataSnapshot ds = "+ds.toString());
            events.add(ds.getValue(Event.class));
        }
        Log.d(TAG,"events.size() = "+ events.size());
    }


    private String setValue(DatabaseReference dbRef, Object obj) {
        if(obj==null) {
            return null;}
        else {
            try
            {
                String key = dbRef.push().getKey();
                dbRef.child(key).setValue(obj);
                return key;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

}
