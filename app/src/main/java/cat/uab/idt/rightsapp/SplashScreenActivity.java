package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.Locale;

import cat.uab.idt.rightsapp.database.DataBaseHelper;

public class SplashScreenActivity extends AppCompatActivity {
    boolean agreed;
    boolean showExplanation;
    String language;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        // Copy database from res folder to the app
        DataBaseHelper myDataBase = new DataBaseHelper(this);
        try{
            myDataBase.createDatabase();
        }catch (IOException e){
            System.out.println("ERROR: Error creating database");
            throw new Error("Unable to create database: " + e.getMessage());
        }

        // Gets preferences file
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Sets the language stored in Preferences for the app
        language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);
        if(language == null){
            //if language is not set, it takes the language of the device - English by default
            Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);

            int index = 0;
            for(index = 0; index < Constants.LANGUAGE_CODES.length; index++){
                if(Constants.LANGUAGE_CODES[index].equals(locale.getLanguage())) break;
            }

            locale = null;
            if(index == Constants.LANGUAGE_CODES.length){
                //Language by default - not in the list
                locale = new Locale(Constants.LANGUAGE_CODES[1], Constants.REGIONS[1]);
                language = Constants.LANGUAGE_CODES[1];
            }else{
                //Sets the language as the device
                locale = new Locale(Constants.LANGUAGE_CODES[index], Constants.REGIONS[index]);
                language = Constants.LANGUAGE_CODES[index];
            }
            //Sets the language for the app
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            //Stores the language
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.PREF_LANGUAGE, language);
            editor.apply();
        }else {
            int index = 0;
            for(index = 0; index < Constants.LANGUAGE_CODES.length; index++){
                if(Constants.LANGUAGE_CODES[index].equals(language)) break;
            }

            //Sets the language for the app
            Locale locale = new Locale(Constants.LANGUAGE_CODES[index], Constants.REGIONS[index]);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        agreed = sharedPreferences.getBoolean(Constants.AGREED, false);
        showExplanation = sharedPreferences.getBoolean(Constants.SHOW_EXPLANATION, true);

        //Test code
        //agreed = false;
        //showExplanation = true;
        //End test code

        if(!agreed){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.terms_and_conditions_body)
                    .setTitle(R.string.title_activity_terms_and_conditions);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                    // Updates the agreed field in the preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("agreed", true);
                    editor.apply();

                    // Sets a delay for the splashscreen
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 3s = 3000ms
                            // Starts next activity
                            if(showExplanation) {
                                Intent intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, 3000);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    finish();
                }
            });

            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

            // Sets text button color to black
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 3s = 3000ms
                    // Starts next activity when agreed terms and conditions
                    if(showExplanation) {
                        Intent intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                        startActivity(intent);
                    }
                }
            }, 3000);
        }
    }
}
