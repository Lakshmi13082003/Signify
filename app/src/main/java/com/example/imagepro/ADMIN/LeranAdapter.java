package com.example.imagepro.ADMIN;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.imagepro.Common.Base64;
import com.example.imagepro.Common.RequestPojo;
import com.example.imagepro.R;

import java.io.IOException;
import java.util.List;

public class LeranAdapter extends ArrayAdapter<RequestPojo> {

    private Activity context;
    private List<RequestPojo> rest_List;

    String base;

    public LeranAdapter(Activity context, List<RequestPojo> rest_List)
    {
        super(context, R.layout.activity_leran_adapter, rest_List);
        this.context = context;
        this.rest_List = rest_List;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_leran_adapter, parent, false);

        ImageView uploadedImage = view.findViewById(R.id.uploadedImage);
        TextView descriptionValue = view.findViewById(R.id.descriptionValue);
        TextView subjectValue = view.findViewById(R.id.subjectValue);





        descriptionValue.setText(rest_List.get(position).getL_description());
        subjectValue.setText(rest_List.get(position).getL_subject());
        base = rest_List.get(position).getL_image();



        try {
            byte[] imageAsBytes = Base64.decode(base.getBytes());
            uploadedImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

}