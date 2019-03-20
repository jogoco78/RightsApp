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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.database.DataBaseHelper;
import cat.uab.idt.rightsapp.models.CategoryModel;
import cat.uab.idt.rightsapp.models.CityModel;
import cat.uab.idt.rightsapp.models.CountryModel;

public class EntityActivity extends AppCompatActivity {

    private String language;
    private Spinner sp_select_entity_category;
    private Spinner sp_select_entity_country;
    private Spinner sp_select_entity_city;

    private DataBaseHelper dataBaseHelper;

    private ArrayList<CategoryModel> categories_list;
    private ArrayList<CountryModel> countries_list;
    private ArrayList<CityModel> cities_list;
    private ArrayAdapter<CategoryModel> category_dataAdapter;
    private ArrayAdapter<CityModel> city_dataAdapter;

    private SharedPreferences sharedPreferences;


    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_entity);

        //Sets the toolbar
        Toolbar toolbarRightsApp = findViewById(R.id.toolbar_rights_app);
        setSupportActionBar(toolbarRightsApp);

        TextView tv_select_entity_type = findViewById(R.id.tv_select_entity_type);
        TextView tv_select_entity_country = findViewById(R.id.tv_select_entity_country);
        TextView tv_select_entity_city = findViewById(R.id.tv_select_entity_city);

        sp_select_entity_category = findViewById(R.id.sp_entity_category);
        sp_select_entity_country = findViewById(R.id.sp_entity_country);
        sp_select_entity_city = findViewById(R.id.sp_entity_city);

        if (dataBaseHelper == null) {
            //Opens DB
            dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.openDataBase();
        }

        //Gets preference file & language
        sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        language = sharedPreferences.getString(Constants.PREF_LANGUAGE, null);

        //Set initial values for categories spinner
        categories_list = dataBaseHelper.getCategoriesList(null, language);
        CategoryModel cat_model = new CategoryModel(
                0,
                getResources().getString(R.string.all_categories),
                language);
        categories_list.add(0, cat_model);

        category_dataAdapter = new ArrayAdapter<CategoryModel>(this, android.R.layout.simple_spinner_item, categories_list);
        sp_select_entity_category.setAdapter(category_dataAdapter);
        category_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set initial values for country spinner
        countries_list = dataBaseHelper.getCountriesList(null, language);
        CountryModel all_countries_option = new CountryModel(
                0,
                getResources().getString(R.string.all_countries),
                language);
        countries_list.add(0, all_countries_option);

        ArrayAdapter<CountryModel> country_dataAdapter = new ArrayAdapter<CountryModel>(this, android.R.layout.simple_spinner_item, countries_list);
        sp_select_entity_country.setAdapter(country_dataAdapter);
        country_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set initial values for cities spinner
        cities_list = dataBaseHelper.getCitiesList(null, language);
        CityModel all_cities_option = new CityModel(
                0,
                getResources().getString(R.string.all_cities),
                0,
                language);
        cities_list.add(0, all_cities_option);

        city_dataAdapter = new ArrayAdapter<CityModel>(this, android.R.layout.simple_spinner_item, cities_list);
        sp_select_entity_city.setAdapter(city_dataAdapter);
        city_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set listener for category spinner
        sp_select_entity_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set listener for country spinner
        sp_select_entity_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryModel country_model = (CountryModel) parent.getSelectedItem();

                cities_list.clear();

                //Updates cities spinner
                if(country_model.getId() == 0){
                    cities_list = dataBaseHelper.getCitiesList(null, language);
                }else{
                    cities_list = dataBaseHelper.getCitiesList(new int[]{country_model.getId()}, language);
                }

                CityModel all_cities_option = new CityModel(
                        0,
                        getResources().getString(R.string.all_cities),
                        0,
                        language);
                cities_list.add(0, all_cities_option);

                city_dataAdapter = new ArrayAdapter<CityModel>(parent.getContext(), android.R.layout.simple_spinner_item, cities_list);
                sp_select_entity_city.setAdapter(city_dataAdapter);
                city_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set listener for city spinner
        sp_select_entity_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CityModel city_model = (CityModel) parent.getSelectedItem();

                if(city_model.getId() != 0){
                    for(int i = 0; i < countries_list.size(); i++) {
                        if (city_model.getId_country() == countries_list.get(i).getId()) {
                            sp_select_entity_country.setSelection(i, true);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Sets the button and its listener
        Button btn_continue = findViewById(R.id.btn_entity_next);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryModel category_model = (CategoryModel) sp_select_entity_category.getSelectedItem();

                CountryModel country_model = (CountryModel) sp_select_entity_country.getSelectedItem();

                CityModel city_model = (CityModel) sp_select_entity_city.getSelectedItem();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.SEARCH_ENTITY_CRITERIA,
                        category_model.getId() +
                                "," + country_model.getId() +
                                "," + city_model.getId());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), NavigateActivity.class);
                startActivity(intent);
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
