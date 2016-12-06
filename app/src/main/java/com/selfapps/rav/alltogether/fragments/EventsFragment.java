package com.selfapps.rav.alltogether.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.selfapps.rav.alltogether.GroupActivity;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.firebaseDao.EventsFirebaseHelper;
import com.selfapps.rav.alltogether.model.Event;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    private static final String TAG ="EventsFragment" ;
    DatabaseReference db;//FirebaseDatabase.getInstance().getReference().child(_Groups).child(groupId)
    EventsFirebaseHelper helper;
    RecyclerView rv;
    EventAdapter adapter;
    FloatingActionButton fab;


    private String groupId;

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
        groupId = ((GroupActivity)getActivity()).getGroupId();
        //SETUP FB

        db = FirebaseDatabase.getInstance().getReference().child(_Groups).child(groupId);

        helper = new EventsFirebaseHelper(db);
        adapter = new EventAdapter(getActivity(),helper.retrieve(),groupId);

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
        View v = inflater.inflate(R.layout.fragment_group_events, container, false);

        TextView tv_coordinator =(TextView) v.findViewById(R.id.tw_coordinator);//add here information from group
        TextView tv_groupName =(TextView) v.findViewById(R.id.tw_group_name);

        rv = (RecyclerView) v.findViewById(R.id.rv_group_events);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });
        return v;
    }

    private String addEvent() {
        Event event = createNewEvent();

        String key = helper.addEvent(event);
        event.setId(key);
        adapter.addEvent(event);
        return key;
    }

    /**
     * TODO-Replace this method on the real data!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @return new Event
     */
    private Event createNewEvent() {
        Event event = new Event();
        event.setName("New Name");
        event.setMessage("New event message");
        event.setEndDate(System.currentTimeMillis());
        event.setStartDate(System.currentTimeMillis()+10000);
        event.setStatus(true);
        return event;
    }

}
