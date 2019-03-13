package cat.uab.idt.rightsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import cat.uab.idt.rightsapp.utils.GPSUtils;


public class NavigateActivity extends AppCompatActivity {

    private final static int REQUEST_PERMISSION_GET_COORDINATES = 101;
    private final static int REQUEST_PERMISSION_UPDATE_COORDINATES = 102;
    private Context context;
    private LocationManager locationManager;
    private String provider;
    private double longitude;
    private double latitude;
    private int coarseLocationPermission;
    private int fineLocationPermission;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private StringBuilder stringBuilder;
    private TextView tv_lat;
    private TextView tv_long;
    private Button btn_location;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        tv_lat = findViewById(R.id.tv_lat);
        tv_long = findViewById(R.id.tv_long);
        btn_location = findViewById(R.id.btn_location);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        new GPSUtils(this).turnGPSOn(new GPSUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnabled) {
                isGPS = isGPSEnabled;
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                System.out.println("CALLBACK");
                if (locationResult == null) {
                    System.out.println("NULL 1");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        System.out.println("GPS Latitude 1: " + latitude);
                        System.out.println("GPS Longitude 1: " + longitude);
                        if(!isContinue){
                            tv_lat.setText(String.valueOf(location.getLatitude()));
                            tv_long.setText(String.valueOf(location.getLongitude()));
                        }else{
                            stringBuilder.append(latitude);
                            stringBuilder.append("-");
                            stringBuilder.append(longitude);
                            stringBuilder.append("\n\n");
                            tv_lat.setText(stringBuilder.toString());
                        }
                        if(!isContinue && fusedLocationClient != null){
                            fusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }else{
                        System.out.println("Location is NULL in CALLBACK");
                    }
                }
            }
        };

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                isContinue = false;
                getLocation();
            }
        });

    }

    private void getLocation() {
        coarseLocationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        fineLocationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED && fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_GET_COORDINATES);

        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(NavigateActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                System.out.println("Success");

                                System.out.println("GPS: Longitude " + location.getLongitude());
                                tv_lat.setText(String.valueOf(location.getLatitude()));
                                tv_long.setText(String.valueOf(location.getLongitude()));
                                System.out.println("GPS: Latitude " + location.getLatitude());
                            } else {
                                System.out.println("NULL 2");
                                tv_lat.setText("Latitude not available");
                                tv_long.setText("Longitude not available");
                                try {
                                    System.out.println("NULL 3");
                                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                                } catch (SecurityException e) {
                                    System.out.println("NULL Exception");
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_GET_COORDINATES: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                    System.out.println("GRANTED");


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();

    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /*public void getLocation(){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        }else{
            Location location = locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
            if(location != null){
                double lati = location.getLatitude();
                double longi = location.getLongitude();
                System.out.println("TEST Location :" +lati+ " ,"+longi );
            } else {
                boolean isNetworkAvailable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if(isNetworkAvailable) {
                    System.out.println("TEST: NETWORK");
                    //fetchCurrentLocation();
                }
                else {
                    //Redirect user to device settings in order to turn on location service
                }
            }
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.settings_language:
                intent = new Intent(getApplicationContext(), LanguageActivity.class);
                startActivity(intent);
                break;
            case R.id.action_home:
                intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_explanation:
                intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_terms_conditions:
                //do things
                intent = new Intent(getApplicationContext(), TermsAndConditions.class);
                startActivity(intent);
                break;
            case R.id.settings_about_us:
                //do things
                intent = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(intent);
                break;
            default:
                //do default
        }

        /*if (id == R.id.action_favorite) {
            Toast.makeText(RightsAppActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


}
