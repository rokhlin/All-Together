package com.selfapps.rav.alltogether.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.BaseActivity;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.FirebaseHelper;
import com.selfapps.rav.alltogether.firebaseDao.GroupAdapter;
import com.selfapps.rav.alltogether.firebaseDao.GroupsViewHolder;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.model.GroupReference;
import static com.selfapps.rav.alltogether.utilites.DbPath.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GroupsFragment extends Fragment {
    private static final String TAG ="GroupsFragment" ;
    DatabaseReference db; //0b7BDBFNWvXnt2h380VP8tZPotE2
    FirebaseHelper helper;
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
        ctx =getActivity();

        //SETUP FB

        db = FirebaseDatabase.getInstance().getReference().child(_Users).child(_authUserId);
        helper = new FirebaseHelper(db);

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



//        //FIREBASE-ADAPTER
//        adapter = new GroupAdapter(
//                                GroupReference.class,
//                                R.layout.group_item,
//                                GroupsViewHolder.class,
//                                db.child(_groupReferences));


        fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fabBaseActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addNewGroupTest();
            }
        });
    }

    private void addNewGroupTest() {
        //GET DATA
        String name = getGroupName();


        String id = helper.addGroup(new Group(name));
//        String role = "coordinator";
//        //SET DATA
//        GroupReference groupReference = new GroupReference(id,name,role);
//
//        //VALIDATE
//        helper.addGroupReference(groupReference);
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
