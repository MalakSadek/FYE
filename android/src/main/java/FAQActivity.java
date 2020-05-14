package com.example.baher.fyeapp;

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

public class FAQActivity extends AppCompatActivity {

    String test;
    ListView list;
    ArrayList<String> faq;
    String[] inf;
    ArrayList <Integer> indexMap;
    int k;
    static RequestQueue requestQueue;
    JSONArray jsonArr;
    StringRequest Stringrequest;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        getSupportActionBar().setTitle("FAQ");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        requestQueue = null;
        jsonArr = new JSONArray();
        faq = new ArrayList<>();
        inf = new String[50];
        k = 0;
        indexMap = new ArrayList<>();
        list = (ListView)findViewById(R.id.list3);
        findStores();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiscDialogFragment dialog = DiscDialogFragment.newInstance();
                dialog.x = "";
                dialog.x = inf[position];
                dialog.show(getFragmentManager(), "fragmentDialog");
            }
        });
    }

    public void findStores(){
        String url = "http://188.226.144.157/group1/find_allFAQ.php";
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
        String url = "http://188.226.144.157/group1/find_FAQ_data.php";
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(this);

        Stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject storeData = result.getJSONObject(0);
                    faq.add(indexMap.get(k),resp.get(i).toString());
                    inf[indexMap.get(k)] = storeData.getString("info");
                    adapter = new ArrayAdapter<String>(FAQActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, faq){ @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);

                        text.setTextColor(0xFFFFFFFF);
                        text.setTextSize(20);

                        return view;
                    }};
                    k = k+1;
                    list.setAdapter(adapter);
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
                try {
                    params.put("name", resp.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }};
        requestQueue.add(Stringrequest);
    }
}

