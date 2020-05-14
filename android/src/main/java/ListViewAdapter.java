package com.example.baher.fyeapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpHead;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by malaksadek on 6/14/17.
 */

public class ListViewAdapter extends ArrayAdapter<Item> {

    ImageView imageView;
    Item c;

    public ListViewAdapter(Context context, ArrayList<Item> c) {
        super(context, 0, c);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        c = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView category = (TextView) convertView.findViewById(R.id.Name);
        imageView = (ImageView) convertView.findViewById(R.id.Icon);

        category.setText(c.name);

        setImage(c.logo);

        return convertView;
    }

    private void setImage(String img) {

        Picasso.with(getContext()).load("http://188.226.144.157/group1/images/" + img).into(imageView);
        if (imageView.getDrawable()== null)
            imageView.setImageResource(R.drawable.discountslogo);

    }
}
