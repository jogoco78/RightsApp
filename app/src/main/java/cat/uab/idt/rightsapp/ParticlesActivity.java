package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.ExpandableAdapter;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class ParticlesActivity extends AppCompatActivity{

    private DataBaseHelper db = null;
    private ArrayList<ParticleModel> particles_list = null;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_subjects);
        setContentView(R.layout.activity_particles);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        //Gets elements from layout
        expandableListView = findViewById(R.id.elv_particles);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Gets current questions, answers and tags parameters
        String par_questionID = sharedPreferences.getString(Constants.PAR_QUESTIONS, null);
        String par_answersID = sharedPreferences.getString(Constants.PAR_ANSWERS, null);
        String par_tag = sharedPreferences.getString(Constants.PAR_TAGS, null);

        //Gets tags in string and int arrays
        String[] tags_s;
        int[] tags_i = null;
        if(par_tag != null) {
            tags_s = par_tag.split(",");
            tags_i = new int[tags_s.length];
            for(int i = 0; i < tags_s.length; i++){
                tags_i[i] = Integer.parseInt(tags_s[i]);
            }
        }

        //Opens database
        if(db == null){
            db = new DataBaseHelper(this);
            db.openDataBase();
        }

        //Gets particles list
        particles_list = db.getParticlesByTag(tags_i, language);

        //Gets subject list in string array
        /*int[] id_subjects = new int[particles_list.size()];
        int index = 0;
        for(ParticleModel par : particles_list){
            id_subjects[index] = par.getId_subject();
            //System.out.println("Particle: " + par.getId());
            //System.out.println("Subject: " + par.getId_subject());
            index++;
        }*/

        String[] subjects = new String[particles_list.size()];
        for(int i = 0; i < subjects.length; i++) {
            subjects[i] = particles_list.get(i).getSubjectText();
        }
        //subjects = db.getSubjectByID(id_subjects, language);

        ArrayList<ArrayList<String>> childList = new ArrayList<>();
        for(int i = 0; i < subjects.length; i++){
            childList.add(particles_list.get(i).getTextArray());
        }

        adapter = new ExpandableAdapter(this, childList, subjects);
        expandableListView.setAdapter(adapter);
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
