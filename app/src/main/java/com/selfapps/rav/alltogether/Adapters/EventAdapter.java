package com.selfapps.rav.alltogether.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventsViewHolder> {
    private static final String TAG = "EventAdapter";
    private Context c;
    private List<Event> events;
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private String groupId;

    public EventAdapter(Context c, List<Event> events, String groupId) {
        this.c = c;
        this.events = events;
        Log.d(TAG,"events.size() = "+events.size());
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.event_item,parent,false);

        return new EventsViewHolder(v,groupId);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        holder.name.setText(events.get(position).getName());
        holder.id.setText(events.get(position).getId());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    protected void populateViewHolder(EventsViewHolder holder, Event event, int position) {
        holder.name.setText(event.getName());
        holder.id.setText(event.getId());
    }
}
