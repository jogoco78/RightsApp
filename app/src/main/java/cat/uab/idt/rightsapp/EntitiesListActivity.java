package cat.uab.idt.rightsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.RecyclerViewAdapter;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.EntityModel;


public class EntitiesListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    private String language;
    private final static int REQUEST_PERMISSION_GET_COORDINATES = 101;
    private LocationManager locationManager;
    private double longitude;
    private double latitude;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter rv_adapter;
    private RecyclerView.LayoutManager rv_layoutManager;
    Activity activity = this;
    ArrayList<EntityModel> entities_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_entities_list);
        setContentView(R.layout.activity_entities_list);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        //Get elements from layout
        recyclerView = findViewById(R.id.rv_entities);
        Button btn_entities_list_back = findViewById(R.id.btn_entities_list_back);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Gets the search criteria stored in intent
        String[] criteria = getIntent().getStringExtra(Constants.SEARCH_ENTITY_CRITERIA).split(",");

        //Opens DB
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.openDataBase();

        //Populates the arraylist for recycleview
        entities_list = new ArrayList<>();

        entities_list = dataBaseHelper.getEntitiesList(
                new int[] {Integer.parseInt(criteria[0])},
                new int[] {Integer.parseInt(criteria[1])},
                new int[] {Integer.parseInt(criteria[2])},
                language);

        //Sets the distance to every selected entity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        /*float[] results = new float[1];
        for (int i = 0; i < entities_list.size(); i++){
            Location.distanceBetween(longitude, latitude, entities_list.get(i).getLongitude(), entities_list.get(i).getLatitude(),results);
            entities_list.get(i).setDistance(Math.round(results[0] / 10.0) / 100.0); //meters to kms and rounded two decimals
        }

        //Sort the entities list by distance
        ArrayList<EntityModel> entities_list_sorted = new ArrayList<>();
        int size = entities_list.size();
        for(int i = 0; i < size; i++){
            double min = Double.MAX_VALUE;
            int s = 0;
            for(int j = 0; j < entities_list.size(); j++){
                if(entities_list.get(j).getDistance() < min){
                    s = j;
                    min = entities_list.get(j).getDistance();
                }
            }
            entities_list_sorted.add(entities_list.get(s));
            entities_list.remove(s);
        }
        entities_list = null;

        //Set up the recycler view
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rv_adapter = new RecyclerViewAdapter(this, entities_list_sorted);
        rv_adapter.setClickListener(this);
        recyclerView.setAdapter(rv_adapter);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);*/

        btn_entities_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position){
        //Toast.makeText(this, "You clicked " + rv_adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), EntityActivity.class);
        intent.putExtra(Constants.ENTITY_NAME, rv_adapter.getItem(position).getEntity_name());
        intent.putExtra(Constants.ENTITY_DESCRIPTION, rv_adapter.getItem(position).getEntity_description());
        intent.putExtra(Constants.ENTITY_ADDRESS, rv_adapter.getItem(position).getAddress());
        intent.putExtra(Constants.ENTITY_PHONE, rv_adapter.getItem(position).getPhone_number());
        intent.putExtra(Constants.ENTITY_POSITION, rv_adapter.getItem(position).getLongitude() + "," + rv_adapter.getItem(position).getLatitude());
        intent.putExtra(Constants.ENTITY_LINK, rv_adapter.getItem(position).getLink());
        startActivity(intent);
    }

    private void getLocation() {
        int coarseLocationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED && fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_GET_COORDINATES);

        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();

                                float[] results = new float[1];
                                for (int i = 0; i < entities_list.size(); i++){
                                    Location.distanceBetween(longitude, latitude, entities_list.get(i).getLongitude(), entities_list.get(i).getLatitude(),results);
                                    entities_list.get(i).setDistance(Math.round(results[0] / 10.0) / 100.0); //meters to kms and rounded two decimals
                                }

                                //Sort the entities list by distance
                                ArrayList<EntityModel> entities_list_sorted = new ArrayList<>();
                                int size = entities_list.size();
                                for(int i = 0; i < size; i++){
                                    double min = Double.MAX_VALUE;
                                    int s = 0;
                                    for(int j = 0; j < entities_list.size(); j++){
                                        if(entities_list.get(j).getDistance() < min){
                                            s = j;
                                            min = entities_list.get(j).getDistance();
                                        }
                                    }
                                    entities_list_sorted.add(entities_list.get(s));
                                    entities_list.remove(s);
                                }
                                entities_list = null;

                                //Set up the recycler view
                                recyclerView.setHasFixedSize(true);

                                recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));
                                rv_adapter = new RecyclerViewAdapter(activity.getBaseContext(), entities_list_sorted);
                                rv_adapter.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        //Toast.makeText(this, "You clicked " + rv_adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), EntityActivity.class);
                                        intent.putExtra(Constants.ENTITY_NAME, rv_adapter.getItem(position).getEntity_name());
                                        intent.putExtra(Constants.ENTITY_DESCRIPTION, rv_adapter.getItem(position).getEntity_description());
                                        intent.putExtra(Constants.ENTITY_ADDRESS, rv_adapter.getItem(position).getAddress());
                                        intent.putExtra(Constants.ENTITY_PHONE, rv_adapter.getItem(position).getPhone_number());
                                        intent.putExtra(Constants.ENTITY_POSITION, rv_adapter.getItem(position).getLongitude() + "," + rv_adapter.getItem(position).getLatitude());
                                        intent.putExtra(Constants.ENTITY_LINK, rv_adapter.getItem(position).getLink());
                                        startActivity(intent);
                                    }
                                });
                                recyclerView.setAdapter(rv_adapter);

                                RecyclerView.ItemDecoration divider = new DividerItemDecoration(activity.getBaseContext(), DividerItemDecoration.VERTICAL);
                                recyclerView.addItemDecoration(divider);

                                //tv_lat.setText(String.valueOf(location.getLatitude()));
                                //tv_long.setText(String.valueOf(location.getLongitude()));
                            } else {
                                //tv_lat.setText("Latitude not available");
                                //tv_long.setText("Longitude not available");
                            }
                        }


                    });
        }
    }

    //@Override
/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_GET_COORDINATES: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                    try {
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(EntitiesListActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // Logic to handle location object
                                            longitude = location.getLongitude();
                                            latitude = location.getLatitude();

                                            //tv_lat.setText(String.valueOf(location.getLatitude()));
                                            //tv_long.setText(String.valueOf(location.getLongitude()));
                                        } else {
                                            //tv_lat.setText("Latitude not available");
                                            //tv_long.setText("Longitude not available");
                                        }
                                    }
                                });
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
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
            case R.id.action_help:
                //TODO: Set the action help text
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.help);

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button -- Do nothing

                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
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
