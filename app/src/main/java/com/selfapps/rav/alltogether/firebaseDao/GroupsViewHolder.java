package com.selfapps.rav.alltogether.firebaseDao;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.selfapps.rav.alltogether.R;

public class GroupsViewHolder extends RecyclerView.ViewHolder  {
    public TextView name;
    public TextView id;

    public GroupsViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.textView_GroupName);
        id = (TextView) itemView.findViewById(R.id.textView_GroupId);
    }
}
