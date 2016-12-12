package com.selfapps.rav.alltogether.firebaseDao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.selfapps.rav.alltogether.model.Event;
import com.selfapps.rav.alltogether.model.Member;

import java.util.LinkedList;
import java.util.List;

import static com.selfapps.rav.alltogether.utilites.DbPath._events;
import static com.selfapps.rav.alltogether.utilites.DbPath._members;


public class MembersFirebaseHelper {
    private static final boolean DEBUG = true;
    private final String TAG = this.getClass().getName();
    private DatabaseReference db;//FirebaseDatabase.getInstance().getReference().child(_Groups).child(groupId)
    private List<Member> members = new LinkedList<>();

    public MembersFirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public List<Member> retrieve(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(DEBUG) Log.d(TAG,"String s = "+s);
                if(DEBUG) Log.d(TAG,"Member retrieve dataSnapshot = "+dataSnapshot);
                if(dataSnapshot.getKey().equals(_members))
                    fetchReferences(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(DEBUG) Log.d(TAG,"String 2s = "+s);
                if(DEBUG) Log.d(TAG,"Member retrieve dataSnapshot = "+dataSnapshot);
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
        return members;
    }


    private void fetchReferences(DataSnapshot dataSnapshot)
    {
        members.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if(DEBUG) Log.d(TAG,"DataSnapshot ds = "+ds.toString());

            if(ds.getKey().equals("lastUpdate")) continue;//pass lastUpdate value
            Member m = ds.getValue(Member.class);
            m.setUserId(ds.getKey());
            if(DEBUG) Log.d(TAG,"member = "+ m);
            members.add(m);
        }
        Log.d(TAG,"members.size() = "+ members.size());
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
