package com.example.baher.fyeapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class VerificationActivity extends AppCompatActivity {


    Button verify;
    ImageView tick;
    ProgressBar prgrs;
    String password, email, name, number, house, group;
    RequestParams params = new RequestParams();
    FirebaseAuth mAuth;
    Boolean verified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);



        getSupportActionBar().setTitle("Verifying");
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        SharedPreferences user = getSharedPreferences("FYEPrefs", 0);
        email = user.getString("Email", "None");
        number = user.getString("Number", "None");
        password = user.getString("Password", "None");
        name = user.getString("Name", "None");
        house = user.getString("houseData", "None");
        group = user.getString("groupData", "None");
        SharedPreferences.Editor edit = user.edit();
        verified = false;
        edit.putBoolean("Verified",verified);
        edit.apply();
        tick = (ImageView) findViewById(R.id.imageView3);
        prgrs = (ProgressBar) findViewById(R.id.progressBar);
        verify = (Button) findViewById(R.id.verify);

        tick.setVisibility(View.INVISIBLE);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Unable to sign in, please try again!", Toast.LENGTH_LONG).show();

                                } else {
                                    checkIfEmailVerified();
                                }

                            }
                        });

            }
        });
    }
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {
            Toast.makeText(getApplicationContext(), "Verified!", Toast.LENGTH_SHORT).show();
            tick.setVisibility(View.VISIBLE);
            prgrs.setVisibility(View.INVISIBLE);

            SharedPreferences Email = getSharedPreferences("FYEPrefs", 0);
            SharedPreferences.Editor editor = Email.edit();
            editor.putString("Email", email);
            editor.apply();

            RequestParams params = new RequestParams();

            // set our JSON object
            params.put("name", name);
            params.put("email", email);
            params.put("password", password);
            params.put("houseData", house);
            params.put("groupData", group);

            // create our HTTP client
            AsyncHttpClient client = new AsyncHttpClient();

            client.post("http://188.226.144.157/group1/AddUser.php", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                    Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                    SharedPreferences email = getSharedPreferences("FYEPrefs", 0);
                    SharedPreferences.Editor editor = email.edit();
                    editor.putString("Name", name);
                    editor.putString("houseData", house);
                    editor.putString("groupData", group);
                    verified = true;
                    editor.putBoolean("Verified",true);
                    Log.i("TEST","SHEDEED-SUCCEEDED");
                    editor.apply();
                    Intent login = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(login);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                    SharedPreferences email = getSharedPreferences("FYEPrefs", 0);
                    SharedPreferences.Editor editor = email.edit();
                    editor.putString("Name", name);
                    editor.putString("houseData", house);
                    editor.putString("groupData", group);
                    verified = true;
                    editor.putBoolean("Verified",true);
                    Log.i("TEST","SHEDEED-SUCCEEDED");
                    editor.apply();
                    Intent login = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(login);
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please verify your email to continue!", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
        }
    }
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        log.i("TAG","minimized");
    }

}