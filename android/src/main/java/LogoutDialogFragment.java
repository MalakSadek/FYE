package com.example.baher.fyeapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;


public class LogoutDialogFragment extends DialogFragment{
    Context mContext;
    public LogoutDialogFragment() {
        mContext = getActivity();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Gone so soon?");
        alertDialogBuilder.setMessage("Are you sure you want to log out?");
        //null should be your on click listener
        alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences firsttime = getContext().getSharedPreferences("FYEPrefs", 0);
                SharedPreferences.Editor editor = firsttime.edit();
                editor.putBoolean("First", true);
                editor.putString("Name","None");
                editor.putString("Email", "None");
                editor.putString("houseData","None");
                editor.putString("groupData","None");
                editor.putString("Password", "None");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("my-topic");
                editor.apply();
                Intent i = new Intent(getContext(), MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(i);
            }
        });

        alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }
}
