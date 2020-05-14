package com.example.baher.fyeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FYEFragment extends Fragment implements View.OnClickListener{

    Button schedule, competition, youtube, majorfair, engagementfair, treasurehunt, guide, stations;
    Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fye_fragment, container, false);

        schedule = (Button) v.findViewById(R.id.schedule);
        competition = (Button) v.findViewById(R.id.competition);
        youtube = (Button) v.findViewById(R.id.youtube);
        majorfair = (Button) v.findViewById(R.id.majorfair);
        engagementfair = (Button) v.findViewById(R.id.engagementfair);
        treasurehunt = (Button) v.findViewById(R.id.treasurehunt);
        stations = (Button) v.findViewById(R.id.stations);
        guide = (Button) v.findViewById(R.id.guide);

        schedule.setOnClickListener(this);
        competition.setOnClickListener(this);
        youtube.setOnClickListener(this);
        majorfair.setOnClickListener(this);
        engagementfair.setOnClickListener(this);
        treasurehunt.setOnClickListener(this);
        stations.setOnClickListener(this);
        guide.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.schedule:
            {
                i = new Intent(getContext(), ScheduleActivity.class);
                break;
            }
            case R.id.competition:
            {
                i = new Intent(getContext(), CompetitionActivity.class);
                break;
            }
            case R.id.youtube:
            {
                i = new Intent(getContext(), VideosActivity.class);
                break;
            }
            case R.id.majorfair:
            {
                i = new Intent(getContext(), MajorFairActivity.class);
                break;
            }
            case R.id.engagementfair:
            {
                i = new Intent(getContext(), EngagementFairActivity.class);
                break;
            }
            case R.id.treasurehunt:
            {
                i = new Intent(getContext(), TreasureHuntActivity.class);
                break;
            }
            case R.id.stations:
            {
                i = new Intent(getContext(), StationsActivity.class);
                break;
            }
            case R.id.guide:
            {
                i = new Intent(getContext(), GuideActivity.class);
                break;
            }

        }
        startActivity(i);
    }
}
