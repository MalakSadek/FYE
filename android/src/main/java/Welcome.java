package com.example.baher.fyeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        getSupportActionBar().setTitle("Welcome");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        View v = (View)findViewById(R.id.button4);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TabbedActivity.class);
                startActivity(i);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        finish();
    }
    @Override
    public void onBackPressed() {
    }

}
