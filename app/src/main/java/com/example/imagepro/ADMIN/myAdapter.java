package com.example.imagepro.ADMIN;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagepro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    private ArrayList<DataClass> datalist;
    private Context context;

    public myAdapter(ArrayList<DataClass> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass dataClass = datalist.get(position);

        // Check if imageUrl is not null before loading it
        String imageUrl = dataClass.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.uploadimage) // Show a placeholder while loading
                    .into(holder.recyclerImage);
        } else {
            // Set a placeholder image if imageUrl is null or empty
            holder.recyclerImage.setImageResource(R.drawable.uploadimage);
        }

        holder.recyuclerCaption.setText(dataClass.getCaption());
        holder.recyclerSub.setText(dataClass.getSubject());

        // Set OnClickListener for the item to delete on click
        holder.itemView.setOnClickListener(v -> {
            // Show an AlertDialog to confirm deletion
            new AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Remove item from Firebase
                        deleteItem(dataClass);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void deleteItem(DataClass dataClass) {
        String itemKey = dataClass.getKey();
        String imageUrl = dataClass.getImageUrl(); // Get the image URL

        if (itemKey != null && imageUrl != null && !imageUrl.isEmpty()) {
            // Delete image from Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
            storageReference.delete()
                    .addOnSuccessListener(aVoid -> {
                        // Image deleted successfully, now delete the database entry
                        deleteDatabaseEntry(itemKey, dataClass);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to delete image from storage", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(context, "Item key or image URL is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteDatabaseEntry(String itemKey, DataClass dataClass) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        databaseReference.child(itemKey).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    datalist.remove(dataClass);
                    notifyDataSetChanged(); // Refresh the adapter
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete item from database", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyuclerCaption;
        TextView recyclerSub;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyuclerCaption = itemView.findViewById(R.id.recyclerCaption);
            recyclerSub = itemView.findViewById(R.id.recyclersubject);
        }
    }
}
