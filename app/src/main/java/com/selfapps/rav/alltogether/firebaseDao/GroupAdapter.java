package com.selfapps.rav.alltogether.firebaseDao;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.model.GroupReference;

import java.util.ArrayList;
import java.util.LinkedList;

public class GroupAdapter extends RecyclerView.Adapter<GroupsViewHolder> {
    private static final String TAG = "GroupAdapter";
    Context c;
    ArrayList<GroupReference> groups;
    DatabaseReference ref;

//    public GroupAdapter(Class<GroupReference> modelClass, int modelLayout, Class<GroupsViewHolder> viewHolderClass, DatabaseReference ref) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//        this.ref = ref;
//    }


    public GroupAdapter(Context c, ArrayList<GroupReference> coordinatorGroups) {
        this.c = c;
        this.groups = coordinatorGroups;
        Log.d(TAG,"coordinatorGroups.size() = "+coordinatorGroups.size());
    }


    @Override
    public GroupsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.group_item,parent,false);
        return new GroupsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GroupsViewHolder holder, int position) {
        holder.name.setText(groups.get(position).getName());
        holder.id.setText(groups.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }


    protected void populateViewHolder(GroupsViewHolder holder, GroupReference group, int position) {
            holder.name.setText(group.getName());
            holder.id.setText(group.getId());
    }


}
