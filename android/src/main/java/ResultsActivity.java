package com.example.baher.fyeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class ResultsActivity extends AppCompatActivity {

    TextView title, first, second, third, ranking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        title = (TextView) findViewById(R.id.textView7);
        first = (TextView) findViewById(R.id.imageView6);
        second = (TextView) findViewById(R.id.imageView7);
        third = (TextView) findViewById(R.id.imageView8);
        ranking = (TextView) findViewById(R.id.imageView9);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        int house = b.getInt("House");

        title.setText("Winners For House "+house+":");

        RequestParams params = new RequestParams();
        params.put("house", house);

        AsyncHttpClient client = new AsyncHttpClient();

        client.post("http://188.226.144.157/group1/FetchHouseWinners.php", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                try {
                    first.setText(response.get(0).toString());
                    second.setText(response.get(1).toString());
                    third.setText(response.get(2).toString());
                    ranking.setText(response.get(3).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
