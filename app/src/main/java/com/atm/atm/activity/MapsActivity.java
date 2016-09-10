package com.atm.atm.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;
import android.util.Log;
import android.widget.Toast; //Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show();

import com.atm.atm.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    boolean mRequestingLocationUpdates = false;

    List<Event> events = new ArrayList<>();
    List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //print()s for lifecycle
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        while (mLastLocation == null) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (mMap != null) {
            double lat = mLastLocation.getLatitude();
            double lng = mLastLocation.getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setSmallestDisplacement(3);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            //if switching activities loses location then consider removing !mRequestingLocationUpdates
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (mGoogleApiClient, mLocationRequest, this);
            mRequestingLocationUpdates = true;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng_one = new LatLng(59.421987, 24.805142);
        Location location_one = new Location("one");
        location_one.setLatitude(latLng_one.latitude);
        location_one.setLongitude(latLng_one.longitude);

        LatLng latLng_two = new LatLng(59.423320, 24.793319);
        Location location_two = new Location("two");
        location_two.setLatitude(latLng_two.latitude);
        location_two.setLongitude(latLng_two.longitude);

        Event one = new Event(
                "Join us and lets throw the greatest party ever!!",
                latLng_one,
                "100000090836482",
                "Juku",
                "http://kippure.com/img/party-venue-wicklow.jpg",
                "#party"
        );
        events.add(one);
        Event two = new Event(
                "Bored silly at this birthday, send help please.",
                latLng_one,
                "10000009083612",
                "Tim",
                "http://naibuzz.com/wp-content/uploads/2014/08/boring-party.jpg",
                "#killme"
        );
        events.add(two);

        for (Event e : events) {
            /*BitmapDescriptor icon;
            try {
                URL url = new URL(e.img_url);
                Bitmap bmp = BitmapFactory.decodeStream(url.openStream());
                icon = BitmapDescriptorFactory.fromBitmap(bmp);
            } catch (Exception ex) {
                icon = null;
            }*/

                Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(e.latLng)
                                .title(e.description)
                                //.icon(icon)
                );

                markers.add(marker);
        }

        if (mLastLocation != null) {
            double lat = mLastLocation.getLatitude();
            double lng = mLastLocation.getLongitude();
            LatLng latLng = new LatLng(lat,lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
        mMap.setMyLocationEnabled(true);


    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        print("Connection failed");
    }

    @Override
    public void onDestroy() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
        mGoogleApiClient.disconnect();

        super.onDestroy();
    }

    private void print(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        Log.i("MapsActivity", info);
    }
}
