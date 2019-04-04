package cat.uab.idt.rightsapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;


public class LanguageActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private RadioGroup rg_answers;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_language);
        setContentView(R.layout.activity_language);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        //Gets preferences
        Context context = getApplicationContext();
        mSharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Gets the layout
        //TextView tv_selectLanguage = findViewById(R.id.textView_change_language);
        rg_answers = findViewById(R.id.radioGroup_select_language);
        Button btn_selectLanguage = findViewById(R.id.btn_select_language);
        Button btn_back_language = findViewById(R.id.btn_back_language);

        //Sets the alert dialog within the activity context
        builder = new AlertDialog.Builder(this);

        btn_selectLanguage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int id_answer = rg_answers.getCheckedRadioButtonId();

                if(id_answer == -1) {
                    //No answer is set by the user
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
                }else {
                    //Saves the device and pref locale
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(Constants.PREF_LANGUAGE, Constants.LANGUAGE_CODES[id_answer]);
                    editor.apply();

                    //Sets the language for the app
                    Locale locale = new Locale(Constants.LANGUAGE_CODES[id_answer], Constants.REGIONS[id_answer]);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                    Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_back_language.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        rg_answers.clearCheck();
        rg_answers.removeAllViews();

        for(int i = 0; i<Constants.LANGUAGE_CODES.length; i++){
            RadioButton rb = new RadioButton(this);
            switch(Constants.LANGUAGE_CODES[i]){
                case "es":
                    rb.setText(getResources().getString(R.string.language_name_spanish));
                    break;
                case "en":
                    rb.setText(getResources().getString(R.string.language_name_english));
                    break;
                case "por":
                    rb.setText(getResources().getString(R.string.language_name_portuguese));
                    break;
                case "it":
                    rb.setText(getResources().getString(R.string.language_name_italian));
                    break;
            }
            //rb.setLayoutParams(rg_answersParams);
            rb.setId(i);
            rg_answers.addView(rb);
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
