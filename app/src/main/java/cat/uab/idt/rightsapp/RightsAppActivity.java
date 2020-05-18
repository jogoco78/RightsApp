package cat.uab.idt.rightsapp;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;


public class RightsAppActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_activity_rights_app);
        setContentView(R.layout.activity_rights_app);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //TextView Listeners
        TextView tv_call112 = findViewById(R.id.tv_112);
        tv_call112.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //START Analytics
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "112");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Call112");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mainscreen");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                //END Analytics

                Intent intent = new Intent(getApplicationContext(), Emergency112CallActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_information = findViewById(R.id.tv_questionnaire);
        tv_information.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionnaireActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_navigate = findViewById(R.id.tv_navigate);
        tv_navigate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), EntitySearchActivity.class);
                startActivity(intent);
            }
        });

        //ImageButton Listeners
        ImageButton button_emergency112 = findViewById(R.id.ib_112);
        button_emergency112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "112Button");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Call112Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mainscreen");

                Intent intent = new Intent(getApplicationContext(), Emergency112CallActivity.class);
                startActivity(intent);
            }
        });

        ImageButton button_information = findViewById(R.id.ib_information);
        button_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), QuestionnaireActivity.class);
                startActivity(intent);
            }
        });

        ImageButton button_navigate = findViewById(R.id.ib_navigate);
        button_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), EntitySearchActivity.class);
                startActivity(intent);
            }
        });

        //START Analytics
        mFirebaseAnalytics.setCurrentScreen(this, "112Screen", null /* class override */);
        //END Analytics
    }

    @Override
    public void onResume(){
        super.onResume();
        mFirebaseAnalytics.setCurrentScreen(this, "112Screen", null /* class override */);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit_app);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked Cancel button - Do nothing
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
                builder.setMessage(R.string.qh_rightsapp_activity);

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
