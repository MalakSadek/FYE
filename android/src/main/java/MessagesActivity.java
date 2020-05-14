package com.example.baher.fyeapp;

import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;


public class MessagesActivity extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    String name;
    String channel;
    TextView messageText,messageUser,messageTime;
    TextView messageText2,messageUser2,messageTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        SharedPreferences chat = getSharedPreferences("FYEPrefs", 0);
        name = chat.getString("Name", "None");
        Intent I = getIntent();
        Bundle b = I.getExtras();
        channel = b.getString("Channel","None");
        final String channel2 = channel;
        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //databaseReference.removeValue();

        displayChatMessages();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance()
                        .getReference().child(channel2)
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                name)
                        );

                input.setText("");
            }
        });

    }

    private void displayChatMessages() {
        final ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.messages, FirebaseDatabase.getInstance().getReference().child(channel)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                messageText = (TextView)v.findViewById(R.id.message_text);
                messageUser = (TextView)v.findViewById(R.id.message_user);
                messageTime = (TextView)v.findViewById(R.id.message_time);
                messageText2 = (TextView)v.findViewById(R.id.message_text2);
                messageUser2 = (TextView)v.findViewById(R.id.message_user2);
                messageTime2 = (TextView)v.findViewById(R.id.message_time2);
                Log.i("MEEE",name);
                Log.i("YOUUU", model.getMessageUser());
                if (name.equals(model.getMessageUser())) {
                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText("ME");
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));
                    messageText.setVisibility(View.VISIBLE);
                    messageUser.setVisibility(View.VISIBLE);
                    messageTime.setVisibility(View.VISIBLE);
                    messageText2.setVisibility(View.INVISIBLE);
                    messageUser2.setVisibility(View.INVISIBLE);
                    messageTime2.setVisibility(View.INVISIBLE);
                }
                else
                {
                    messageText.setVisibility(View.INVISIBLE);
                    messageUser.setVisibility(View.INVISIBLE);
                    messageTime.setVisibility(View.INVISIBLE);
                    // Set their text
                    messageText2.setText(model.getMessageText());
                    messageUser2.setText(model.getMessageUser());
                    messageTime2.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));
                    messageText2.setVisibility(View.VISIBLE);
                    messageUser2.setVisibility(View.VISIBLE);
                    messageTime2.setVisibility(View.VISIBLE);

                }

            }
        };

        listOfMessages.setAdapter(adapter);
    }
}