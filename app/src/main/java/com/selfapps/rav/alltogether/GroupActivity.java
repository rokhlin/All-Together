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
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.Member;

import java.util.ArrayList;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;
import static com.selfapps.rav.alltogether.utilites.DbPath._members;

public class GroupActivity extends AppCompatActivity {
    private String TAG = this.getClass().getName();
    private String groupId;
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
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(_Groups);//.child(groupId)
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG," dataSnapshot GROUP = " + dataSnapshot.child(groupId).child(_members));
                ArrayList<Member> members = fillMembers(dataSnapshot.child(groupId).child(_members));
                Log.d(TAG,"members = " + members.size());
                //group = dataSnapshot.getValue(Group.class);
                //Log.d(TAG,"group_restore = " + group);
//                ArrayList<Member> members = fillMembers(dataSnapshot);
//                group.setId(dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public Group getGroup() {
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
