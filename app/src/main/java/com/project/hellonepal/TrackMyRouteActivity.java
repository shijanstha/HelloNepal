package com.project.hellonepal;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrackMyRouteActivity extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;

    double currentLat = 0, currentLong = 0;

    org.json.JSONArray array;
    ArrayList<LatLng> pointList;
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;// meters  (make the time and distance to 0,0 so that we could get the updates very quickly
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 3;//1000 * 60 * 1; //  minute
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; //latitude

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_my_route);
        databaseHelper = new DatabaseHelper(this);
        pointList = new ArrayList<LatLng>();

        findViewById(R.id.track).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointList = new ArrayList<LatLng>();
            }
        });

        findViewById(R.id.stoptrack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRouteInputDialog();
            }
        });

        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getApplicationContext(), "Either Network or GPS is not available in your device.", Toast.LENGTH_LONG).show();
                // no network provider is enabled
            } else {
                this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    //Update the location of a user after t time and d distance... where we have declared time and distance as 0,0 which means to get frequent updates.
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);


                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            //Toast.makeText(getApplicationContext(), "Location "+location.getLatitude() , Toast.LENGTH_LONG).show();
                        }
                    }
                }

				/*else if (isNetworkEnabled)
                {	//Toast.makeText(getApplicationContext(), "inside 'isGpdenabled' " , Toast.LENGTH_LONG).show();
					if (location == null)
					{
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
				//*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "lat:" + location.getLatitude() + " long:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
        if (distance_in_meter(currentLat, currentLong, location.getLatitude(), location.getLongitude()) > 10) {
            currentLat = location.getLatitude();
            currentLong = location.getLongitude();
            pointList.add(new LatLng(location.getLatitude(), location.getLongitude()));

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void showRouteInputDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Enter route Name");
        View view = LayoutInflater.from(this).inflate(R.layout.route_dialog_layout, null);
        final EditText routeName = (EditText) view.findViewById(R.id.routeName);

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouteInfo(routeName.getText().toString());
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.show();


    }


    public void getRouteInfo(String routename) {
        array = new JSONArray();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng latLng = pointList.get(i);
            try {
                JSONObject object = new JSONObject();
                object.put("latitude", latLng.latitude);
                object.put("longitude", latLng.longitude);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ContentValues cv = new ContentValues();
        cv.put("name", routename);
        cv.put("points", array.toString());
        databaseHelper.insertRoute(cv);
        Toast.makeText(this, "Route Inserted", Toast.LENGTH_SHORT).show();

    }

    private static double distance_in_meter(final double lat1, final double lon1, final double lat2, final double lon2) {
        double R = 6371000f; // Radius of the earth in m
        double dLat = (lat1 - lat2) * Math.PI / 180f;
        double dLon = (lon1 - lon2) * Math.PI / 180f;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1 * Math.PI / 180f) * Math.cos(lat2 * Math.PI / 180f) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

}
