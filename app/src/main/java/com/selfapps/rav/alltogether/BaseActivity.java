package com.selfapps.rav.alltogether;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.selfapps.rav.alltogether.fragments.GroupsFragment;


public class BaseActivity extends AppCompatActivity {


    public static FirebaseUser authUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        Fragment groupsFrag = new GroupsFragment();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frameLayoutBaseActivity, groupsFrag);
        fTrans.commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBaseActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public FirebaseUser getAuthUser() {
        return authUser;
    }

}
