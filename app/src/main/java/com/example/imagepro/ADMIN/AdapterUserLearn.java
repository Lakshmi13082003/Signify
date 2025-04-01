package com.example.imagepro.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imagepro.R;

import java.util.ArrayList;

public class AdapterUserLearn extends RecyclerView.Adapter<myAdapter.MyViewHolder> {


    private ArrayList<DataClass> datalist;
    private Context context;

    public AdapterUserLearn(ArrayList<DataClass> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }



    @NonNull
    @Override
    public myAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new myAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.MyViewHolder holder, int position)
    {
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
//        holder.itemView.setOnClickListener(v -> {
//            // Show an AlertDialog to confirm deletion
//            new AlertDialog.Builder(context)
//                    .setTitle("Delete Item")
//                    .setMessage("Are you sure you want to delete this item?")
//                    .setPositiveButton("Yes", (dialog, which) -> {
//                        // Remove item from Firebase
//                       // deleteItem(dataClass);
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
//        });
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