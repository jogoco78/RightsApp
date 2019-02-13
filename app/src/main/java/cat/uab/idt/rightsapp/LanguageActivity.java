package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cat.uab.idt.rightsapp.utils.LocaleUtils;


public class LanguageActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private RadioGroup rg_answers;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        //Sets the toolbar
        Toolbar toolbarRightsApp = (Toolbar) findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        context = getApplicationContext();
        mSharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        TextView tv_selectLanguage = findViewById(R.id.textView_change_language);
        rg_answers = findViewById(R.id.radioGroup_select_language);
        Button btn_selectLanguage = findViewById(R.id.button_select_language);

        btn_selectLanguage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int id_answer = rg_answers.getCheckedRadioButtonId();
                LocaleUtils.setLocale(context, Constants.LANGUAGES[id_answer]);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Constants.PREF_LANGUAGE, Constants.LANGUAGES[id_answer]);
                editor.apply();
                System.out.println("LANGUAGE: " + Constants.LANGUAGES[id_answer]);

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

        for(int i=0; i<Constants.LANGUAGES.length; i++){
            RadioButton rb = new RadioButton(this);
            rb.setText(Constants.LANGUAGES[i]);
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
