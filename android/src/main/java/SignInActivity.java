package com.example.baher.fyeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignInActivity extends AppCompatActivity {

    EditText email, password;
    String mail, pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(SignInActivity.this)) {
            buildDialog(SignInActivity.this).show();
            return;
        }
        else {
            //Toast.makeText(ClubsActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_sign_in);
        }


        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button signin = (Button) findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail =email.getText().toString().replace(" ","");
                pass = password.getText().toString();

                if (mail.matches("") || pass.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter both your email and password!", Toast.LENGTH_LONG).show();
                }

                else {

                    //TODO: Search for email, verify password, log in or display error

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Unable to sign in, please try again!", Toast.LENGTH_LONG).show();

                                    } else {
                                        checkIfEmailVerified();
                                    }
                                    // ...
                                }
                            });
                }
            }
        });
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {

            RequestParams params = new RequestParams();

            // set our JSON object
            params.put("email", mail);

            // create our HTTP client
            AsyncHttpClient client = new AsyncHttpClient();

            client.post("http://188.226.144.157/group1/SearchEmail.php", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                    try {
                        Log.i("", response.get("Major1").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences email = getSharedPreferences("FYEPrefs", 0);
                    SharedPreferences.Editor editor = email.edit();
                    try {
                        editor.putBoolean("First", false);
                        editor.putString("Name", response.get("Name").toString());
                        editor.putString("Password", response.get("Password").toString());
                        editor.putString("Email", response.get("Email").toString());
                        editor.putString("houseData", response.get("House").toString());
                        editor.putString("groupData", response.get("HGroup").toString());
                        editor.putString("Admin",response.get("Admin").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    editor.apply();

                    Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.i("",responseString.toString());
                    Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                    startActivity(i);
                    finish();
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Unknown error, please contact FYE!", Toast.LENGTH_LONG).show();

        }
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
