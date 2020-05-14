package com.example.baher.fyeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class VideosActivity extends AppCompatActivity {

    String[] options;
    ArrayList<String> list;
    ListView v;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(VideosActivity.this)) {
            buildDialog(VideosActivity.this).show();
            return;
        }
        else {
            //Toast.makeText(ClubsActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_videos);
        }

        options = new String[100];
        list = new ArrayList<>();
        v = (ListView) findViewById(R.id.lv);

        getSupportActionBar().setTitle("FYE Videos");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        AsyncHttpClient client = new AsyncHttpClient();

        client.post("http://188.226.144.157/group1/FetchVideos.php", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {

                    try {

                        for (int i = 0; i < response.length(); i++) {
                            Log.i("tttt", response.get(i).toString());
                            list.add(response.get(i).toString());
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, list){ @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                View view = super.getView(position, convertView, parent);
                                TextView text = (TextView) view.findViewById(android.R.id.text1);

                                text.setTextColor(0xFFFFFFFF);
                                text.setTextSize(20);


                                return view;
                            }};
                            v.setAdapter(adapter);
                        }

                        AsyncHttpClient client = new AsyncHttpClient();

                        client.post("http://188.226.144.157/group1/FetchVideosURL.php", new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                                for (int i = 0; i < response.length(); i++)
                                    try {
                                        options[i] = response.get(i).toString();
                                        Log.i("Options", options[i]);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(getApplicationContext(), "Updated Password!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Updated Password!", Toast.LENGTH_SHORT).show();
            }
        });


        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), YoutubeActivity.class);

                i.putExtra("Code", options[position]);
                startActivity(i);
            }
        });
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

}
