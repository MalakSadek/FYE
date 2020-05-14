package com.example.baher.fyeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    String email;
    Boolean vf;
    Boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(! isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN))
        {
            finish();
            return;

        }
    /*    if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)!=0){

            finish();
            return;
        }*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        SharedPreferences firsttime = getSharedPreferences("FYEPrefs", 0);
        first = firsttime.getBoolean("First", true);
        email = firsttime.getString("Email", "None");


        if (first == true) {
            Button signin = (Button) findViewById(R.id.login);
            Button signup = (Button) findViewById(R.id.signup);

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(next);
                    //finish();
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(next);
                    //finish();
                }
            });
        } else {
            vf = firsttime.getBoolean("Verified", false);
            Log.i("B2olakk", vf.toString());
            if (vf == true) {
                Intent login = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(login);
                finish();
            } else if (vf == false) {
                FirebaseAuth.getInstance().getCurrentUser().delete();
                SharedPreferences.Editor edit = firsttime.edit();
                edit.putBoolean("First", true);
                edit.apply();
                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(login);
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
    }

   /* @Override
    public void onDestroy()
    {
        FirebaseAuth.getInstance().getCurrentUser().delete();
        super.onDestroy();
        if (vf == false) {
            Intent login = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(login);
        finish(); }
    }*/

}