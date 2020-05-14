package com.example.baher.fyeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ScrollView;
import android.widget.TextView;

public class displayFacilityData extends AppCompatActivity {
    TextView txtview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_facility_data);

        getSupportActionBar().setTitle("Services");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Intent I = getIntent();
        String data = I.getStringExtra("data");

        txtview = (TextView)findViewById(R.id.textView2);

        txtview.setText(data);
        txtview.setMovementMethod(new ScrollingMovementMethod());
        txtview.setTextColor(0xFFFFFFFF);

    }
}
