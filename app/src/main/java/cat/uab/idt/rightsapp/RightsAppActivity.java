package cat.uab.idt.rightsapp;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

import cat.uab.idt.rightsapp.database.AnswerModel;
import cat.uab.idt.rightsapp.database.DataBaseHelper;

public class RightsAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rights_app);

        DataBaseHelper myDataBase = new DataBaseHelper(this);

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
        myDataBase.close();

    }
}
