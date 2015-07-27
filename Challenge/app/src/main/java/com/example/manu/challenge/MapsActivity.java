package com.example.manu.challenge;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //SharedPreferences mySettings = getSharedPreferences("MyPrefsFile", 0);
    private double savedLat = 37.774929;
    private double savedLng = -122.419416;
    private float savedZoom = 4f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // Gets previous latitude and Longitude values saved in the app
/*        if(mySettings.contains("zoom"))
        {
            savedZoom = mySettings.getFloat("zoom", 4);
            savedLng = (double) mySettings.getFloat("longitude", -122.419416f);
            savedLat = (double) mySettings.getFloat("latitude", 37.774929f);
        }*/

        // Settings of Map
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //mMap.getUiSettings().setIndoorLevelPickerEnabled(false);

        // Locates location of user
        Location myLocation = null;
        boolean er = false;

        try{
            // tries to find my location
            LocationManager myLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(myLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                myLocation = mMap.getMyLocation();
            } else
            {
                er = true;
            }

        } catch(Exception e)
        {
            er = true;
        }
        LatLng myLatLng;

        if (er == true) {
            // sets a default location for the coordinates (San Francisco)
            myLatLng = new LatLng(37.774929, -122.419416);
        } else {
            myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        }

        // zooms map to designated location
        zoomMap(myLatLng, (mMap.getMaxZoomLevel() + 2f));
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    private void zoomMap(LatLng myLatLng, float zoomVal)
    {
        if(mMap == null) {
            return;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoomVal));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_maps))
                    .getMap();
        }
    }
}
