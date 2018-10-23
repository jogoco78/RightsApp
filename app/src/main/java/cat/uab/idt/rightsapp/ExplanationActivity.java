package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class ExplanationActivity extends AppCompatActivity {
    boolean showExplanation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        final Button button = findViewById(R.id.button_explanation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Context context = getApplicationContext();
                SharedPreferences mSharedPreferences = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("show_explanation", showExplanation);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), RightsAppActivity.class);
                startActivity(intent);

            }
        });

        final Switch _switch = findViewById(R.id.switch_explanation);
        _switch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                showExplanation = !_switch.isChecked();
            }
        });

        /*Context context = getApplicationContext();
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
        }*/



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
