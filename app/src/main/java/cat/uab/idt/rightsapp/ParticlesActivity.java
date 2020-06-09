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
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.ExpandableAdapter;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class ParticlesActivity extends AppCompatActivity {

    private int id_tag_selected = 0;
    private int id_tag_cluster_selected = 0;
    private int main_tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_subjects);
        setContentView(R.layout.activity_particles);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        ImageButton ib_particles_back = findViewById(R.id.ib_particles);
        Button btn_whatsnext = findViewById(R.id.btn_whatsnext);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Database descriptor
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDataBase();

        //Gets elements from layout
        ExpandableListView expandableListView = findViewById(R.id.elv_particles);

        //Tags and/or cluster selected by the user
        id_tag_selected = sharedPreferences.getInt(Constants.SELECTED_TAG, 0);
        if(id_tag_selected > 9){
            id_tag_cluster_selected = id_tag_selected % 10;
            id_tag_selected = id_tag_selected / 10;
        }
        main_tag = sharedPreferences.getInt(Constants.MAIN_TAG, 0);

        //Gets particles list
        ArrayList<ParticleModel> particles_list = null;
        if(id_tag_selected != Constants.TAG_UE_RESIDENT && id_tag_selected != Constants.TAG_NON_EU_RESIDENT){
            particles_list = db.getParticlesByTag(id_tag_selected, Constants.TAG_SPANISH_RESIDENT, language);
        }else{
            particles_list = db.getParticlesByTag(main_tag, id_tag_selected, language);
        }

        particles_list = sortParticles(particles_list);

        String[] subjects = new String[particles_list.size()];
        for(int i = 0; i < subjects.length; i++) {
            subjects[i] = particles_list.get(i).getSubjectText();
        }

        ArrayList<ArrayList<String>> childList = new ArrayList<>();
        for(int i = 0; i < subjects.length; i++){
            //childList.add(particles_list.get(i).getTextArray());
        }

        ExpandableListAdapter adapter = new ExpandableAdapter(this, childList, subjects);
        expandableListView.setAdapter(adapter);

        //Closes the database
        db.close();

        //Buttons listeners
        ib_particles_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_whatsnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WhatsNextActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<ParticleModel> sortParticles(ArrayList<ParticleModel> source){
        int order = 0;
        int index = 0;
        int size = source.size();
        ArrayList<ParticleModel> destination = new ArrayList<>();

        for(int i = 0; i < size; i++){
            order = source.get(0).getOrder();
            index = 0;
            for (ParticleModel particle:  source){
                if(order >= particle.getOrder()){
                    order = particle.getOrder();
                    index = source.indexOf(particle);
                }
            }
            destination.add(source.get(index));
            source.remove(index);
        }

        return destination;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ParticlesClusterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
