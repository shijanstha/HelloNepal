
package com.project.hellonepal;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
PlacePickerActivity extends Activity

        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // The client object for connecting to the Google API
    private GoogleApiClient mGoogleApiClient;


    // The TextView for displaying the current location
    private TextView snapToPlace;

    // The RecyclerView and associated objects for displaying the nearby coffee spots
    private RecyclerView placePicker;
    private LinearLayoutManager placePickerManager;
    private RecyclerView.Adapter placePickerAdapter;

    // The base URL for the Foursquare API
    private String foursquareBaseURL = "https://api.foursquare.com/v2/";

    // The client ID and client secret for authenticating with the Foursquare API
    private String foursquareClientID;
    private String foursquareClientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // The visible TextView and RecyclerView objects
        snapToPlace = (TextView) findViewById(R.id.snapToPlace);
        placePicker = (RecyclerView) findViewById(R.id.coffeeList);

        // Sets the dimensions, LayoutManager, and dividers for the RecyclerView
        placePicker.setHasFixedSize(true);
        placePickerManager = new LinearLayoutManager(this);
        placePicker.setLayoutManager(placePickerManager);
        placePicker.addItemDecoration(new DividerItemDecoration(placePicker.getContext(), placePickerManager.getOrientation()));

        // Creates a connection to the Google API for location services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Gets the stored Foursquare API client ID and client secret from XML
        foursquareClientID = getResources().getString(R.string.foursquare_client_id);
        foursquareClientSecret = getResources().getString(R.string.foursquare_client_secret);
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
    public void onConnected(Bundle connectionHint) {

        // Checks for location permissions at runtime (required for API >= 23)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Makes a Google API request for the user's last known location
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {

                // The user's current latitude, longitude, and location accuracy
                String userLL = mLastLocation.getLatitude() + "," + mLastLocation.getLongitude();
                double userLLAcc = mLastLocation.getAccuracy();

                // Builds Retrofit and FoursquareService objects for calling the Foursquare API and parsing with GSON
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(foursquareBaseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                FoursquareService foursquare = retrofit.create(FoursquareService.class);

                // Calls the Foursquare API to snap the user's location to a Foursquare venue
                Call<FoursquareJSON> stpCall = foursquare.snapToPlace(
                        foursquareClientID,
                        foursquareClientSecret,
                        userLL,
                        userLLAcc);
                stpCall.enqueue(new Callback<FoursquareJSON>() {
                    @Override
                    public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                        // Gets the venue object from the JSON response
                        FoursquareJSON fJson = response.body();
                        FoursquareResponse fr = fJson.response;
                        List<FoursquareVenue> frs = fr.venues;
                        FoursquareVenue fv = frs.get(0);

                        // Notifies the user of their current location
                        if(MainActivity.selector==1) {
                            snapToPlace.setText("You're at " + fv.name + ". Here's some resturants nearby.");
                        }
                        if(MainActivity.selector==2) {
                            snapToPlace.setText("You're at " + fv.name + ". Here's some cafe nearby.");
                        }
                        if(MainActivity.selector==3) {
                            snapToPlace.setText("You're at " + fv.name + ". Here's some places to see nearby.");
                        }
                        if(MainActivity.selector==4) {
                            snapToPlace.setText("You're at " + fv.name + ". Here's some place to have a drink nearby.");
                        }
                        if(MainActivity.selector==5) {
                            snapToPlace.setText("You're at " + fv.name + ". Here's some cafe nearby.");
                        }
                    }

                    @Override
                    public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "App can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                // Calls the Foursquare API to explore nearby coffee spots
//  For Coffee              Call<FoursquareJSON> coffeeCall = foursquare.searchCoffee(
//                        foursquareClientID,
//                        foursquareClientSecret,
//                        userLL,
//                        userLLAcc);
//                coffeeCall.enqueue(new Callback<FoursquareJSON>() {

//  For Food             Call<FoursquareJSON> foodCall = foursquare.searchFood(
//                        foursquareClientID,
//                        foursquareClientSecret,
//                        userLL,
//                        userLLAcc);
//                foodCall.enqueue(new Callback<FoursquareJSON>() {
                if(MainActivity.selector==1) {
                    Call<FoursquareJSON> placesCall = foursquare.searchFood(
                            foursquareClientID,
                            foursquareClientSecret,
                            userLL,
                            userLLAcc);

                    placesCall.enqueue(new Callback<FoursquareJSON>() {

                        @Override
                        public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                            // Gets the venue object from the JSON response
                            FoursquareJSON fJson = response.body();
                            FoursquareResponse fr = fJson.response;
                            FoursquareGroup fg = fr.group;
                            List<FoursquareResults> frs = fg.results;

                            // Displays the results in the RecyclerView
                            placePickerAdapter = new PlacePickerAdapter(getApplicationContext(), frs);
                            placePicker.setAdapter(placePickerAdapter);
                        }

                        @Override
                        public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "App can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                if(MainActivity.selector==2) {
                    Call<FoursquareJSON> placesCall = foursquare.searchCoffee(
                            foursquareClientID,
                            foursquareClientSecret,
                            userLL,
                            userLLAcc);

                    placesCall.enqueue(new Callback<FoursquareJSON>() {

                        @Override
                        public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                            // Gets the venue object from the JSON response
                            FoursquareJSON fJson = response.body();
                            FoursquareResponse fr = fJson.response;
                            FoursquareGroup fg = fr.group;
                            List<FoursquareResults> frs = fg.results;

                            // Displays the results in the RecyclerView
                            placePickerAdapter = new PlacePickerAdapter(getApplicationContext(), frs);
                            placePicker.setAdapter(placePickerAdapter);
                        }

                        @Override
                        public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "App can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                if(MainActivity.selector==3) {
                    Call<FoursquareJSON> placesCall = foursquare.sights(
                            foursquareClientID,
                            foursquareClientSecret,
                            userLL,
                            userLLAcc);

                    placesCall.enqueue(new Callback<FoursquareJSON>() {

                        @Override
                        public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                            // Gets the venue object from the JSON response
                            FoursquareJSON fJson = response.body();
                            FoursquareResponse fr = fJson.response;
                            FoursquareGroup fg = fr.group;
                            List<FoursquareResults> frs = fg.results;

                            // Displays the results in the RecyclerView
                            placePickerAdapter = new PlacePickerAdapter(getApplicationContext(), frs);
                            placePicker.setAdapter(placePickerAdapter);
                        }

                        @Override
                        public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "App can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                if(MainActivity.selector==4) {
                    Call<FoursquareJSON> placesCall = foursquare.drinks(
                            foursquareClientID,
                            foursquareClientSecret,
                            userLL,
                            userLLAcc);

                    placesCall.enqueue(new Callback<FoursquareJSON>() {

                        @Override
                        public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                            // Gets the venue object from the JSON response
                            FoursquareJSON fJson = response.body();
                            FoursquareResponse fr = fJson.response;
                            FoursquareGroup fg = fr.group;
                            List<FoursquareResults> frs = fg.results;

                            // Displays the results in the RecyclerView
                            placePickerAdapter = new PlacePickerAdapter(getApplicationContext(), frs);
                            placePicker.setAdapter(placePickerAdapter);
                        }

                        @Override
                        public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "App can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                if(MainActivity.selector==5) {
                    Call<FoursquareJSON> placesCall = foursquare.arts(
                            foursquareClientID,
                            foursquareClientSecret,
                            userLL,
                            userLLAcc);

                    placesCall.enqueue(new Callback<FoursquareJSON>() {

                        @Override
                        public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                            // Gets the venue object from the JSON response
                            FoursquareJSON fJson = response.body();
                            FoursquareResponse fr = fJson.response;
                            FoursquareGroup fg = fr.group;
                            List<FoursquareResults> frs = fg.results;

                            // Displays the results in the RecyclerView
                            placePickerAdapter = new PlacePickerAdapter(getApplicationContext(), frs);
                            placePicker.setAdapter(placePickerAdapter);
                        }

                        @Override
                        public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "App can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                }

            } else {
                Toast.makeText(getApplicationContext(), "App can't determine your current location!", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    @Override
    protected void onResume() {
        super.onResume();

        // Reconnects to the Google API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Disconnects from the Google API
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "App can't connect to Google's servers!", Toast.LENGTH_LONG).show();
        finish();
    }
}