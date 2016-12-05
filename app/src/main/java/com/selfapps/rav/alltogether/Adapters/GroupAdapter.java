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
import com.selfapps.rav.alltogether.model.GroupReference;
import com.selfapps.rav.alltogether.utilites.ListenerGetter;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupsViewHolder> {
    private static final String TAG = "GroupAdapter";
    private ListenerGetter getter = new ListenerGetter();
    private Context c;
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private List<GroupReference> coordinatorGroups;
    DatabaseReference ref;//Users/0b7BDBFNWvXnt2h380VP8tZPotE2
//    public GroupAdapter(Class<GroupReference> modelClass, int modelLayout, Class<GroupsViewHolder> viewHolderClass, DatabaseReference ref) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//        this.ref = ref;
//    }


    public GroupAdapter(Context c, List<GroupReference> coordinatorGroups) {
        this.c = c;
        this.coordinatorGroups = coordinatorGroups;
        Log.d(TAG,"coordinatorGroups.size() = "+coordinatorGroups.size());
    }


    @Override
    public GroupsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.group_item,parent,false);

        return new GroupsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GroupsViewHolder holder, int position) {
        holder.name.setText(coordinatorGroups.get(position).getName());
        holder.id.setText(coordinatorGroups.get(position).getId());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return coordinatorGroups.size();
    }

    protected void populateViewHolder(GroupsViewHolder holder, GroupReference group, int position) {
            holder.name.setText(group.getName());
            holder.id.setText(group.getId());
    }




}