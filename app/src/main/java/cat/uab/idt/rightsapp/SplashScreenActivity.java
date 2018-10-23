package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.io.IOException;

import cat.uab.idt.rightsapp.database.DataBaseHelper;

public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        DataBaseHelper myDataBase = new DataBaseHelper(this);
        try{
            myDataBase.createDatabase();
        }catch (IOException e){
            System.out.println("Error creating database");
            throw new Error("Unable to create database: " + e.getMessage());
        }

        Context context = getApplicationContext();
        mSharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean agreed = mSharedPreferences.getBoolean("agreed", false);
        if(agreed){
            System.out.println("Agreed");
        }else{
            System.out.println("Not agreed");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.terms_and_conditions_title1)
                    .setTitle(R.string.terms_and_conditions_body1);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean("agreed", true);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), TermsAndConditions.class);
                    startActivity(intent);
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
        }


    }
}
