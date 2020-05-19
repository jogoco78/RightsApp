package cat.uab.idt.rightsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.RVAWhatsNext;
import cat.uab.idt.rightsapp.models.WhatsNextModel;

public class WhatsNextActivity extends AppCompatActivity implements RVAWhatsNext.ItemClickListener {

    private ArrayList<WhatsNextModel> dataSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_next);
        setTitle(R.string.whats_next);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        // Sets the back button
        ImageButton ib_back_whats_next = findViewById(R.id.ib_back_whats_next);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Gets the tags
        int main_tag = sharedPreferences.getInt(Constants.MAIN_TAG, 0);
        int side_tag = sharedPreferences.getInt(Constants.SIDE_TAG, 0);
        int residence_tag = sharedPreferences.getInt(Constants.RESIDENCE_TAG, Constants.TAG_SPANISH_RESIDENT);

        //Set the data set
        dataSet = new ArrayList<>();

        //Sets the data set container
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_whats_next);

        //Common scenarios
        dataSet.add(new WhatsNextModel(0, getResources().getString(R.string.phone_112)));
        dataSet.add(new WhatsNextModel(1, getResources().getString(R.string.victims_association)));
        dataSet.add(new WhatsNextModel(2, getResources().getString(R.string.police_station)));

        if(main_tag == Constants.TAG_VIOLENCE_AGAINST_WOMEN){
            dataSet.add(new WhatsNextModel(3, getResources().getString(R.string.phone_against_violence)));
        }

        if(main_tag == Constants.TAG_SEXUAL_ATTACK || side_tag == Constants.TAG_SEXUAL_ATTACK){
            dataSet.add(new WhatsNextModel(4, getResources().getString(R.string.hospital)));
        }

        if(residence_tag != Constants.TAG_SPANISH_RESIDENT){
            dataSet.add(new WhatsNextModel(5, getResources().getString(R.string.consulate_embassy)));
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Specify an adapter
        RVAWhatsNext mAdapter = new RVAWhatsNext(this.getBaseContext(), dataSet);
        mAdapter.setClickListener(new RVAWhatsNext.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                switch (position){
                    case 0:
                        //Emergency call
                        intent = new Intent(getApplicationContext(), Emergency112CallActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case 1:
                        //Victims assistance office
                        intent = new Intent(getApplicationContext(), EntitiesListActivity.class);
                        intent.putExtra(Constants.SEARCH_ENTITY_CRITERIA, Integer.toString(2) +
                                "," + Integer.toString(0) +
                                "," + Integer.toString(0));
                        startActivity(intent);
                        break;
                    case 2:
                        //Police station
                        intent = new Intent(getApplicationContext(), EntitiesListActivity.class);
                        intent.putExtra(Constants.SEARCH_ENTITY_CRITERIA, Integer.toString(1) +
                                "," + Integer.toString(0) +
                                "," + Integer.toString(0));
                        startActivity(intent);
                        break;
                    case 3:
                        break;
                    case 4:
                        //Go to hospital
                        intent = new Intent(getApplicationContext(), EntitiesListActivity.class);
                        intent.putExtra(Constants.SEARCH_ENTITY_CRITERIA, Integer.toString(3) +
                                "," + Integer.toString(0) +
                                "," + Integer.toString(0));
                        startActivity(intent);
                        break;
                    case 5:
                        //Consulate or embassy
                        intent = new Intent(getApplicationContext(), EntitiesListActivity.class);
                        intent.putExtra(Constants.SEARCH_ENTITY_CRITERIA, Integer.toString(4) +
                                "," + Integer.toString(0) +
                                "," + Integer.toString(0));
                        startActivity(intent);
                        break;
                    default:
                        //Error
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        ib_back_whats_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position){

    }

    @Override
    public void onBackPressed(){
        finish();
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
