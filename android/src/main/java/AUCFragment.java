package com.example.baher.fyeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AUCFragment extends Fragment implements View.OnClickListener{

    Button faq, clubs, facilities, links, majors, maps, discounts, food, messages;
    Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_auc_fragment, container, false);

        faq = (Button) v.findViewById(R.id.faq);
        clubs = (Button) v.findViewById(R.id.clubs);
        facilities = (Button) v.findViewById(R.id.facilities);
        links = (Button) v.findViewById(R.id.links);
        majors = (Button) v.findViewById(R.id.majors);
        maps = (Button) v.findViewById(R.id.map);
        discounts = (Button) v.findViewById(R.id.discounts);
        food = (Button) v.findViewById(R.id.food);
        messages = (Button) v.findViewById(R.id.messages);


        faq.setOnClickListener(this);
        clubs.setOnClickListener(this);
        facilities.setOnClickListener(this);
        links.setOnClickListener(this);
        majors.setOnClickListener(this);
        maps.setOnClickListener(this);
        discounts.setOnClickListener(this);
        food.setOnClickListener(this);
        messages.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.faq:
            {
                i = new Intent(getContext(), FAQActivity.class);
                startActivity(i);
                break;
            }
            case R.id.links:
            {
                i = new Intent(getContext(), LinksActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs:
            {
                i = new Intent(getContext(), ClubsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.facilities:
            {
                i = new Intent(getContext(), FacilitiesAndServicesActivity.class);
                startActivity(i);
                break;
            }
            case R.id.majors:
            {
                i = new Intent(getContext(), MajorsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.map:
            {
                Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("windrose.com.aucmaps");
                if (intent == null) {
                    Toast.makeText(getContext(), "You need to install AUC Maps!", Toast.LENGTH_SHORT).show();
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=windrose.com.aucmaps&hl=en"));
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            }
            case R.id.discounts:
            {
                i = new Intent(getContext(), DiscountsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.food:
            {
                i = new Intent(getContext(), FoodActivity.class);
                startActivity(i);
                break;
            }
            case R.id.messages:
            {
                i = new Intent(getContext(), MessageBoardsActivity.class);
                startActivity(i);
                break;
            }
        }

    }
    public void onDestroy(){
        super.onDestroy();

    }

}