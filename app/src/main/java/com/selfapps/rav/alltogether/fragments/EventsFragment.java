package com.selfapps.rav.alltogether.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.EventsFirebaseHelper;
import com.selfapps.rav.alltogether.firebaseDao.GroupsFirebaseHelper;
import com.selfapps.rav.alltogether.model.Event;

import java.util.Date;

import static com.selfapps.rav.alltogether.utilites.DbPath._Users;
import static com.selfapps.rav.alltogether.utilites.DbPath._authUserId;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    private static final String TAG ="EventsFragment" ;
    DatabaseReference db;
    EventsFirebaseHelper helper;
    RecyclerView rv;
    EventAdapter adapter;
    FloatingActionButton fab;
    private Context ctx;

    private String groupId = "-KVavukfKGQEXqZGP3lj";// add value of selected group!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public EventsFragment() {
        // Required empty public constructor
    }


    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();

        //SETUP FB

        db = FirebaseDatabase.getInstance().getReference().child(_Users).child(_authUserId);
        helper = new EventsFirebaseHelper(db);
        adapter = new EventAdapter(getActivity(),helper.retreive(),groupId);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_events, container, false);
        TextView tv_coordinator =(TextView) v.findViewById(R.id.tw_coordinator);
        TextView tv_groupName =(TextView) v.findViewById(R.id.tw_group_name);
        rv =(RecyclerView) v.findViewById(R.id.rv_group_events);
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event();
                event.setName("New Name");
                event.setMessage("New event message");
                event.setEndDate(System.currentTimeMillis());
                event.setStartDate(System.currentTimeMillis()+10000);
                event.setStatus(true);
                helper.addEvent(event,groupId);
            }
        });
        return v;
    }

}
