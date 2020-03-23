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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_subjects);
        setContentView(R.layout.activity_particles);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

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

        if(par_tag.contains("2")){
            //Terrorism
            particlesMainTags.add(2);
        }else if(par_tag.contains("3")){
            //Violence against women
            particlesMainTags.add(3);
        }else if(par_tag.contains("4")){
            //Domestic violence
            particlesMainTags.add(4);
        }else if(par_tag.contains("5)")){
            //Violent crime
            particlesMainTags.add(5);
        }else if(par_tag.contains("1")){
            //Common crime
            particlesMainTags.add(1);
        }

        switch(id_tag_user){
            case 1:
                particlesResidenceTags.add(9);
                break;
            case 6:
                //Sexual attack
                particlesMainTags.add(6);
                break;
            case 7:
                //UE Residents
                particlesResidenceTags.add(7);
                break;
            case 8:
                //Non UE residents
                particlesResidenceTags.add(8);
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
