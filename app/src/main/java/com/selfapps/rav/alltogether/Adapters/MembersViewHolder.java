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
import static com.selfapps.rav.alltogether.utilites.DbPath._members;

public class MembersViewHolder extends RecyclerView.ViewHolder {
    private String TAG = this.getClass().getName();
    private static final boolean DEBUG =true;
    public TextView name;
    public TextView id;
    private View view;
    private String groupId;

    public MembersViewHolder(View itemView, final String groupId) {
        super(itemView);
        this.groupId = groupId;
        view = itemView;
        name = (TextView) itemView.findViewById(R.id.textView_MemberName);
        id = (TextView) itemView.findViewById(R.id.textView_MemberId);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memberId = id.getText().toString();
                loadFragment(memberId,v.getContext());
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child(_Groups).child(groupId).child(_members).child(memberId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(DEBUG) Log.d(TAG,"key - dataSnapshot = "+ memberId +" -- "+dataSnapshot.toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void loadFragment(String memberId, Context context) {
        Snackbar.make(view.getRootView(), "Replace with your own action !MEMBERS", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
