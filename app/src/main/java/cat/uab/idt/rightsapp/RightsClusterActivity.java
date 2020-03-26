package cat.uab.idt.rightsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.MyRecyclerViewAdapterGroups;
import cat.uab.idt.rightsapp.models.TagModel;

public class RightsClusterActivity extends AppCompatActivity implements MyRecyclerViewAdapterGroups.ItemClickListener{

    private ArrayList<TagModel> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_activity_subjects);
        setContentView(R.layout.activity_rightsapp_cluster);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_particles_groups);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //TODO: Alert dialog once selected the user could come back by pressing back button
        //Gets if it is the first run in the current launch
        //boolean isFirstRunBackQuestionnaire = sharedPreferences.getBoolean(Constants.FIRST_RUN_BACK_QUESTIONNAIRE, true);

       /* if(isFirstRunBackQuestionnaire){
            AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setMessage(R.string.no_answer);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button - Do nothing

                }
            });
            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.FIRST_RUN_BACK_QUESTIONNAIRE, false);
            editor.apply();
        }*/

        //Gets current questions, answers and tags parameters
        String par_tag = sharedPreferences.getString(Constants.PAR_TAGS, null);

        dataSet = new ArrayList<>();
        dataSet.add(new TagModel(Constants.TAG_COMMON_CRIME, getResources().getString(R.string.citizens_rights)));

        if (par_tag.contains(String.valueOf(Constants.TAG_SEXUAL_ATTACK))){
            //Sexual attack tag is activated
            dataSet.add(new TagModel(Constants.TAG_SEXUAL_ATTACK, getResources().getString(R.string.sexual_attack_protocol)));
        }
        if (par_tag.contains(String.valueOf(Constants.TAG_UE_RESIDENT))){
            //EU residents tag activated
            dataSet.add(new TagModel(Constants.TAG_UE_RESIDENT, getResources().getString(R.string.EU_citizens_rights)));
        } else if(par_tag.contains(String.valueOf(Constants.TAG_NON_EU_RESIDENT))){
            //non-EU residents activated
            dataSet.add(new TagModel(Constants.TAG_NON_EU_RESIDENT, getResources().getString(R.string.no_EU_citizens_rights)));
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // specify an adapter
        MyRecyclerViewAdapterGroups mAdapter = new MyRecyclerViewAdapterGroups(this.getBaseContext(), dataSet);
        mAdapter.setClickListener(new MyRecyclerViewAdapterGroups.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ParticlesActivity.class);
                intent.putExtra("group", dataSet.get(position).getId_tag());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.back_questionnaire);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent intent = new Intent(getApplicationContext(), QuestionnaireActivity.class);
                startActivity(intent);
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
    public void onItemClick(View view, int position){
        Intent intent = new Intent(getApplicationContext(), ParticlesActivity.class);
        intent.putExtra("group", dataSet.get(position).getId_tag());
        startActivity(intent);
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