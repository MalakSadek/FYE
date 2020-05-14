package com.example.baher.fyeapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class SimpleDialogFragment extends DialogFragment {

    Context mContext;
    String info, link;

    public SimpleDialogFragment() {
        mContext = getActivity();
    }

    public void setInfo (String inf){
        info = inf;
    }

    public void setLink (String lnk){
        link = lnk;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Options");
        alertDialogBuilder.setMessage("Please choose your preferred option for selected Major/Minor");

        //null should be your on click listener
        alertDialogBuilder.setPositiveButton("Link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("Info", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getContext(),displayFacilityData.class);
                i.putExtra("data",info);
                startActivity(i);
            }
        });
        alertDialogBuilder.setNeutralButton("OK", null);
        return alertDialogBuilder.create();
    }

    public static SimpleDialogFragment newInstance() {
        SimpleDialogFragment f = new SimpleDialogFragment();
        return f;
    }

}