package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class ExplanationActivity extends AppCompatActivity {

    private boolean showExplanation = true;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_explanation);
        setContentView(R.layout.activity_explanation);

        Toolbar toolbarRightsApp = (Toolbar) findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        final Switch sw_explanation = findViewById(R.id.switch_explanation);
        TextView tv_explanation = findViewById(R.id.tv_explanation);
        tv_explanation.setMovementMethod(new ScrollingMovementMethod());

        // Gets preferences file
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        showExplanation = sharedPreferences.getBoolean(Constants.SHOW_EXPLANATION, true);

        sw_explanation.setChecked(!showExplanation);
        sw_explanation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showExplanation = ! sw_explanation.isChecked();
            }
        });

        Button button = findViewById(R.id.button_explanation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Context context = getApplicationContext();
                SharedPreferences mSharedPreferences = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean(Constants.SHOW_EXPLANATION, showExplanation);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                startActivity(intent);
            }
        });
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
