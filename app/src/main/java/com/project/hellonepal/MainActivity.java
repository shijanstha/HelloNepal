
package com.project.hellonepal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    // The ImageView of the Logos
    private ImageView coffeeLogo;
    private ImageView travelLogo;
    private ImageView foodLogo;
    private ImageView drinkLogo;
    private ImageView artLogo;
    private ImageView shopLogo;
    public static int selector;

    // The app-specific constant when requesting the location permission
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attaches a listener for animation/transition to the logos

        travelLogo = (ImageView) findViewById(R.id.travel);
        travelLogo.setOnClickListener(MainActivity.this);

        coffeeLogo = (ImageView) findViewById(R.id.coffee);
        coffeeLogo.setOnClickListener(this);

        foodLogo = (ImageView) findViewById(R.id.food);
        foodLogo.setOnClickListener(this);

        drinkLogo = (ImageView) findViewById(R.id.drink);
        drinkLogo.setOnClickListener(this);

        shopLogo = (ImageView) findViewById(R.id.shop);
        shopLogo.setOnClickListener(this);

        artLogo = (ImageView) findViewById(R.id.art);
        artLogo.setOnClickListener(this);


        // Requests for location permissions at runtime (required for API >= 23)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Adds animation to the Logos
//        RotateAnimation jitter = new RotateAnimation(0, 2, 50, 50);
//        jitter.setDuration(10);
//        jitter.setRepeatCount(Animation.INFINITE);
//        jitter.setRepeatMode(Animation.REVERSE);
//        coffeeLogo.startAnimation(jitter);
//        travelLogo.startAnimation(jitter);
//        foodLogo.startAnimation(jitter);
//        artLogo.startAnimation(jitter);
//        drinkLogo.startAnimation(jitter);
//        shopLogo.startAnimation(jitter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food:
                Animation click = AnimationUtils.loadAnimation(this, R.anim.click);

                // Defines a listener to transition to the PlacePickerActivity after the animation completes
                click.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            selector = 1;
                            Intent i = new Intent(getApplicationContext(), PlacePickerActivity.class);
                            startActivity(i);

                        } else {
                            // Notifies the user if there are insufficient location permissions
                            Toast.makeText(getApplicationContext(), "App is missing permissions to access your location!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Attaches the alpha/scale animation to the view
                view.startAnimation(click);
                break;
            case R.id.coffee:
                Animation click2 = AnimationUtils.loadAnimation(this, R.anim.click);

                // Defines a listener to transition to the PlacePickerActivity after the animation completes
                click2.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            selector = 2;
                            Intent i = new Intent(getApplicationContext(), PlacePickerActivity.class);
                            startActivity(i);

                        } else {
                            // Notifies the user if there are insufficient location permissions
                            Toast.makeText(getApplicationContext(), "App is missing permissions to access your location!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Attaches the alpha/scale animation to the view
                view.startAnimation(click2);
                break;
            case R.id.travel:
                Animation click3 = AnimationUtils.loadAnimation(this, R.anim.click);

                // Defines a listener to transition to the PlacePickerActivity after the animation completes
                click3.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            selector = 3;
                            Intent i = new Intent(getApplicationContext(), PlacePickerActivity.class);
                            startActivity(i);

                        } else {
                            // Notifies the user if there are insufficient location permissions
                            Toast.makeText(getApplicationContext(), "App is missing permissions to access your location!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Attaches the alpha/scale animation to the view
                view.startAnimation(click3);
                break;
            case R.id.drink:
                Animation click4 = AnimationUtils.loadAnimation(this, R.anim.click);

                // Defines a listener to transition to the PlacePickerActivity after the animation completes
                click4.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            selector = 4;
                            Intent i = new Intent(getApplicationContext(), PlacePickerActivity.class);
                            startActivity(i);

                        } else {
                            // Notifies the user if there are insufficient location permissions
                            Toast.makeText(getApplicationContext(), "App is missing permissions to access your location!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Attaches the alpha/scale animation to the view
                view.startAnimation(click4);
                break;
            case R.id.art:
                Animation click5 = AnimationUtils.loadAnimation(this, R.anim.click);

                // Defines a listener to transition to the PlacePickerActivity after the animation completes
                click5.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            selector = 4;
                            Intent i = new Intent(getApplicationContext(), PlacePickerActivity.class);
                            startActivity(i);

                        } else {
                            // Notifies the user if there are insufficient location permissions
                            Toast.makeText(getApplicationContext(), "App is missing permissions to access your location!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Attaches the alpha/scale animation to the view
                view.startAnimation(click5);
                break;
        }

    }
}
