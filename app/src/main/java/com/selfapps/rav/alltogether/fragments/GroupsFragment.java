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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.GroupsFirebaseHelper;
import com.selfapps.rav.alltogether.Adapters.GroupAdapter;
import com.selfapps.rav.alltogether.model.Group;

import static com.selfapps.rav.alltogether.utilites.DbPath.*;

import java.util.Random;

public class GroupsFragment extends Fragment {
    private static final String TAG ="GroupsFragment" ;
    DatabaseReference db; //0b7BDBFNWvXnt2h380VP8tZPotE2
    GroupsFirebaseHelper helper;
    RecyclerView rv;
    GroupAdapter adapter;
    FloatingActionButton fab;
    private Context ctx;
    private Random r;


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

        //SETUP FB

        db = FirebaseDatabase.getInstance().getReference().child(_Users).child(_authUserId);
        helper = new GroupsFirebaseHelper(db);

        adapter = new GroupAdapter(ctx,helper.retreive());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fabBaseActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addNewGroup();
            }
        });
    }

    private void addNewGroup() {
        //GET DATA
        String name = getGroupName();
        String id = helper.addGroup(new Group(name));

        adapter.notifyDataSetChanged();
    }


    private String getGroupName() {
        r = new Random();
        return "Group_"+ r.nextInt(1000);
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
