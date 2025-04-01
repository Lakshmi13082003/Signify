package com.example.imagepro.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imagepro.Common.Utility;
import com.example.imagepro.R;
import com.example.imagepro.TEST.UploadDescription;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class uploadAct extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private FloatingActionButton uploadButton;
    private ImageView uploadImage;
    private EditText uploadDescription;
    private EditText uploadSubject;


    Uri imageUri;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_Camera_IMAGE = 2;
    private Bitmap bitmap;
    String bal = "null";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadButton = findViewById(R.id.property_gal);
        uploadImage = findViewById(R.id.uploadImage);
        uploadDescription = findViewById(R.id.uploadDescription);
        uploadSubject = findViewById(R.id.uploadSubject); // New field for subject


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    Intent gintent = new Intent();
                    gintent.setType("image/*");
                    gintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gintent, "Select Picture"), PICK_IMAGE);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                validateAndUpload();
            }
        });
    }

    private void validateAndUpload()
    {
        String subject = uploadSubject.getText().toString().trim();
        String description = uploadDescription.getText().toString().trim();

        if (bal == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }
        if (subject.isEmpty()) {
            Toast.makeText(this, "Please enter a subject", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call function to upload data to Firebase
        uploadToDatabase(imageUri, subject, description);
    }

    private void uploadToDatabase(Uri imageUri, String subject, String description)
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {

                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                SharedPreferences prefs = getApplicationContext().getSharedPreferences("sharedData", MODE_PRIVATE);
                final String uid = prefs.getString("reg_id", "No_idd");//"No name defined" is the default value.
                final String type = prefs.getString("type", "Notype");
                Map<String, String> params = new HashMap<>();


                params.put("key", "addLearning");
                params.put("subject", subject);
                params.put("description", description);
                params.put("image", bal);
                //params.put("qrView", qrView);

                return params;
            }
        };
        queue.add(request);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        String filePath = null;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE:
                    selectedImageUri = data.getData();
                    break;
                case PICK_Camera_IMAGE:
                    selectedImageUri = imageUri;
                    break;
            }

            if (selectedImageUri != null) {
                try {
                    String filemanagerstring = selectedImageUri.getPath();
                    String selectedImagePath = getPath(selectedImageUri);

                    if (selectedImagePath != null) {
                        filePath = selectedImagePath;
                    } else if (filemanagerstring != null) {
                        filePath = filemanagerstring;
                    } else {
                        Toast.makeText(getApplicationContext(), "Unknown path", Toast.LENGTH_LONG).show();
                    }

                    if (filePath != null) {
                        decodeFile(filePath);
                    } else {
                        bitmap = null;
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        }
    }

    public void decodeFile(String filePath) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        final int REQUIRED_SIZE = 1024;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (width_tmp > REQUIRED_SIZE || height_tmp > REQUIRED_SIZE) {
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        uploadImage.setImageBitmap(bitmap);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        bal = android.util.Base64.encodeToString(ba, android.util.Base64.DEFAULT);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
    }

}
