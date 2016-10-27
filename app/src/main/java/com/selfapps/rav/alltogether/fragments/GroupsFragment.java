package com.selfapps.rav.alltogether.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selfapps.rav.alltogether.BaseActivity;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.FirebaseHelper;
import com.selfapps.rav.alltogether.firebaseDao.GroupAdapter;
import com.selfapps.rav.alltogether.firebaseDao.GroupsViewHolder;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.GroupReference;

import java.util.Random;

public class GroupsFragment extends Fragment {
    private static final String TAG ="GroupsFragment" ;
    DatabaseReference db; //0b7BDBFNWvXnt2h380VP8tZPotE2
    FirebaseHelper helper;
    RecyclerView rv;
    GroupAdapter adapter;
    FloatingActionButton fab;
    private Context ctx;
    private String userUID;

    public GroupsFragment() {
        // Required empty public constructor
    }


    public static GroupsFragment newInstance(String param1, String param2) {
        GroupsFragment fragment = new GroupsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
        userUID = BaseActivity.authUser.getUid();
        //SETUP FB
        db = FirebaseDatabase.getInstance().getReference();

        helper = new FirebaseHelper(db);
        DatabaseReference groupReferenses = db.child("Users").child(userUID).child("groupReferenses");

        //ADAPTER
        adapter = new GroupAdapter(
                                GroupReference.class,
                                R.layout.group_item,
                                GroupsViewHolder.class,
                                groupReferenses);


        fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fabBaseActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addNewGroupTest();
            }
        });
    }

    private void addNewGroupTest() {

        Group group = addNewGroup();
        String groupKey = helper.addGroup(group);

        GroupReference groupRef = addGroupReference(groupKey);

        if(helper.addGroupReference(groupRef) != null)
            adapter.notifyDataSetChanged();


    }

    private Group addNewGroup() {
        Random r = new Random();
        String name = "Group_"+ r.nextInt(1000);

        return new Group(name);
    }

    private GroupReference addGroupReference(String id) {
        //GET DATA
        Random r = new Random();
        String name = "Group_"+ r.nextInt(1000);
        String role = "coordinator";
        return  new GroupReference(id,name,role);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //SETUP RV
        View v = inflater.inflate(R.layout.fragment_groups, container, false);
        rv = (RecyclerView) v.findViewById(R.id.recyclerGroups);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(ctx));
        rv.setAdapter(adapter);
        return v;
    }



}
