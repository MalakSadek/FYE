package com.example.baher.fyeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TabbedActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    String data;
    String permission;
    String j = "";
    Intent profile, help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabbed);

        FirebaseMessaging.getInstance().subscribeToTopic("my-topic");
        FirebaseInstanceId.getInstance().getToken();

        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16 * 1024 * 1024);
        SharedPreferences yo =  getSharedPreferences("FYEPrefs", 0);
        Boolean first = yo.getBoolean("First", true);

        Log.i("first", String.valueOf(first));

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(getApplicationContext(), getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("AUC").setIndicator("AUC", null),
                AUCFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("FYE").setIndicator("FYE", null),
                FYEFragment.class, null);

        // Setting tab color
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

            final TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);
            if (tv == null)
                continue;
            else
                tv.setTextColor(0xFFFFFFFF);
        }

        switch (mTabHost.getId()) {
            case 0:
            {
                getSupportActionBar().setTitle("AUC");
            }
            case 1:
            {
                getSupportActionBar().setTitle("FYE");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        help = new Intent(this, HelpActivity.class);
        profile = new Intent(this, ProfileActivity.class);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getTitle().toString())
        {
            case "Profile":
            {
                startActivity(profile);
                break;
            }
            case "Help page":
            {
                startActivity(help);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
