package cat.uab.idt.rightsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.CityModel;
import cat.uab.idt.rightsapp.models.CountryModel;

public class EntityActivity extends AppCompatActivity {

    private Spinner sp_select_entity_type;
    private Spinner sp_select_entity_country;
    private Spinner sp_select_entity_city;
    private Spinner sp_select_entity_postal_code;
    private DataBaseHelper dataBaseHelper;
    private String language;
    private ArrayList<CountryModel> countries_list;
    private ArrayList<CityModel> cities_list;


    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_entity);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        TextView tv_select_entity_type = findViewById(R.id.tv_select_entity_type);
        TextView tv_select_entity_country = findViewById(R.id.tv_select_entity_country);
        TextView tv_select_entity_city = findViewById(R.id.tv_select_entity_city);
        TextView tv_select_entity_postal_code = findViewById(R.id.tv_select_entity_postal_code);

        sp_select_entity_type = findViewById(R.id.sp_entity_type);
        sp_select_entity_country = findViewById(R.id.sp_entity_country);
        sp_select_entity_city = findViewById(R.id.sp_entity_city);
        sp_select_entity_postal_code = findViewById(R.id.sp_entity_postal_code);

        if (dataBaseHelper == null) {
            //Opens DB
            dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.openDataBase();
        }

        //Gets preference file & language
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Set initial values for country spinner
        countries_list = dataBaseHelper.getCountriesList(null, language);

        ArrayAdapter<CountryModel> country_dataAdapter = new ArrayAdapter<CountryModel>(this, android.R.layout.simple_spinner_item, countries_list);
        sp_select_entity_country.setAdapter(country_dataAdapter);
        country_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set initial values for cities spinner
        cities_list = dataBaseHelper.getCitiesList(null, language);

        ArrayAdapter<CityModel> city_dataAdapter = new ArrayAdapter<CityModel>(this, android.R.layout.simple_spinner_item, cities_list);
        sp_select_entity_city.setAdapter(city_dataAdapter);
        city_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set listener for country spinner
        sp_select_entity_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cities_list.clear();
                CountryModel cm = (CountryModel) parent.getSelectedItem();
                cities_list = dataBaseHelper.getCitiesList(new int[] {cm.getId()}, language);

                //Toast.makeText(MainActivity.this, "Selected : "+ categories.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set listener for city spinner
        sp_select_entity_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, "Selected : "+ categories.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
