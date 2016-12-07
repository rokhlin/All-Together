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

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;
import static com.selfapps.rav.alltogether.utilites.DbPath._authEmail;
import static com.selfapps.rav.alltogether.utilites.DbPath._authUserId;
import static com.selfapps.rav.alltogether.utilites.DbPath._events;
import static com.selfapps.rav.alltogether.utilites.DbPath._groupReferences;
import static com.selfapps.rav.alltogether.utilites.DbPath._lastUpdate;

public class EventsFirebaseHelper {

    private final String TAG = this.getClass().getName();

    private final DatabaseReference db;//FirebaseDatabase.getInstance().getReference().child(_Groups).child(groupId)
    private List<Event> events = new LinkedList<>();


    public EventsFirebaseHelper(DatabaseReference db) {
        this.db = db;
        db.child(_events).child(_lastUpdate).setValue(System.currentTimeMillis());
    }


    public String addEvent(Event event){
        return setValue(db.child(_events),event);
    }


    public List<Event> retrieve(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"String s = "+s);
                Log.d(TAG,"Event retrieve dataSnapshot = "+dataSnapshot);
                if(dataSnapshot.getKey().equals(_events))
                        fetchReferences(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"String 2s = "+s);
                Log.d(TAG,"Event retrieve dataSnapshot = "+dataSnapshot);
                if(dataSnapshot.getKey().equals(_events))
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

            if(ds.getKey().equals("lastUpdate")) continue;//pass lastUpdate value
            Event e = ds.getValue(Event.class);
            e.setId(ds.getKey());
            Log.d(TAG,"event = "+ e);
            events.add(e);
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
