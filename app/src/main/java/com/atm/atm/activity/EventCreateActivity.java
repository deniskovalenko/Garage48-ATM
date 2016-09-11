package com.atm.atm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atm.atm.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCreateActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    File imagepath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        final LinearLayout cameraLayout = (LinearLayout) findViewById(R.id.cameraLayout);
        cameraLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_";
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    try {
                        imagepath = File.createTempFile(
                                imageFileName,  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* directory */
                        );
                        String mCurrentPhotoPath = "file:" + imagepath.getAbsolutePath();

                    } catch (IOException ex) {

                    }


                    if (imagepath != null) {

                        Uri photoURI = Uri.fromFile(imagepath); //FileProvider.getUriForFile(EventCreateActivity.this.getApplicationContext(), "com.example.android.fileprovider", imagepath);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }


                //TODO
                //update linearlayout background

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);*/

            Bitmap bitmap = BitmapFactory.decodeFile(imagepath.getAbsolutePath());
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            //((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
            final LinearLayout cameraLayout = (LinearLayout) findViewById(R.id.cameraLayout);
            cameraLayout.setBackground(drawable);
        }
    }

    public void onCloseButtonClick(View view) {
        finish();
    }
    public void onCreateButtonClick (View view) {
        //TODO create event
        //upload
        //get url
    }
    
    private void print(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        Log.i(this.getClass().getSimpleName(), info);
    }

}
