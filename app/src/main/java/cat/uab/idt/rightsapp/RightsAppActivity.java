package cat.uab.idt.rightsapp;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

import cat.uab.idt.rightsapp.database.AnswersModel;
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
            e.printStackTrace();
            throw new Error("Unable to create database: " + e.getMessage());
        }

       /* try{
            myDataBase.openDataBase();
        }catch (SQLException e){
            throw new Error("Unable to open database");
        }

        ArrayList<AnswersModel> result = myDataBase.getAnswers(1);
        for(int i = 0; i< result.size(); i++){

        }

        System.out.println("Fin1. Size: " + result.size());*/


    }
}
