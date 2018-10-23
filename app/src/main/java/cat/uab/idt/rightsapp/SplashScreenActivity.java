package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import cat.uab.idt.rightsapp.database.DataBaseHelper;

public class SplashScreenActivity extends AppCompatActivity {
    boolean agreed;
    boolean showExplanation;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        //Copy database from res folder to the app
        DataBaseHelper myDataBase = new DataBaseHelper(this);
        try{
            myDataBase.createDatabase();
        }catch (IOException e){
            System.out.println("Error creating database");
            throw new Error("Unable to create database: " + e.getMessage());
        }

        //Gets preferences file
        Context context = getApplicationContext();
        mSharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        agreed = mSharedPreferences.getBoolean("agreed", false);
        showExplanation = mSharedPreferences.getBoolean("show_explanation", true);
        if(!agreed){
            System.out.println("Not agreed");

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

                    // Starts next activity
                    if(showExplanation) {
                        Intent intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                        startActivity(intent);
                    }
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
            // Starts next activity
            if(showExplanation) {
                Intent intent = new Intent(getApplicationContext(), ExplanationActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                startActivity(intent);
            }
        }
    }
}
