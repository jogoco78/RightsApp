package cat.uab.idt.rightsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EntityActivity extends AppCompatActivity {

    private final static int REQUEST_PERMISSION_PHONE_CALL = 102;
    private Context context;
    private String phone_number;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_entity);
        setContentView(R.layout.activity_entity);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        context = getApplicationContext();

        //Gets the entity information from the previous activity
        String name = getIntent().getStringExtra(Constants.ENTITY_NAME);
        String description = getIntent().getStringExtra(Constants.ENTITY_DESCRIPTION);
        String address = getIntent().getStringExtra(Constants.ENTITY_ADDRESS);
        phone_number = getIntent().getStringExtra(Constants.ENTITY_PHONE);
        String position = getIntent().getStringExtra(Constants.ENTITY_POSITION);
        String link = getIntent().getStringExtra(Constants.ENTITY_LINK);

        longitude = Double.parseDouble(position.split(",")[0]);
        latitude = Double.parseDouble(position.split(",")[1]);

        TextView tv_entity_name = findViewById(R.id.tv_name);
        TextView tv_entity_description = findViewById(R.id.tv_description);
        TextView tv_entity_address = findViewById(R.id.tv_address);
        TextView tv_entity_phone = findViewById(R.id.tv_entity_phone);
        TextView tv_entity_link = findViewById(R.id.tv_entity_link);
        Button btn_call = findViewById(R.id.btn_call);
        Button btn_navigate = findViewById(R.id.btn_navigate);
        Button btn_back = findViewById(R.id.btn_entity_back);

        tv_entity_name.setText(name);
        tv_entity_description.setText(description);
        tv_entity_address.setText(address);
        tv_entity_phone.setText(phone_number);
        tv_entity_link.setText(link);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int phonePermission = ActivityCompat.checkSelfPermission(EntityActivity.this, Manifest.permission.CALL_PHONE);

                if (phonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EntityActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_PERMISSION_PHONE_CALL);
                }else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone_number));
                    startActivity(callIntent);
                }
            }
        });

        btn_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Create a Uri from an intent string. Use the result to create an Intent
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + longitude + "," + latitude);

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_CALL: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone_number));
                    startActivity(callIntent);

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
