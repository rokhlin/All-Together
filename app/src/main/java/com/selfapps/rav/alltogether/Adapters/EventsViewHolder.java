package com.selfapps.rav.alltogether.Adapters;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.R;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;
import static com.selfapps.rav.alltogether.utilites.DbPath._events;

public class EventsViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "EventsViewHolder";
    public TextView name;
    public TextView id;
    private View view;
    private String groupId;

    public EventsViewHolder(View itemView, final String groupId) {
        super(itemView);
        this.groupId = groupId;
        view = itemView;
        name = (TextView) itemView.findViewById(R.id.textView_EventName);
        id = (TextView) itemView.findViewById(R.id.textView_EventId);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eventId = id.getText().toString();
                loadFragment(eventId,v.getContext());
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child(_Groups).child(groupId).child(_events).child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG,"key - dataSnapshot = "+ eventId +" -- "+dataSnapshot.toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void loadFragment(String groupId, Context context) {
//        Fragment fragment = ((BaseActivity)context).groupDetailFragment;
//        Intent intent = new Intent(((BaseActivity)context), GroupActivity.class);
//        intent.putExtra("groupId", groupId);
//        ((BaseActivity)context).startActivity(intent);
        Snackbar.make(view.getRootView(), "Replace with your own action !!!!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
