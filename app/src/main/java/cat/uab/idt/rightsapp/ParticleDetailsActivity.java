package cat.uab.idt.rightsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class ParticleDetailsActivity extends AppCompatActivity {

    private String[] particle_texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_particle_details);
        setContentView(R.layout.activity_particle_details);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        ConstraintLayout cl = findViewById(R.id.cl_particle_details);
        TextView tv_particle_title = new TextView(this);
        TextView tv_particle_details = new TextView(this);
        tv_particle_details.setId(View.generateViewId());
        tv_particle_title.setId(View.generateViewId());
        cl.addView(tv_particle_title);
        cl.addView(tv_particle_details);

        Button btn_whats_next_particle_details = findViewById(R.id.btn_whats_next_particle_details);
        ImageButton ib_back_particle_details = findViewById(R.id.ib_back_particle_details);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the language stored in Preferences for the app
        String language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        int id_particle = sharedPreferences.getInt(Constants.SELECTED_PARTICLE, 0);

        //Database descriptor
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDataBase();

        ArrayList<ParticleModel> particles = db.getParticles(new int[] {id_particle}, language);
        String title = particles.get(0).getSubjectText();
        particle_texts = particles.get(0).getTextSplit();

        db.close();

        //Constraint params_title for details
        ConstraintLayout.LayoutParams params_title = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        );
        tv_particle_title.setLayoutParams(params_title);

        //Constraint params_details for details
        ConstraintLayout.LayoutParams params_details = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        );
        tv_particle_details.setLayoutParams(params_details);

        //Constraints in the layout
        ConstraintSet cs = new ConstraintSet();
        cs.clone(cl);

        //Constraints title
        cs.connect(tv_particle_title.getId(),ConstraintSet.TOP,toolbarRightsApp.getId(),ConstraintSet.BOTTOM,50);
        cs.connect(tv_particle_title.getId(),ConstraintSet.LEFT,cl.getId(),ConstraintSet.LEFT, 20);
        cs.connect(tv_particle_title.getId(),ConstraintSet.RIGHT,cl.getId(),ConstraintSet.RIGHT, 20);

        //Constraint details
        cs.connect(tv_particle_details.getId(),ConstraintSet.TOP,tv_particle_title.getId(),ConstraintSet.BOTTOM,40);
        cs.connect(tv_particle_details.getId(),ConstraintSet.LEFT,cl.getId(),ConstraintSet.LEFT, 20);
        cs.connect(tv_particle_details.getId(),ConstraintSet.RIGHT,cl.getId(),ConstraintSet.RIGHT, 20);
        cs.connect(tv_particle_details.getId(),ConstraintSet.BOTTOM,btn_whats_next_particle_details.getId(),ConstraintSet.TOP, 16);
        cs.applyTo(cl);

        int flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        ssb.append(title);
        ssb.setSpan(new UnderlineSpan(), 0, title.length(), 0);
        tv_particle_title.setGravity(Gravity.CENTER);
        tv_particle_title.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_black));
        tv_particle_title.setTextColor(Color.BLACK);
        tv_particle_title.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_particle_title.setTextSize((float) 24.0);
        tv_particle_title.setLineSpacing((float) 5.0,(float) 1.0);

        ssb.clear();
        for(int i = 0; i < particle_texts.length; i++){
            ssb.append("\t" + particle_texts[i]+"\n\n");
        }
        tv_particle_details.setVerticalScrollBarEnabled(true);
        tv_particle_details.setMovementMethod(new ScrollingMovementMethod());

        tv_particle_details.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_black));
        tv_particle_details.setTextColor(Color.BLACK);
        tv_particle_details.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_particle_details.setTextSize((float) 18.0);
        tv_particle_details.setLineSpacing((float) 5.0,(float) 1.0);

        //Sets buttons listeners
        btn_whats_next_particle_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), WhatsNextActivity.class);
                startActivity(intent);
            }
        });

        ib_back_particle_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
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