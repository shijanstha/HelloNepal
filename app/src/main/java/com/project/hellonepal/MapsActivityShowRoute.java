
package com.project.hellonepal;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivityShowRoute extends FragmentActivity
        implements OnMapReadyCallback /*, GoogleMap.OnInfoWindowClickListener*/ {

    // The Google Maps object.
    private GoogleMap mMap;

    // The details of the venue that is being displayed.
    String points;

    PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Retrieves venues details from the intent sent from PlacePickerActivity

        setTitle(getIntent().getStringExtra("name"));
        points = getIntent().getStringExtra("points");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        dwawRoute();
        // Centers and zooms the map into the selected venue
        LatLng venue = polylineOptions.getPoints().get(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue, 16));


        // Creates and displays marker and info window for the venue
        Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(venue)
                        .title("Start")
//                .snippet("View on Foursquare")
        );
//        mMap.addMarker(new MarkerOptions()
//                        .position(polylineOptions.getPoints().get(polylineOptions.getPoints().size()))
//                        .title("End")
////                .snippet("View on Foursquare")
//        );
        marker.showInfoWindow();
//        mMap.setOnInfoWindowClickListener(this);

        // Checks for location permissions at runtime (required for API >= 23)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Shows the user's current location
            mMap.setMyLocationEnabled(true);
        }
    }


    public void dwawRoute() {

        polylineOptions = new PolylineOptions();

        try {
            JSONArray array = new JSONArray(points);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                double lat = object.getDouble("latitude");
                double lang = object.getDouble("longitude");

                polylineOptions.add(new LatLng(lat, lang));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMap.addPolyline(polylineOptions).setColor(getResources().getColor(R.color.colorAccent));
    }


}
