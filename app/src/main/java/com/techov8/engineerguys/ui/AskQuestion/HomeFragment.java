package com.techov8.engineerguys.ui.AskQuestion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.techov8.engineerguys.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;



public class HomeFragment extends Fragment {

    private Button sendquestion;
    private EditText inputQuestion;
    private ImageView CameraScan, image;

    Bitmap thumbnail;
    File pic;

    protected static final int CAMERA_PIC_REQUEST = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sendquestion = view.findViewById(R.id.sendbtn);
        CameraScan = view.findViewById(R.id.scanCamera);
        inputQuestion = view.findViewById(R.id.AskqustioneditText);

        image = view.findViewById(R.id.imageView2);


        CameraScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            }
        });


        sendquestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"fake@fake.edu"});
                i.putExtra(Intent.EXTRA_SUBJECT, "On The Job");
                i.putExtra(Intent.EXTRA_TEXT, inputQuestion.getText().toString());
                //Log.d("URI@!@#!#!@##!", Uri.fromFile(pic).toString() + "   " + pic.exists());
                if(pic != null)
                {  i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pic));}
                else{
                    Toast.makeText(getContext(),"null", Toast.LENGTH_SHORT).show();
                }

                i.setType("image/*");
              //  i.setType("message/rfc822");

                startActivity(Intent.createChooser(i, "Share you on the jobing"));
            }
        });


        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            thumbnail = (Bitmap) data.getExtras().get("data");

            image.setImageBitmap(thumbnail);


            try {
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    pic = new File(root, "pic.jpg");
                    FileOutputStream out = new FileOutputStream(pic);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {

                Log.e("BROKEN", "Could not write file " + e.getMessage());
            }

        }


    }
}