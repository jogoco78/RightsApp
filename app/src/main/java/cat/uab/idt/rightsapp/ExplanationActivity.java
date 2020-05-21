package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ExplanationActivity extends AppCompatActivity {

    private boolean showExplanation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_explanation);
        setContentView(R.layout.activity_explanation);

        Toolbar toolbarRightsApp = (Toolbar) findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        final Switch sw_explanation = findViewById(R.id.switch_explanation);
        TextView tv_howto_title = findViewById(R.id.tv_howto_title);
        TextView tv_howto_body = findViewById(R.id.tv_howto_body);
        TextView tv_howto_first = findViewById(R.id.tv_howto_first);
        TextView tv_howto_second = findViewById(R.id.tv_howto_second);
        TextView tv_howto_third = findViewById(R.id.tv_howto_third);
        TextView tv_howto_preferences_title = findViewById(R.id.tv_howto_preferences_title);
        TextView tv_howto_preferences_body = findViewById(R.id.tv_howto_preferences_body);
        TextView tv_howto_preferences_first = findViewById(R.id.tv_howto_preferences_first);
        TextView tv_howto_preferences_second = findViewById(R.id.tv_howto_preference_second);
        TextView tv_howto_preferences_third = findViewById(R.id.tv_howto_preferences_third);
        TextView tv_howto_preferences_fourth = findViewById(R.id.tv_howto_preferences_fourth);

        int flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

        SpannableStringBuilder ssb_title = new SpannableStringBuilder();
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        //HowTo title
        ssb_title.clear();
        ssb_title.append(getResources().getString(R.string.explanation_howTo_title));
        ssb_title.setSpan (new StyleSpan(Typeface.BOLD), 0, ssb_title.length(), flag);
        tv_howto_title.setText(ssb_title, TextView.BufferType.SPANNABLE);
        tv_howto_title.setTextSize((float) 25.0);

        //HowTo body
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_howTo_body));
        tv_howto_body.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_body.setTextSize((float) 18.0);

        //HowTo first scenario
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_howTo_first));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_howTo_first).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_howTo_first).indexOf(":"), flag);
        tv_howto_first.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_first.setTextSize((float) 18.0);

        //HowTo second scenario
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_howTo_second));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_howTo_second).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_howTo_second).indexOf(":"), flag);
        tv_howto_second.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_second.setTextSize((float) 18.0);

        //HowTo third scenario
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_howTo_third));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_howTo_third).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_howTo_third).indexOf(":"), flag);
        tv_howto_third.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_third.setTextSize((float) 18.0);

        //Preferences menu title
        ssb_title.clear();
        ssb_title.append(getResources().getString(R.string.explanation_preferences_title));
        ssb_title.setSpan (new StyleSpan(Typeface.BOLD), 0, ssb_title.length(), flag);
        tv_howto_preferences_title.setText(ssb_title, TextView.BufferType.SPANNABLE);
        tv_howto_preferences_title.setTextSize((float) 25.0);

        //Preferences body
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_preferences_body));
        tv_howto_preferences_body.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_preferences_body.setTextSize((float) 18.0);

        //First option preferences
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_preferences_first_scenario));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_preferences_first_scenario).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_preferences_first_scenario).indexOf(":"), flag);
        tv_howto_preferences_first.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_preferences_first.setTextSize((float) 18.0);

        //Second option preferences
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_preferences_second_scenario));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_preferences_second_scenario).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_preferences_second_scenario).indexOf(":"), flag);
        tv_howto_preferences_second.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_preferences_second.setTextSize((float) 18.0);

        //Third option preferences
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_preferences_third_scenario));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_preferences_third_scenario).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_preferences_third_scenario).indexOf(":"), flag);
        tv_howto_preferences_third.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_preferences_third.setTextSize((float) 18.0);

        //Fourth option preferences
        ssb.clear();
        ssb.append(getResources().getString(R.string.explanation_preferences_fourth_scenario));
        ssb.setSpan(new UnderlineSpan(), 0, getResources().getString(R.string.explanation_preferences_fourth_scenario).indexOf(":"), flag);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(R.string.explanation_preferences_fourth_scenario).indexOf(":"), flag);
        tv_howto_preferences_fourth.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_howto_preferences_fourth.setTextSize((float) 18.0);

        // Gets preferences file
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
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
