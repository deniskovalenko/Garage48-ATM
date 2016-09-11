package com.atm.atm.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.atm.atm.MyItemRecyclerViewAdapter;
import com.atm.atm.R;
import com.atm.atm.RestService;
import com.atm.atm.dummy.DummyContent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements MyItemRecyclerViewAdapter.OnJoinButtonPressed,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://atmapi.herokuapp.com/")
            .build();
    RestService restService = retrofit.create(RestService.class);
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize recycler view
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);



        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                         @Override
                         public void onRefresh() {
                             refreshContent();
                         }
                     });

        findViewById(R.id.button_create_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventCreateActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       refreshContent();
    }

    private void refreshContent() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Call<ResponseBody> call;
        if (mLastLocation !=null) {
            call = restService.queryEvents(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            call  = restService.queryEvents();
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyItemRecyclerViewAdapter adapter = null;
                try {
                    adapter = new MyItemRecyclerViewAdapter(response.body().string(), MainActivity.this, 1);
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to refresh, retry in a moment", Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onListFragmentInteraction(DummyContent.ATMEventViewModel item) {
        refreshContent();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
