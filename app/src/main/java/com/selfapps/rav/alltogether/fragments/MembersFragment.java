package com.selfapps.rav.alltogether.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.Adapters.EventAdapter;
import com.selfapps.rav.alltogether.Adapters.MembersAdapter;
import com.selfapps.rav.alltogether.GroupActivity;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.EventsFirebaseHelper;
import com.selfapps.rav.alltogether.firebaseDao.MembersFirebaseHelper;
import com.selfapps.rav.alltogether.model.Group;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;


public class MembersFragment extends Fragment {
    private static final boolean DEBUG = true;
    private static final String TAG = "MembersFragment" ;

    private Group group;
    private String groupId;
    private String groupName;
    private String groupRole;
    private RecyclerView rv;
    private DatabaseReference db;
    private MembersFirebaseHelper helper;
    private MembersAdapter adapter;

    public MembersFragment() {
        // Required empty public constructor
    }


    public static MembersFragment newInstance(String param1, String param2) {
        MembersFragment fragment = new MembersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGroup();

        //SETUP FB

        db = FirebaseDatabase.getInstance().getReference().child(_Groups).child(groupId);

        helper = new MembersFirebaseHelper(db);
        adapter = new MembersAdapter(getActivity(),helper.retrieve(),groupId);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_members, container, false);
        setGroupAttributes(v);

        rv = (RecyclerView) v.findViewById(R.id.rv_group_members);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        return v;
    }

    private void setGroupAttributes(View v) {
        TextView tv_coordinator =(TextView) v.findViewById(R.id.tw_coordinator);
        TextView tv_groupName =(TextView) v.findViewById(R.id.tw_group_name);

        if(DEBUG) Log.d(TAG,"MARKER--------------------------------------------E");
        tv_coordinator.setText(groupRole);
        tv_groupName.setText(groupName);
    }

    private void getGroup() {
        GroupActivity activity =(GroupActivity)getActivity();
        groupId = activity.getGroupId();
        groupName = activity.getGroupName();
        groupRole = activity.getGroupRole();
    }
}
