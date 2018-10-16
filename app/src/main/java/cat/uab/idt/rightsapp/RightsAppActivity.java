package cat.uab.idt.rightsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.io.IOException;
import java.util.ArrayList;

import cat.uab.idt.rightsapp.database.AnswerModel;
import cat.uab.idt.rightsapp.database.DataBaseHelper;

public class RightsAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rights_app);

        Context context = getApplicationContext();
        SharedPreferences mSharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean firstTime = mSharedPreferences.getBoolean("firstTime", true);
        if(firstTime){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();

            DataBaseHelper myDataBase = new DataBaseHelper(this);
            try{
                myDataBase.createDatabase();
            }catch (IOException e){
                System.out.println("Error creating database");
                throw new Error("Unable to create database: " + e.getMessage());
            }

            Intent intent = new Intent(getApplicationContext(), TermsAndConditions.class);
            startActivity(intent);
        }else{
            System.out.println("Not first time");
        }



        /*DataBaseHelper myDataBase = new DataBaseHelper(this);

        try{
            myDataBase.createDatabase();
        }catch (IOException e){
            System.out.println("Error creating database");
            throw new Error("Unable to create database: " + e.getMessage());
        }

       try{
            myDataBase.openDataBase();
        }catch (SQLException e){
            System.out.println("Error opening database");
           // throw new Error("Unable to open database");
        }catch (Exception e){
           System.out.println("Error opening database 2");
       }
       ArrayList<AnswerModel> result = myDataBase.getAnswersForQuestion(2);
        for(int i = 0; i < result.size(); i++){
            System.out.println("i: " + i + " ID: " + result.get(i).getId());
            System.out.println("Text_es: " + result.get(i).getText_es());
        }

        System.out.println("Fin1. Size: " + result.size());
        myDataBase.close();*/

    }
}
