package com.selfapps.rav.alltogether.firebaseDao;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.BaseActivity;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.GroupReference;
import com.selfapps.rav.alltogether.model.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private final String userUID;
    private final DatabaseReference db;
    private final DatabaseReference userRef;
    ArrayList<GroupReference> groupReferences = new ArrayList<>();
    LinkedHashMap<String,Integer> groupReferencesLinks = new LinkedHashMap<>();

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


    private void fetchGroupReferences (DataSnapshot dataSnapshot) {

        GroupReference groupReference = dataSnapshot.getValue(GroupReference.class);
        Log.d(TAG,"groupReference = "+groupReference.toString());
        groupReferences.add(groupReference);
        groupReferencesLinks.put(dataSnapshot.getKey(),groupReferences.size()-1);
    }

    private void updateGroupReferences(DataSnapshot dataSnapshot, String s) {
        GroupReference groupReference = dataSnapshot.getValue(GroupReference.class);
            if(groupReferencesLinks.containsKey(dataSnapshot.getKey())) {
                int index = groupReferencesLinks.get(dataSnapshot.getKey());
                groupReferences.set(index, groupReference);
            }
            else
                fetchGroupReferences(dataSnapshot);
    }

    public ArrayList<GroupReference> retreive(){

        userRef.child("groupReferenses").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"dataSnapshot.toString() = "+dataSnapshot.toString() +" __s="+s);
                updateGroupReferences(dataSnapshot,s);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateGroupReferences(dataSnapshot,s);
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

    private String setGroupValue(DatabaseReference dbRef, Group group) {
        if(group==null) {
            return null;}
        else {
            try
            {
                final String key = dbRef.push().getKey();
                dbRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Group updGroup = dataSnapshot.getValue(Group.class);
                        for (Member m: updGroup.getMembers()) {
                            if(m.equals(new Member(key))){
                                DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child(userUID).child("groupReferenses");
                                String refKey = dref.push().getKey();
                                dref.child(refKey).setValue(new GroupReference(key,updGroup.getName(),m.getRole()));
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dbRef.child(key).setValue(group);
                return key;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }


}
