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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.adapters.RVAParticlesCluster;
import cat.uab.idt.rightsapp.adapters.RVASubjectsList;
import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class ParticlesSubjectsListActivity extends AppCompatActivity implements RVASubjectsList.ItemClickListener {

    private int id_tag_selected = 0;
    private int id_tag_cluster_selected = 0;
    private int main_tag = 0;

    private SharedPreferences.Editor editor = null;

    private ArrayList<ParticleModel> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_subjects);
        setContentView(R.layout.activity_particles_subjects_list);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        ImageButton ib_back_subjects_list = findViewById(R.id.ib_back_subjects_list);
        Button btn_whats_next_subjects_list = findViewById(R.id.btn_whats_next_subjects_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_subjects_list);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Sets editor
        editor = sharedPreferences.edit();

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        main_tag = sharedPreferences.getInt(Constants.MAIN_TAG, 0);
        id_tag_selected = sharedPreferences.getInt(Constants.SELECTED_TAG, 0);

        //Database descriptor
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDataBase();

        //Gets particles list
        if(id_tag_selected != Constants.TAG_UE_RESIDENT && id_tag_selected != Constants.TAG_NON_EU_RESIDENT){
            dataSet = db.getParticlesByTag(id_tag_selected, Constants.TAG_SPANISH_RESIDENT, language);
        }else{
            dataSet = db.getParticlesByTag(main_tag, id_tag_selected, language);
        }
        dataSet = sortParticles(dataSet);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // specify an adapter
        RVASubjectsList mAdapter = new RVASubjectsList(this.getBaseContext(), dataSet);
        mAdapter.setClickListener(new RVASubjectsList.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editor.putInt(Constants.SELECTED_PARTICLE, dataSet.get(position).getId());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), ParticleDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        //Sets the back button listener
        ib_back_subjects_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        //Sets the whats next button listener
        btn_whats_next_subjects_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
        finish();
    }

    @Override
    public void onItemClick(View view, int position){
        editor.putInt(Constants.SELECTED_PARTICLE, dataSet.get(position).getId());
        System.out.println("TEST: " + dataSet.get(position).getId());
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), ParticleDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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