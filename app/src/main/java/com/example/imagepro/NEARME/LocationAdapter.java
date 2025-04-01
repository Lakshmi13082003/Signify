package com.example.imagepro.NEARME;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.imagepro.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {



    private final List<LocationItem> locationList;
    private final Context context;

    public LocationAdapter(List<LocationItem> locationList, Context context) {
        this.locationList = locationList;
        this.context = context;
    }



    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationItem locationItem = locationList.get(position);
        holder.locationName.setText(locationItem.getName());

        // Set the same location icon for all items
        holder.locationIcon.setImageResource(R.drawable.placeholder); // Use your location icon resource

        holder.itemView.setOnClickListener(v -> {
            String locationType = locationItem.getQuery(); // Example: "hospitals near me"
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(locationType));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;
        ImageView locationIcon;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.locationName);
            locationIcon = itemView.findViewById(R.id.locationIcon);
        }
    }
}