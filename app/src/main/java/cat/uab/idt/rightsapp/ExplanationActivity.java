package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class ExplanationActivity extends AppCompatActivity {
    boolean showExplanation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        Toolbar toolbarRightsApp = (Toolbar) findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

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
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean firstTime = sharedPreferences.getBoolean("firstTime", true);
        if(firstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
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
