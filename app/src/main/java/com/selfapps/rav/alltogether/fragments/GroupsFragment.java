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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.FirebaseHelper;
import com.selfapps.rav.alltogether.firebaseDao.GroupAdapter;
import com.selfapps.rav.alltogether.firebaseDao.GroupsViewHolder;
import com.selfapps.rav.alltogether.model.GroupReference;

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
ArrayList<String> sfda;
    private Context ctx;

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
        db = FirebaseDatabase.getInstance().getReference().child("0b7BDBFNWvXnt2h380VP8tZPotE2");
        helper = new FirebaseHelper(db);
        DatabaseReference coordinatorsRef = db.child("groupCoordinators");

        //ADAPTER
        adapter = new GroupAdapter(
                                GroupReference.class,
                                R.layout.group_item,
                                GroupsViewHolder.class,
                                coordinatorsRef);


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
        Random r = new Random();

        String name="Group_"+ r.nextInt(1000);
        //SET DATA
        GroupReference group = new GroupReference(name);

        //VALIDATE
        if(name.length()>0 && name != null)
        {
            if(helper.addGroupReference(group))
            {
                adapter.notifyDataSetChanged();
            }
        }else
        {
            Toast.makeText(getContext(), "Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
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
