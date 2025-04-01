package com.example.imagepro.NEARME;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.imagepro.R;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class NearmeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearme);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchLocationData();
    }


    private void fetchLocationData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nearme");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<LocationItem> locationList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.getValue(String.class); // Fetch the location name
                    String query = name + " near me";  // Generate the query for Google Maps

                    locationList.add(new LocationItem(name, query));
                }

                // Update RecyclerView with fetched data
                LocationAdapter adapter = new LocationAdapter(locationList, NearmeActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }
}