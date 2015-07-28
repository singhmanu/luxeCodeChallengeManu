package com.example.manu.challenge;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // map
    private SearchView search; // search view
    private Marker[] markers;
    private int markerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // Settings of the GoogleMap
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        // Search
        search = (SearchView) findViewById(R.id.searchAddress);
        search.bringToFront();
        search.setQueryHint("SearchView");

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SearchView search1 = (SearchView) findViewById(R.id.searchAddress);
                if (hasFocus)
                {
                    search1.setBackgroundColor(0x40ffffff);
                } else {
                    search1.setBackgroundColor(0x80ffffff);
                }
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });
    }

    @Override
    protected void onStart ()
    {
        // Locates location of the user
        Location myLocation = null;
        LatLng myLatLng;
        boolean er = false;

        try
        {
            mMap.setMyLocationEnabled(true);
            myLocation = mMap.getMyLocation();
        } catch (Exception e)
        {
            er = true;
        }

        if (er == true) {
            // Sets a default location for the coordinates (San Francisco)
            myLatLng = new LatLng(37.774929, -122.419416);
        } else {
            myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        }

        // zooms map to designated location
        zoomMap(myLatLng, (14f));
    }

    // Adds a Marker
    private int addMarker(String title, String snippet, LatLng markerLngLat, String color)
    {
        float fColor = BitmapDescriptorFactory.HUE_AZURE;

        if (color == "blue")
        {
            fColor = BitmapDescriptorFactory.HUE_BLUE;
        }

        MarkerOptions markerOpt = new MarkerOptions()
                .position(markerLngLat)
                .title(title)
                .snippet(snippet)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(fColor));

        markers[markerIndex] = mMap.addMarker(markerOpt);

        markerIndex++;
        return markerIndex - 1;
    }

    // Removes a specific marker
    private void removeMarker(int markerI)
    {
        markers[markerI].remove();
        markers[markerI] = null;
    }

    private void doMySearch(String query)
    {

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
