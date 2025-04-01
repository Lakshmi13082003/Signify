package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imagepro.ADMIN.LeranAdapter;
import com.example.imagepro.Common.RequestPojo;
import com.example.imagepro.Common.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LearnActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    List<RequestPojo> packageList;
    LeranAdapter adapter;
    List<RequestPojo> filteredList; // List for search filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        listView = findViewById(R.id.viewLearnlistUser);
        searchView = findViewById(R.id.searchView);

        getLearn();

        // SearchView Filtering Logic
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void getLearn() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && !response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    packageList = Arrays.asList(gson.fromJson(response, RequestPojo[].class));
                    filteredList = new ArrayList<>(packageList); // Initialize filtered list
                    adapter = new LeranAdapter(LearnActivity.this, filteredList);
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
                Map<String, String> map = new java.util.HashMap<>();
                map.put("key", "viewLearn");
                return map;
            }
        };
        queue.add(request);
    }

    // Function to filter list based on search input
    private void filterList(String query) {
        if (packageList == null) {
            return; // Don't proceed if the list is null
        }

        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(packageList);
        } else {
            for (RequestPojo item : packageList) {
                if (item.getL_subject().toLowerCase().contains(query.toLowerCase())) { // Change getL_subject() to relevant field
                    filteredList.add(item);
                }
            }
        }

        adapter.notifyDataSetChanged(); // Refresh ListView
    }

}
