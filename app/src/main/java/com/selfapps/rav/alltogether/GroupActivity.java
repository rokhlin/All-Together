package com.selfapps.rav.alltogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.Adapters.SectionsPagerAdapter;
import com.selfapps.rav.alltogether.model.Event;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.Member;

import java.util.ArrayList;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;
import static com.selfapps.rav.alltogether.utilites.DbPath._events;
import static com.selfapps.rav.alltogether.utilites.DbPath._lastUpdate;
import static com.selfapps.rav.alltogether.utilites.DbPath._members;

public class GroupActivity extends AppCompatActivity {
    private String TAG = this.getClass().getName();
    private String groupId;
    private String groupName;
    private String groupRole;
    private Group group;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();

        groupId = intent.getStringExtra("groupId");
        groupRole = intent.getStringExtra("groupRole");
        groupName = intent.getStringExtra("groupName");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(_Groups);//.child(groupId)


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG," dataSnapshot GROUP = " + dataSnapshot.child(groupId).child(_events));
                Group group = new Group();
                group.setId(groupId);
                group.setName(dataSnapshot.child(groupId).child("name").getValue(String.class));

                ArrayList<Member> members = fillMembers(dataSnapshot.child(groupId).child(_members));
                group.setMembers(members);
                Log.d(TAG,"members = " + members.size());

                ArrayList<Event> events = fillEvents(dataSnapshot.child(groupId).child(_events));
                Log.d(TAG,"events = " + events.size());
                group.setEvents(events);
                setGroup(group);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setGroup(Group group) {
        Log.d(TAG,"MARKER--------------------------------------------G");
        this.group = group;
    }

    private ArrayList<Event> fillEvents(DataSnapshot dataSnapshot) {
        ArrayList<Event> events = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){

            if(ds.getKey().equals("lastUpdate"))continue;//skip non Event value

            Event e = ds.getValue(Event.class);
            e.setId(ds.getKey());
            events.add(e);
        }
        return events;
    }

    private ArrayList<Member> fillMembers(DataSnapshot dataSnapshot) {
        ArrayList<Member> members = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Member m = ds.getValue(Member.class);
            m.setUserId(ds.getKey());
            members.add(m);
        }
        return members;
    }


    public String getGroupId(){
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupRole() {
        return groupRole;
    }

    public Group getGroup() {
        Log.d(TAG,"GROUP = " + group);
        return group;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
