package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.ExpandableAdapter;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class ParticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_subjects);
        setContentView(R.layout.activity_particles);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        ImageButton ib_particles_back = findViewById(R.id.ib_particles);

        ib_particles_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int id_tag_user = 0;
        //Main tags
        ArrayList<Integer> particlesMainTags = new ArrayList<>();
        //Residence tags
        ArrayList<Integer> particlesResidenceTags = new ArrayList<>();

        //Database descriptor
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDataBase();

        //Gets elements from layout
        ExpandableListView expandableListView = findViewById(R.id.elv_particles);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id_tag_user = extras.getInt("group");
        }

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Gets tags
        String par_tag = sharedPreferences.getString(Constants.PAR_TAGS, null);

        if(id_tag_user == Constants.TAG_SEXUAL_ATTACK){
            particlesMainTags.add(Constants.TAG_SEXUAL_ATTACK);
        } else if(par_tag.contains(String.valueOf(Constants.TAG_TERRORISM))){
            //Terrorism
            particlesMainTags.add(Constants.TAG_TERRORISM);
        }else if(par_tag.contains(String.valueOf(Constants.TAG_VIOLENCE_AGAINST_WOMEN))){
            //Violence against women
            particlesMainTags.add(Constants.TAG_VIOLENCE_AGAINST_WOMEN);
        }else if(par_tag.contains(String.valueOf(Constants.TAG_DOMESTIC_VIOLENCE))){
            //Domestic violence
            particlesMainTags.add(Constants.TAG_DOMESTIC_VIOLENCE);
            if(par_tag.contains(String.valueOf(Constants.TAG_VIOLENT_CRIME))) {
                //In addition to domestic violence tag, Violent crime could arise
                particlesMainTags.add(Constants.TAG_VIOLENT_CRIME);
            }
        }else if(par_tag.contains(String.valueOf(Constants.TAG_VIOLENT_CRIME))){
            //Violent crime
            particlesMainTags.add(Constants.TAG_VIOLENT_CRIME);
        }else if(par_tag.contains(String.valueOf(Constants.TAG_COMMON_CRIME))){
            //Common crime
            particlesMainTags.add(Constants.TAG_COMMON_CRIME);
        }

        switch(id_tag_user){
            case Constants.TAG_COMMON_CRIME:
            case Constants.TAG_TERRORISM:
            case Constants.TAG_VIOLENCE_AGAINST_WOMEN:
            case Constants.TAG_DOMESTIC_VIOLENCE:
            case Constants.TAG_VIOLENT_CRIME:
            case Constants.TAG_SEXUAL_ATTACK:
                particlesResidenceTags.add(Constants.TAG_SPANISH_RESIDENT);
                break;
            case Constants.TAG_UE_RESIDENT:
                //UE Residents
                particlesResidenceTags.add(Constants.TAG_UE_RESIDENT);
                break;
            case Constants.TAG_NON_EU_RESIDENT:
                //Non UE residents
                particlesResidenceTags.add(Constants.TAG_NON_EU_RESIDENT);
                break;
            default:
                //Error
        }

        //Gets particles list
        ArrayList<ParticleModel> particles_list = db.getParticlesByTag(particlesMainTags, particlesResidenceTags, language);

        String[] subjects = new String[particles_list.size()];
        for(int i = 0; i < subjects.length; i++) {
            subjects[i] = particles_list.get(i).getSubjectText();
        }

        ArrayList<ArrayList<String>> childList = new ArrayList<>();
        for(int i = 0; i < subjects.length; i++){
            childList.add(particles_list.get(i).getTextArray());
        }

        ExpandableListAdapter adapter = new ExpandableAdapter(this, childList, subjects);
        expandableListView.setAdapter(adapter);

        //Closes the database
        db.close();
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
