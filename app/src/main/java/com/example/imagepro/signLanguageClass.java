package com.example.imagepro;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.A;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class  signLanguageClass {
    // should start from small letter

    // this is used to load model and predict
    private Interpreter interpreter;
    private Interpreter interpreter2;
    // store all label in array
    private List<String> labelList;
    private int INPUT_SIZE;
    private int PIXEL_SIZE=3; // for RGB
    private int IMAGE_MEAN=0;
    private  float IMAGE_STD=255.0f;
    // use to initialize gpu in app
    private GpuDelegate gpuDelegate;
    private int height=0;
    private  int width=0;

    private int Classification_Input_Size=0;
    private String final_text="";
    private String current_text="";





    signLanguageClass(Button clearButon, Button addButton, TextView changeText, AssetManager assetManager, String modelPath, String labelPath, int inputSize , String classification_model, int classification_input_size) throws IOException{
        INPUT_SIZE=inputSize;
        Classification_Input_Size = classification_input_size;
        // use to define gpu or cpu // no. of threads
        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4); // set it according to your phone
        // loading model
        interpreter=new Interpreter(loadModelFile(assetManager,modelPath),options);
        // load labelmap
        labelList=loadLabelList(assetManager,labelPath);

        Interpreter.Options option2 = new Interpreter.Options();
        option2.setNumThreads(2);
        interpreter2= new Interpreter(loadModelFile(assetManager,classification_model), option2);

        clearButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final_text="";
                    changeText.setText(final_text);

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final_text=final_text+current_text;
                changeText.setText(final_text);
            }
        });





    }

    private List<String> loadLabelList(AssetManager assetManager, String labelPath) throws IOException {
        // to store label
        List<String> labelList=new ArrayList<>();
        // create a new reader
        BufferedReader reader=new BufferedReader(new InputStreamReader(assetManager.open(labelPath)));
        String line;
        // loop through each line and store it to labelList
        while ((line=reader.readLine())!=null){
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        // use to get description of file
        AssetFileDescriptor fileDescriptor=assetManager.openFd(modelPath);
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset =fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }
    // create new Mat function
    public Mat recognizeImage(Mat mat_image) {
        // Rotate original image by 90 degrees to get a portrait frame
        Mat rotated_mat_image = new Mat();
        Mat a = mat_image.t();
        Core.flip(a, rotated_mat_image, 1);
        a.release();

        // Convert Mat to Bitmap
        Bitmap bitmap = Bitmap.createBitmap(rotated_mat_image.cols(), rotated_mat_image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rotated_mat_image, bitmap);

        // Define height and width
        height = bitmap.getHeight();
        width = bitmap.getWidth();

        // Scale the bitmap to the input size of the model
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

        // Define output map
        Object[] input = new Object[1];
        input[0] = byteBuffer;
        Map<Integer, Object> output_map = new TreeMap<>();

        float[][][] boxes = new float[1][10][4];
        float[][] scores = new float[1][10];
        float[][] classes = new float[1][10];

        output_map.put(0, boxes);
        output_map.put(1, classes);
        output_map.put(2, scores);

        // Run the model
        interpreter.runForMultipleInputsOutputs(input, output_map);

        // Extract results
        Object value = output_map.get(0);
        Object Object_class = output_map.get(1);
        Object score = output_map.get(2);

        // Loop through detected objects
        for (int i = 0; i < 10; i++) {
            float class_value = (float) Array.get(Array.get(Object_class, 0), i);
            float score_value = (float) Array.get(Array.get(score, 0), i);

            if (score_value > 0.5) { // Apply threshold
                Object box1 = Array.get(Array.get(value, 0), i);

                float y1 = (float) Array.get(box1, 0) * height;
                float x1 = (float) Array.get(box1, 1) * width;
                float y2 = (float) Array.get(box1, 2) * height;
                float x2 = (float) Array.get(box1, 3) * width;

                // Ensure coordinates are within bounds
                x1 = Math.max(0, Math.min(x1, width - 1));
                y1 = Math.max(0, Math.min(y1, height - 1));
                x2 = Math.max(0, Math.min(x2, width));
                y2 = Math.max(0, Math.min(y2, height));

                float w1 = Math.max(1, x2 - x1);
                float h1 = Math.max(1, y2 - y1);

                if (x1 + w1 > width) w1 = width - x1;
                if (y1 + h1 > height) h1 = height - y1;

                if (x1 < width && y1 < height && w1 > 0 && h1 > 0) {
                    Rect cropped_roi = new Rect((int) x1, (int) y1, (int) w1, (int) h1);
                    Mat cropped = new Mat(rotated_mat_image, cropped_roi).clone();

                    if (!cropped.empty()) {
                        Bitmap bitmap1 = Bitmap.createBitmap(cropped.cols(), cropped.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(cropped, bitmap1);
                        Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, Classification_Input_Size, Classification_Input_Size, false);
                        ByteBuffer byteBuffer1 = convertBitmapToByteBuffer1(scaledBitmap1);

                        float[][] out_put_class_value = new float[1][1];
                        interpreter2.run(byteBuffer1, out_put_class_value);
                        Log.d("signLanguageClass", "out_put_class_value :" + out_put_class_value[0][0]);

                        String sign_val = get_aphabets(out_put_class_value[0][0]);
                        current_text = sign_val;

                        Imgproc.putText(rotated_mat_image, "" + sign_val, new Point(x1 + 10, y1 + 40), 2, 1.5, new Scalar(255, 255, 255, 255), 2);
                        Imgproc.rectangle(rotated_mat_image, new Point(x1, y1), new Point(x2, y2), new Scalar(0, 255, 0, 255), 2);
                    } else {
                        Log.e("signLanguageClass", "Cropped Mat is empty! Skipping processing.");
                    }
                } else {
                    Log.e("signLanguageClass", "Invalid crop region: x1=" + x1 + ", y1=" + y1 + ", w1=" + w1 + ", h1=" + h1);
                }
            }
        }

        // Rotate back to original orientation
        Mat b = rotated_mat_image.t();
        Core.flip(b, mat_image, 0);
        b.release();

        return mat_image;
    }

    private String get_aphabets(float sig_v)
    {
        String val="";

        if (sig_v >= -0.5 && sig_v < 0.5) {
            val = "A";
        } else if (sig_v >= 0.5 && sig_v < 1.5) {
            val = "B";
        } else if (sig_v >= 1.5 && sig_v < 2.5) {
            val = "C";
        } else if (sig_v >= 2.5 && sig_v < 3.5) {
            val = "D";
        } else if (sig_v >= 3.5 && sig_v < 4.5) {
            val = "E";
        } else if (sig_v >= 4.5 && sig_v < 5.5) {
            val = "F";
        } else if (sig_v >= 5.5 && sig_v < 6.5) {
            val = "G";
        } else if (sig_v >= 6.5 && sig_v < 7.5) {
            val = "H";
        } else if (sig_v >= 7.5 && sig_v < 8.5) {
            val = "I";
        } else if (sig_v >= 8.5 && sig_v < 9.5) {
            val = "J";
        } else if (sig_v >= 9.5 && sig_v < 10.5) {
            val = "K";
        } else if (sig_v >= 10.5 && sig_v < 11.5) {
            val = "L";
        } else if (sig_v >= 11.5 && sig_v < 12.5) {
            val = "M";
        } else if (sig_v >= 12.5 && sig_v < 13.5) {
            val = "N";
        } else if (sig_v >= 13.5 && sig_v < 14.5) {
            val = "O";
        } else if (sig_v >= 14.5 && sig_v < 15.5) {
            val = "P";
        } else if (sig_v >= 15.5 && sig_v < 16.5) {
            val = "Q";
        } else if (sig_v >= 16.5 && sig_v < 17.5) {
            val = "R";
        } else if (sig_v >= 17.5 && sig_v < 18.5) {
            val = "S";
        } else if (sig_v >= 18.5 && sig_v < 19.5) {
            val = "T";
        } else if (sig_v >= 19.5 && sig_v < 20.5) {
            val = "U";
        } else if (sig_v >= 20.5 && sig_v < 21.5) {
            val = "V";
        } else if (sig_v >= 21.5 && sig_v < 22.5) {
            val = "W";
        } else if (sig_v >= 22.5 && sig_v < 23.5) {
            val = "X";
        } else {
            val = "Y"; // Handles values outside the specified ranges
        }

        return val;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        // some model input should be quant=0  for some quant=1
        // for this quant=0
        // Change quant=1
        // As we are scaling image from 0-255 to 0-1
        int quant=1;
        int size_images=INPUT_SIZE;
        if(quant==0){
            byteBuffer=ByteBuffer.allocateDirect(1*size_images*size_images*3);
        }
        else {
            byteBuffer=ByteBuffer.allocateDirect(4*1*size_images*size_images*3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int[size_images*size_images];
        bitmap.getPixels(intValues,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        int pixel=0;

        // some error
        //now run
        for (int i=0;i<size_images;++i){
            for (int j=0;j<size_images;++j){
                final  int val=intValues[pixel++];
                if(quant==0){
                    byteBuffer.put((byte) ((val>>16)&0xFF));
                    byteBuffer.put((byte) ((val>>8)&0xFF));
                    byteBuffer.put((byte) (val&0xFF));
                }
                else {
                    // paste this
                    byteBuffer.putFloat((((val >> 16) & 0xFF))/255.0f);
                    byteBuffer.putFloat((((val >> 8) & 0xFF))/255.0f);
                    byteBuffer.putFloat((((val) & 0xFF))/255.0f);
                }
            }
        }
        return byteBuffer;
    }





    private ByteBuffer convertBitmapToByteBuffer1(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        // some model input should be quant=0  for some quant=1
        // for this quant=0
        // Change quant=1
        // As we are scaling image from 0-255 to 0-1
        int quant=1;
        int size_images=Classification_Input_Size;
        if(quant==0){
            byteBuffer=ByteBuffer.allocateDirect(1*size_images*size_images*3);
        }
        else {
            byteBuffer=ByteBuffer.allocateDirect(4*1*size_images*size_images*3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int[size_images*size_images];
        bitmap.getPixels(intValues,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        int pixel=0;

        for (int i=0;i<size_images;++i){
            for (int j=0;j<size_images;++j){
                final  int val=intValues[pixel++];
                if(quant==0){
                    byteBuffer.put((byte) ((val>>16)&0xFF));
                    byteBuffer.put((byte) ((val>>8)&0xFF));
                    byteBuffer.put((byte) (val&0xFF));
                }
                else {
                    // paste this
                    byteBuffer.putFloat((((val >> 16) & 0xFF)));
                    byteBuffer.putFloat((((val >> 8) & 0xFF)));
                    byteBuffer.putFloat((((val) & 0xFF)));
                }
            }
        }
        return byteBuffer;
    }
}
// Next video is about drawing box and labeling it
// If you have any problem please inform me