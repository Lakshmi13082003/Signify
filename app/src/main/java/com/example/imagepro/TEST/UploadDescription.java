package com.example.imagepro.TEST;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imagepro.ADMIN.DataClass;
import com.example.imagepro.ADMIN.LeranAdapter;
import com.example.imagepro.ADMIN.myAdapter;
import com.example.imagepro.ADMIN.uploadAct;
import com.example.imagepro.Common.RequestPojo;
import com.example.imagepro.Common.Utility;
import com.example.imagepro.MainActivity;
import com.example.imagepro.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UploadDescription extends AppCompatActivity {

    private FloatingActionButton fab;


    ListView listView;
    List<RequestPojo> packageList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_description);


        listView = findViewById(R.id.viewLearnlist);


        getLearn();

        fab = findViewById(R.id.fab);



        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), uploadAct.class);
            startActivity(intent);
            finish();
        });


        listView.setOnItemClickListener((parent, view, position, id) -> {
            RequestPojo selectedItem = packageList.get(position); // Get selected item
            String L_id = selectedItem.getL_id(); // Extract L_id from response

            if (L_id == null || L_id.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Error: L_id not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show confirmation dialog
            new AlertDialog.Builder(UploadDescription.this)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to remove this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        removeLearnItem(L_id); // Call function to remove item
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }

    private void removeLearnItem(String lId)
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl,
                response -> {
                    if (response.trim().equals("success")) {
                        Toast.makeText(getApplicationContext(), "Item Removed", Toast.LENGTH_SHORT).show();

                        recreate();// Refresh the list after deletion
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to remove item", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show())
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("key", "deleteLearn");
                map.put("L_id", lId); // Pass L_id to server
                return map;
            }
        };

        queue.add(request);
    }

    private void getLearn()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    packageList = Arrays.asList(gson.fromJson(response, RequestPojo[].class));
                    LeranAdapter adapter = new LeranAdapter(UploadDescription.this, packageList);
                    listView.setAdapter(adapter);
                    registerForContextMenu(listView);
                } else {
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("sharedData", MODE_PRIVATE);
                final String reg_id = prefs.getString("reg_id", "No_id");
                final String type = prefs.getString("type", "NO-Type");
                Map<String, String> map = new HashMap<>();
                map.put("key", "viewLearn");
                return map;
            }
        };
        queue.add(request);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Start MainActivity
                        Intent intent = new Intent(UploadDescription.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Finish the current activity
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
