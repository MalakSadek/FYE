package com.example.baher.fyeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageBoardsActivity extends AppCompatActivity {

    String temp = "";
    String[] options;
    String admin;

    //TODO: DB FOR MESSAGES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boards);
        getSupportActionBar().setTitle("Message Boards");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter;
        int counter = 0;
        options = new String[10];
        ListView v = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list){ @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);
            TextView text = (TextView) view.findViewById(android.R.id.text1);

            text.setTextColor(0xFFFFFFFF);
            text.setTextSize(20);


            return view;
        }};
        v.setAdapter(adapter);
        SharedPreferences boards = getSharedPreferences("FYEPrefs", 0);
        String house = "House "+ boards.getString("houseData", "None");
        String group = "Group " + boards.getString("groupData", "None");
        admin=boards.getString("Admin", String.valueOf(0));
        if(house.equals("None")||group.equals("None"))
        {


        }else {
            list.add(house + " Messaging Board");
            options[counter] = house;
            counter = counter + 1;

            list.add(group + " Messaging Board");
            options[counter] = group;
            counter = counter + 1;

        }

        options[counter] = "Announcements";

        list.add("Announcements");

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MessagesActivity.class);
                Intent Admin=new Intent(getApplicationContext(),AnnouncmentsAdmin.class);
                i.putExtra("Channel", options[position]);
                Admin.putExtra("Channel", options[position]);
                if(options[position].equals("Announcements")&& admin.equals("0"))
                {
                    startActivity(Admin);

                }else
                startActivity(i);
            }
        });
    }

}
