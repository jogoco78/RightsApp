package cat.uab.idt.rightsapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Emergency112CallActivity extends AppCompatActivity {

    private final static int REQUEST_PERMISSION_PHONE_CALL = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_call112);
        setContentView(R.layout.activity_emergency112_call);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        ImageButton ib_call112 = findViewById(R.id.ib_call_112);
        ib_call112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int phonePermission = ActivityCompat.checkSelfPermission(Emergency112CallActivity.this, Manifest.permission.CALL_PHONE);

                if (phonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency112CallActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_PERMISSION_PHONE_CALL);
                }else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Constants.PHONE_EMERGENCIES));
                    startActivity(callIntent);
                }
            }
        });

        Button bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                startActivity(intent);
            }
        });

        Button bt_call = findViewById(R.id.bt_call112);
        bt_call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int phonePermission = ActivityCompat.checkSelfPermission(Emergency112CallActivity.this, Manifest.permission.CALL_PHONE);

                if (phonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency112CallActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_PERMISSION_PHONE_CALL);
                }else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Constants.PHONE_EMERGENCIES));
                    startActivity(callIntent);
                }
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
                    callIntent.setData(Uri.parse("tel:" + Constants.PHONE_EMERGENCIES));
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
