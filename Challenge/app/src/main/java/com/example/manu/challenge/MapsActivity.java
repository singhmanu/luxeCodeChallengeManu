package com.example.manu.challenge;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener, AdapterView.OnItemClickListener {

    private GoogleMap mMap;                 // Google Map
    private SearchView search;              // search view
    private Marker[] markers;               // Markers for addresses
    private int markerIndex = 0;
    private ListView suggestions;           // Suggestion List
    private ArrayAdapter<String> listAdapter;
    private String mKey = "AIzaSyCHHvWg4m_K00vbTV3gJJiJlwNGP4T4Nnc";
    private boolean findLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String ref = "adsf";
        String url = "https://maps.googleapis.com/maps/api/place/details/json?reference="+ref+"sensor=false&"+mKey;

        // GoogleMap setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        //suggestions = (ListView)findViewById(R.id.suggestList);

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
        search.setQueryHint("Search for Places…");

        // When the search view is selected and unselected
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SearchView search1 = (SearchView) findViewById(R.id.searchAddress);
                if (hasFocus == true)
                {
                    search1.setBackgroundColor(0xe0ffffff);
                } else {
                    search1.setBackgroundColor(0x80ffffff);
                }
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        // default to san francisco
        LatLng sFll = new LatLng(37.774929, -122.419416);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sFll, 14));

        //addSuggestion("location1");
        //addSuggestion("location2");
    }

    // Locates location of the user
    @Override
    public void onLocationChanged(Location location) {
        if (findLocation == false)
        {
            return;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        showlog("longlat found");
        findLocation = false;
    }

    private void showlog(String str)
    {
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void addSuggestion(String strSuggest)
    {
        listAdapter.add(strSuggest);
        suggestions.setAdapter(listAdapter);
    }

    private void removeSuggestions()
    {
        listAdapter.clear();
        suggestions.setAdapter(listAdapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
