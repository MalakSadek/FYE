package com.example.baher.fyeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class AnnouncmentsAdmin extends AppCompatActivity {

    // TO-DO: Store messages on database for monitoring abuse

    private FirebaseListAdapter<ChatMessage> adapter;
    String name;
    String channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcments_admin);

        SharedPreferences chat = getSharedPreferences("FYEPrefs", 0);
        name = chat.getString("Name", "None");
        Intent I = getIntent();
        Bundle b = I.getExtras();
        channel = b.getString("Channel","None");
        final String channel2 = channel;
        Log.i("HMBKAA",channel2);
        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //databaseReference.removeValue();

        displayChatMessages();


    }

    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.messages, FirebaseDatabase.getInstance().getReference().child(channel)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}