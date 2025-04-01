package com.example.imagepro.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imagepro.R;
import com.example.imagepro.TEST.UploadDescription;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginPage extends AppCompatActivity {

    EditText loginUserNAme, loginpassword;
    Button loginbutton;

    private static final String TAG = "AdminLoginPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);

        loginUserNAme = findViewById(R.id.login_username);
        loginpassword = findViewById(R.id.login_password);
        loginbutton = findViewById(R.id.login_button);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() || !validatePassword()) {
                    // Do nothing, validation failed
                } else {
                    checkUser();
                }
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUserNAme.getText().toString();
        if (val.isEmpty()) {
            loginUserNAme.setError("User name cannot be empty");
            return false;
        } else {
            loginUserNAme.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginpassword.getText().toString();
        if (val.isEmpty()) {
            loginpassword.setError("Password cannot be empty");
            return false;
        } else {
            loginpassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = loginUserNAme.getText().toString().trim();
        String userPassword = loginpassword.getText().toString().trim();

        // Log the input data for debugging
        Log.d(TAG, "Input Username: " + userUsername);
        Log.d(TAG, "Input Password: " + userPassword);

        // Directly reference the "admin" node
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child("admin");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve username and password from the snapshot
                    String usernameFromDB = snapshot.child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    // Log the retrieved data for debugging
                    Log.d(TAG, "Database Username: " + usernameFromDB);
                    Log.d(TAG, "Database Password: " + passwordFromDB);

                    if (usernameFromDB != null && passwordFromDB != null) {
                        // Compare input with retrieved data
                        if (userUsername.equals(usernameFromDB) && userPassword.equals(passwordFromDB)) {
                            loginUserNAme.setError(null);

                            // Redirect to admin home page
                            Intent intent = new Intent(AdminLoginPage.this, UploadDescription.class);
                            startActivity(intent);
                            finish(); // Finish the current activity
                        } else {
                            loginpassword.setError("Invalid credentials");
                            loginpassword.requestFocus();
                        }
                    } else {
                        loginpassword.setError("Username or password not found in database");
                        loginpassword.requestFocus();
                    }
                } else {
                    loginUserNAme.setError("User does not exist");
                    loginUserNAme.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }
}
