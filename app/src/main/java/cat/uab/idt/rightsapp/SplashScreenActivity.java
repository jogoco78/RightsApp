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
import cat.uab.idt.rightsapp.utils.LocaleUtils;

public class SplashScreenActivity extends AppCompatActivity {
    boolean agreed;
    boolean showExplanation;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        //Gets the language from the device
        Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        String deviceLocaleName = String.valueOf(locale.getLanguage());

        System.out.println("Device locale: " + deviceLocaleName);

        //Sets English by default
        String localeName = null;
        for (int i=0; i<Constants.LANGUAGES.length; i++){
            if(Constants.LANGUAGES[i].equals(deviceLocaleName)){
                localeName = String.valueOf(Constants.LANGUAGES[i]);
                System.out.println("Break: " + localeName);
                break;
            }
            else if(i == Constants.LANGUAGES.length-1) {
                localeName = String.valueOf(Constants.LANGUAGE_EN);
                System.out.println("NoBreak: " + localeName);
            }
        }

        // Gets preferences file
        Context context = getApplicationContext();
        mSharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Saves the device and pref locale
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.DEVICE_LANGUAGE, deviceLocaleName);
        editor.putString(Constants.PREF_LANGUAGE, localeName);
        editor.apply();

        //Sets the language for the app
        /*locale = new Locale(localeName);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        this.onConfigurationChanged(config);
        Intent refresh = new Intent(SplashScreenActivity.this, SplashScreenActivity.class);
        startActivity(refresh);*/

        //System.out.println("LANGUAGE DEVICE: " + mSharedPreferences.getString(Constants.DEVICE_LANGUAGE,null));
        //System.out.println("LANGUAGE PREF: " + mSharedPreferences.getString(Constants.PREF_LANGUAGE,null));

        // Copy database from res folder to the app
        DataBaseHelper myDataBase = new DataBaseHelper(this);
        try{
            myDataBase.createDatabase();
        }catch (IOException e){
            System.out.println("ERROR: Error creating database");
            throw new Error("Unable to create database: " + e.getMessage());
        }

        // Gets preferences file
        //Context context = getApplicationContext();
        //mSharedPreferences = context.getSharedPreferences(
          //      getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        agreed = mSharedPreferences.getBoolean(Constants.AGREED, false);
        showExplanation = mSharedPreferences.getBoolean(Constants.SHOW_EXPLANATION, true);

        if(!agreed){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.terms_and_conditions_title)
                    .setTitle(R.string.terms_and_conditions_body);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                    // Updates the agreed field in the preferences
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
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
