package com.example.baher.fyeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


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

public class FoodActivity extends AppCompatActivity {

    String test;
    ListView list;
    ArrayList<Item> discount;
    String[] inf;
    ArrayList <Integer> indexMap;
    int k;
    static RequestQueue requestQueue;
    JSONArray jsonArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(FoodActivity.this)) {
            buildDialog(FoodActivity.this).show();
            return;
        }
        else {
            //Toast.makeText(ClubsActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_discounts);
        }


        getSupportActionBar().setTitle("Food Outlets");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        jsonArr = new JSONArray();
        requestQueue = null;

        discount = new ArrayList<>();
        inf = new String[1000];
        k = 0;
        indexMap = new ArrayList<>();
        list = (ListView)findViewById(R.id.list);
        findStores();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TO-DO POPUP CONTAINING INFO
                DiscDialogFragment dialog = DiscDialogFragment.newInstance();
                dialog.x = inf[list.getCount() - position - 1];
                dialog.show(getFragmentManager(), "fragmentDialog");
            }
        });
    }

    public void findStores(){
        String url = "http://188.226.144.157/group1/find_allfood.php";
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArr, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {
                try {
                    for (int i = 0;i<response1.length();i++) {
                        final int finalI = i;
                        fillList(response1,finalI);
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
    }

    private void fillList(final JSONArray resp,final int i) throws JSONException {
        indexMap.add(i);
        String url = "http://188.226.144.157/group1/find_food_data.php";
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest Stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                String info = "";
                String image = "";
                try {
                    Item tmp;
                    tmp = new Item();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject storeData = result.getJSONObject(0);
                    tmp.name = resp.get(i).toString();
                    inf[indexMap.get(k)] = storeData.getString("info");
                    tmp.logo = storeData.getString("logo");
                    discount.add(0, tmp);
                    ListViewAdapter CA = new ListViewAdapter(getApplicationContext(), discount);
                    list.setAdapter(CA);
                    k = k+1;
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
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("name", resp.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
