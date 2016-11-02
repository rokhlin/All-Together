package com.selfapps.rav.alltogether.firebaseDao;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfapps.rav.alltogether.BaseActivity;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.fragments.GroupDetailFragment;
import com.selfapps.rav.alltogether.fragments.GroupsFragment;
import com.selfapps.rav.alltogether.model.Group;
import com.selfapps.rav.alltogether.utilites.ListenerGetter;

import static com.selfapps.rav.alltogether.utilites.DbPath._Groups;

public class GroupsViewHolder extends RecyclerView.ViewHolder  {
    private static final String TAG = "GroupsViewHolder";
    public TextView name;
    public TextView id;


    public GroupsViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.textView_GroupName);
        id = (TextView) itemView.findViewById(R.id.textView_GroupId);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String groupId = id.getText().toString();
                loadFragment(groupId,v.getContext());
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child(_Groups).child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG,"key - dataSnapshot = "+ groupId +" -- "+dataSnapshot.toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void loadFragment(String groupId, Context context) {
        Fragment fragment = ((BaseActivity)context).groupDetailFragment;
//        Bundle b = new Bundle();
//        b.putString("groupId",groupId);
//        fragment.setArguments(b);
        ListenerGetter.setId(groupId);
        FragmentTransaction fTrans = ((BaseActivity)context).getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frameLayoutBaseActivity, fragment);
        fTrans.commit();
    }
}
