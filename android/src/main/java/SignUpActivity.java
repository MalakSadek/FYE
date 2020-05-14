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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password, vpassword;
    TextView House, Group;
    Spinner houseList, groupList;
    String Name, Email, Password, Vpassword, houseData, groupData;
    RadioButton YES, NO;
    ArrayList<String> houses, groups;
    Boolean sent = false;
    Button done;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isConnected(SignUpActivity.this)) {
            buildDialog(SignUpActivity.this).show();
            return;
        } else {
            //Toast.makeText(ClubsActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_sign_up);
        }


        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        houseList = (Spinner) findViewById(R.id.spinner2);
        groupList = (Spinner) findViewById(R.id.spinner);


        done = (Button) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        vpassword = (EditText) findViewById(R.id.vpassword);
        YES = (RadioButton) findViewById(R.id.radioButton);
        NO = (RadioButton) findViewById(R.id.radioButton2);
        House = (TextView) findViewById(R.id.textView3);
        Group = (TextView) findViewById(R.id.textView4);

        houseList.setVisibility(View.GONE);
        groupList.setVisibility(View.GONE);
        House.setVisibility(View.GONE);
        Group.setVisibility(View.GONE);

        houses = new ArrayList<>();

        String url = "http://188.226.144.157/group1/find_allhouses.php";
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONArray jsonArr = new JSONArray();

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, url, jsonArr, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {
                try {
                    for (int i = 0; i < response1.length(); i++) {
                        houses.add(response1.getString(i));
                        Log.i("HOBB", response1.getString(i));

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, houses);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        houseList.setAdapter(adapter);

                        houseList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                                houseData = houses.get(position);
                                String houseData2 = houseData.replace("House ", "");
                                fillGroupSpinner(houseData2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                Toast t = new Toast(getApplicationContext());
                                t.makeText(getApplicationContext(), "Please select a primary major ('Undeclared' if you have none)", Toast.LENGTH_LONG).show();

                            }
                        });
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


        groups = new ArrayList<>();
        fillGroupSpinner(groupData);


        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        houseList.setVisibility(View.VISIBLE);
                        groupList.setVisibility(View.VISIBLE);
                        House.setVisibility(View.VISIBLE);
                        Group.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        houseList.setVisibility(View.GONE);
                        groupList.setVisibility(View.GONE);
                        House.setVisibility(View.GONE);
                        Group.setVisibility(View.GONE);
                        break;
                }
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = name.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                Vpassword = vpassword.getText().toString();

                if ((houseData == "Nothing") || (groupData == "Nothing")) {
                    Toast t = new Toast(getApplicationContext());
                    t.makeText(getApplicationContext(), "You must select a valid option for both houses!", Toast.LENGTH_LONG).show();
                } else if (!Email.contains("@aucegypt.edu")) {
                    Toast t = new Toast(getApplicationContext());
                    t.makeText(getApplicationContext(), "You must sign up using an AUC email!", Toast.LENGTH_LONG).show();
                } else if (!Password.equals(Vpassword)) {
                    Toast t = new Toast(getApplicationContext());
                    t.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
                } else if (Password == null) {
                    Toast t = new Toast(getApplicationContext());
                    t.makeText(getApplicationContext(), "Please enter a password!", Toast.LENGTH_LONG).show();
                } else {
                    Toast t = new Toast(getApplicationContext());
                    t.makeText(getApplicationContext(), "Creating Profile!", Toast.LENGTH_LONG).show();
                    mAuth = FirebaseAuth.getInstance();
                    // mAuth.addAuthStateListener(mAuthListener);
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Toast t = new Toast(getApplicationContext());
                                Log.d("TAG", "onComplete: Failed=" + task.getException().getMessage());
                                if (task.getException().getMessage().contains("password"))
                                    t.makeText(getApplicationContext(), "Your password needs to be at least 6 characters, please try again!", Toast.LENGTH_SHORT).show();
                                else if (task.getException().getMessage().contains("email address"))
                                    t.makeText(getApplicationContext(), "This email is already registered, please try again!", Toast.LENGTH_SHORT).show();
                                else
                                    t.makeText(getApplicationContext(), "There was an error creating your profile, please try again!", Toast.LENGTH_SHORT).show();

                            } else {
                                if (!sent) {
                                    sent = true;
                                    sendVerificationEmail();
                                }
                            }
                        }
                    });
                }


            }
        });

    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
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


    private void sendVerificationEmail() {
        //Toast.makeText(getApplicationContext(), "Sending Verification Email, This Might Take A Few Moments...", Toast.LENGTH_LONG).show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // email sent
                                // after email is sent just logout the user and finish this activity
                                SharedPreferences email = getSharedPreferences("FYEPrefs", 0);
                                SharedPreferences.Editor editor = email.edit();
                                editor.putString("Name", Name);
                                editor.putBoolean("First", false);
                                editor.putString("Email", Email);
                                editor.putString("Password", Password);
                                editor.putString("houseData", houseData);
                                editor.putString("groupData", groupData);
                                editor.apply();
                                startActivity(new Intent(getApplicationContext(), VerificationActivity.class));
                                finish();
                            } else {
                                // email not sent, so display message and restart the activity or do whatever you wish to do
                                //restart this activity
                                Toast t = new Toast(getApplicationContext());
                                t.makeText(getApplicationContext(), "There was an error sending the verification email, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void fillGroupSpinner(String house) {
        groups.clear();
        String url = "http://188.226.144.157/group1/find_groups.php?house=" + house;
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest Stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject storeData = result.getJSONObject(0);
                    List<String> items = Arrays.asList(storeData.getString("groups").split("\\s*,\\s*"));
                    for (int i = 0; i < items.size(); i++) {
                        groups.add(items.get(i));
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, groups);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupList.setAdapter(adapter2);

                    groupList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                            groupData = groups.get(position);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast t = new Toast(getApplicationContext());
                            t.makeText(getApplicationContext(), "Please select a second major ('No Second Major' if you are not a double major)", Toast.LENGTH_LONG).show();

                        }
                    });

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
                });
        requestQueue.add(Stringrequest);

    }


}