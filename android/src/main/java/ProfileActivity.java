package com.example.baher.fyeapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    Button logout, changepass;
    TextView txtName,txtEmail;
    EditText newpassword;
    String password, email, Email;
    String name1,email1,house1,group1;
    FirebaseUser user;
    ArrayList<String> majors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        txtName = (TextView)findViewById(R.id.textView11);
        txtEmail = (TextView)findViewById(R.id.textView12);

        logout = (Button) findViewById(R.id.logout);
        changepass = (Button) findViewById(R.id.changepass);
        newpassword = (EditText) findViewById(R.id.pass);

        SharedPreferences user1 = getSharedPreferences("FYEPrefs", 0);
        email1 = user1.getString("Email", "None");
        name1 = user1.getString("Name", "None");
        house1 = user1.getString("houseData", "None");
        group1 = user1.getString("groupData", "None");

        Log.i("name", name1);
        Log.i("name", email1);
        txtName.setText(name1);
        txtEmail.setText(email1);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, majors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newpassword.getText().toString().length() >= 6) {
                    user = FirebaseAuth.getInstance().getCurrentUser();

                    SharedPreferences userr = getSharedPreferences("FYEPrefs", 0);
                    email = userr.getString("Email", "None");
                    password = userr.getString("Password", "None");

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                    AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                    Log.i("email", email);

// Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.i("pass", password);
                                                    Log.d("TAG", "Password updated");
                                                    RequestParams params = new RequestParams();
                                                    SharedPreferences email = getSharedPreferences("FYEPrefs", 0);
                                                    SharedPreferences.Editor editor = email.edit();
                                                    editor.putString("Password", newpassword.getText().toString());
                                                    editor.apply();
                                                    // set our JSON object
                                                    params.put("email", email.getString("Email","None"));
                                                    params.put("password", newpassword.getText().toString());

                                                    AsyncHttpClient client = new AsyncHttpClient();

                                                    client.post("http://188.226.144.157/group1/UpdatePassword.php", params, new JsonHttpResponseHandler() {

                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                                                            Toast.makeText(getApplicationContext(), "Updated Password!", Toast.LENGTH_SHORT).show();
                                                            newpassword.setText("");
                                                        }

                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                            Toast.makeText(getApplicationContext(), "Updated Password!", Toast.LENGTH_SHORT).show();
                                                            newpassword.setText("");
                                                        }
                                                    });

                                                } else {
                                                    Log.d("TAG", "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d("TAG", "Error auth failed");
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Password should be at least 6 characters!", Toast.LENGTH_LONG).show();
                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialogFragment d = new LogoutDialogFragment();
                d.show(getSupportFragmentManager(), "My Dialog");
            }
        });
    }


}