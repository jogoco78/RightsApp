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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class NavigateActivity extends AppCompatActivity implements LocationListener {

    private final static int REQUEST_PERMISSION_GET_COORDINATES = 101;
    private final static int REQUEST_PERMISSION_UPDATE_COORDINATES = 102;
    private Context context;
    private LocationManager locationManager;
    private String provider;
    private double longitude;
    private double latitude;
    private int coarseLocationPermission;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        final TextView tv_lat = findViewById(R.id.tv_lat);
        final TextView tv_long = findViewById(R.id.tv_long);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        coarseLocationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if(coarseLocationPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION_GET_COORDINATES);
        }else {
            System.out.println("Success");
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
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
                            }else{
                                System.out.println("NULL");
                            }
                        }
                    });
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        System.out.println("GPS: Longitude " + longitude);
        System.out.println("GPS: Latitude " + latitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
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
