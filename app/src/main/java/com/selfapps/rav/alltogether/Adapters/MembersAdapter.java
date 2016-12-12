package com.selfapps.rav.alltogether.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selfapps.rav.alltogether.R;
import com.selfapps.rav.alltogether.model.Member;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersViewHolder>  {
    private static final String TAG = "MembersAdapter";
    private static final boolean DEBUG = true;
    private Context c;
    private List<Member> members;
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private String groupId;


    public MembersAdapter(Context c, List<Member> members, String groupId) {
        this.c = c;
        this.members = members;
        this.groupId = groupId;
        if(DEBUG) Log.d(TAG,"members.size() = "+members.size());
    }

    @Override
    public MembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.member_item,parent,false);
        return new MembersViewHolder(v,groupId);
    }

    @Override
    public void onBindViewHolder(MembersViewHolder holder, int position) {
        holder.name.setText(members.get(position).getName());
        holder.id.setText(members.get(position).getUserId());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addEvent(Member member){
        members.add(member);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    protected void populateViewHolder(MembersViewHolder holder, Member member, int position) {
        holder.name.setText(member.getName());
        holder.id.setText(member.getUserId());
    }
}
