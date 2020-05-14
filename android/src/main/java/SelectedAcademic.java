package com.example.baher.fyeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectedAcademic extends AppCompatActivity {

    String selected;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView v;
    String information;
    String link;
    ArrayList<Integer>indexMap;
    int k;
    String name1;
    static RequestQueue requestQueue;
    JSONArray jsonArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(SelectedAcademic.this)) {
            buildDialog(SelectedAcademic.this).show();
            return;
        }
        else {
            //Toast.makeText(ClubsActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_selected_academic);
        }


        list = new ArrayList<>();

        jsonArr = new JSONArray();
        requestQueue = null;

        information = new String();
        link = new String();
        k = 0;

        Intent I = getIntent();
        Bundle b = I.getExtras();
        selected = b.getString("Selected","None");

        v = (ListView) findViewById(R.id.lv_selected);

        String url = "http://188.226.144.157/group1/fill_academic_list.php?type=" + selected;

        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, url, jsonArr, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {
                try {
                    for (int i = 0;i<response1.length();i++) {
                        final int finalI = i;
                        Log.i("SHEDDD1",response1.get(finalI).toString());
                        list.add(response1.get(finalI).toString());

                        adapter = new ArrayAdapter<String>(SelectedAcademic.this, android.R.layout.simple_list_item_1, android.R.id.text1, list){ @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);

                            text.setTextColor(0xFFFFFFFF);
                            text.setTextSize(15);

                            return view;
                        }};

                        v.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "onERRORResponse register: " + error.toString());
            }
        });
        requestQueue.add(jsonArrRequest);

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name1 = list.get(position);
                getInfoLink();

            }
        });

    }

    public void getInfoLink()
    {
        String url = "http://188.226.144.157/group1/find_academic_data.php";
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(this);

        StringRequest Stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject storeData = result.getJSONObject(0);
                    information = storeData.getString("info");
                    link = storeData.getString("link");
                    if (link.equals("None"))
                    {
                        DiscDialogFragment dialog = DiscDialogFragment.newInstance();
                        dialog.x = information;
                        dialog.show(getFragmentManager(), "fragmentDialog");
                    }
                    else {
                    SimpleDialogFragment dialog = SimpleDialogFragment.newInstance();
                    dialog.setInfo(information);
                    dialog.setLink(link);
                    dialog.show(getSupportFragmentManager(), "fragmentDialog"); }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("", "onERRORResponse: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name1);
                return params;
            }};
        requestQueue.add(Stringrequest);
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
