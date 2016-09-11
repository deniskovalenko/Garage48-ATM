package com.atm.atm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atm.atm.R;
import com.atm.atm.RestService;
import com.facebook.AccessToken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventCreateActivity extends AppCompatActivity {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("multipart/form-data/");

    private final OkHttpClient client = new OkHttpClient();
    File imageFile = null;
    Bitmap bitmap;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://atmapi.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RestService restService = retrofit.create(RestService.class);

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
                        imageFile = File.createTempFile(
                                imageFileName,  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* directory */
                        );
                        String mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath();

                    } catch (IOException ex) {

                    }
                    print("imagepath is \"" + imageFile.getAbsolutePath() + "\"");

                    if (imageFile != null) {

                        Uri photoURI = Uri.fromFile(imageFile); //FileProvider.getUriForFile(EventCreateActivity.this.getApplicationContext(), "com.example.android.fileprovider", imagepath);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        print("successful");

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

            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            //((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
            final LinearLayout cameraLayout = (LinearLayout) findViewById(R.id.cameraLayout);
            cameraLayout.setBackground(drawable);
        }
    }

    public void onCloseButtonClick(View view) {
//        print("close");
        finish();
    }
    public void onCreateButtonClick (View view) {
        //TODO create event
        //upload
        //get url
//
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
//
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("uploaded_file", imageFile.getName(), requestFile);
//
////        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), );
//        TypedFile typedFile = new TypedFile("multipart/form-data", new File("path/to/your/file"));

        // create RequestBody instance from file
        final String name = getSharedPreferences("APPLICATION", MODE_APPEND).getString("user_name", "Denis Kovalenko");
        final String hash = ((EditText) findViewById(R.id.descriptionEditText)).getText().toString();
        final String desc =  ((EditText) findViewById(R.id.descriptionEditText)).getText().toString();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", "logo-square.png",
                                RequestBody.create(MEDIA_TYPE_PNG, imageFile))
                        .build();

//                59.421031,
//                24.810168, //todo real location
//                60,
//                AccessToken.getCurrentAccessToken().getUserId(),
//                getSharedPreferences("APPLICATION", MODE_APPEND).getString("user_name", "Denis Kovalenko"),
//
                Request request = new Request.Builder()
                        .url("https://atmapi.herokuapp.com/upload?description="
                                + desc
                                + "&lat=" + 59.421031
                                + "&lon=" + 24.810168
                                + "&time_limit=60"
                                + "&host_id=" + AccessToken.getCurrentAccessToken().getUserId()
                                + "&host_name= " + getSharedPreferences("APPLICATION", MODE_APPEND).getString("user_name", "Denis Kovalenko")
                                +"&hashtag=" + hash)
                        .post(requestBody)
                        .build();

                try {
                    okhttp3.Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                Request request = new Request.Builder()
//                        .url()
//                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, imageFile))
//                        .build();
//
//                okhttp3.Response response = null;
//                try {
//                    response = client.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (!response.isSuccessful()) try {
//                    throw new IOException("Unexpected code " + response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                EventCreateActivity.this.finish();
            }
        }.execute() ;


//
//
//
//
//
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("image/*"), imageFile);
//
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
//
//        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload-text");
//
//        Call<ResponseBody> call =restService.sendEvent(body,
//                ((EditText) findViewById(R.id.descriptionEditText)).getText().toString(),
//                59.421031,
//                24.810168, //todo real location
//                60,
//                AccessToken.getCurrentAccessToken().getUserId(),
//                getSharedPreferences("APPLICATION", MODE_APPEND).getString("user_name", "Denis Kovalenko"),
//                ((EditText) findViewById(R.id.descriptionEditText)).getText().toString()
//        );
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                EventCreateActivity.this.finish();
//                print("Event created");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                EventCreateActivity.this.finish();
//            }
//        });
//        print("create");

    }
    
    private void print(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        Log.i(this.getClass().getSimpleName(), info);
    }

}
