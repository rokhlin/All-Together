package com.selfapps.rav.alltogether;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RegisterActivity";
    TextView  tvSignIn;
    EditText etEmail, etPass;
    Button btnRegister;
    RelativeLayout layout;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    loadBaseActivity();

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        layout = (RelativeLayout) findViewById(R.id.activity_register);
        etEmail = (EditText) findViewById(R.id.et_Login);
        etPass = (EditText) findViewById(R.id.et_Password);
        tvSignIn = (TextView) findViewById(R.id.tv_SignIn);
        btnRegister = (Button) findViewById(R.id.btn_Register);

        progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
    }

    private void loadBaseActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(),BaseActivity.class));
    }

    private void loadLoginActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Register:
                registerUser(layout);
                break;
            case R.id.tv_SignIn:
                loadLoginActivity();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }

    private void registerUser(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if(email.equalsIgnoreCase("")){
            //check wrong email
            Snackbar.make(view,"Please enter EMAIL!", Snackbar.LENGTH_SHORT)
                    .setAction("Action",null).show();
        }
        else if(password.equalsIgnoreCase("")){
            //check wrong password
            Snackbar.make(view,"Please enter PASSWORD!", Snackbar.LENGTH_SHORT)
                    .setAction("Action",null).show();
        }

        else {
            progressDialog.setMessage("User Registration...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Snackbar.make(layout, "Authentication failed. Check Email/Password and try Again.", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }

                            loadBaseActivity();
                        }
                    });
        }
    }
}
