package cat.uab.idt.rightsapp.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import cat.uab.idt.rightsapp.models.CategoryModel;
import cat.uab.idt.rightsapp.models.CityModel;
import cat.uab.idt.rightsapp.models.CountryModel;
import cat.uab.idt.rightsapp.models.EntityModel;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static String DB_NAME = "rightsapp_v6_utf16.db";

    /**
     * Assets path for databases folder
     */
    private static String ASSETS_PATH = "databases/";

    /**
     * Database descriptor
     */
    private SQLiteDatabase myDataBase = null;

    /**
     * Context of the app
     */
    private Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference to access to the application assets and resources
     * @param context
     */
    public DataBaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates the database if it does not exist
     * @throws IOException
     */
    public void createDatabase() throws IOException {
        File dbFile = myContext.getDatabasePath(DB_NAME);

        if(dbFile.exists()){
            dbFile.delete();
        }
        myDataBase = myContext.openOrCreateDatabase(dbFile.getName(), myContext.MODE_PRIVATE, null);
        myDataBase.close();

        CopyDataBase(dbFile);
    }

    /**
     * Copies the database from the assets folder to the default location in the app
     * @throws IOException
     */
    private void CopyDataBase(File dbFile) throws IOException{

        //open local db as input stream
        InputStream myInput = myContext.getAssets().open(ASSETS_PATH + DB_NAME, AssetManager.ACCESS_STREAMING);

        //Open the empty db as outputstream
        OutputStream myOutput = new FileOutputStream(dbFile);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ( (bytesRead = myInput.read(buffer))!= -1){
            myOutput.write(buffer, 0, bytesRead);
        }

        //close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Opens the database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        //open the database
        myDataBase = SQLiteDatabase.openDatabase(myContext.getDatabasePath(DB_NAME).toString(), null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null){
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Nothing to do
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Nothing to do
    }

    //SETTER AND GETTER

    /**
     * Sets the database descriptor into the class
     * @param myDataBase database descriptor
     */
    public void setMyDataBase(SQLiteDatabase myDataBase){
        this.myDataBase = myDataBase;
    }

    /**
     * Return the database descriptor
     * @return database descriptor
     */
    public SQLiteDatabase getMyDataBase(){
        return myDataBase;
    }

    //Public helper methods to access and get content from the database

    /*********************************************************************************************
     **************************     QUESTIONS AND ANSWERS      ***********************************
     *********************************************************************************************/

    /**
     * Returns the text of the question given by parameter in the specified language
     * @param id_question ID of the question
     * @param language languge of the text to be returned
     * @return the text of the given question in the text specified by parameter
     */
    public String getQuestionText(int id_question, String language){
        return getQuestionsText(new int[] {id_question}, language)[0];
    }

    /**
     * Returns an array with the text of the questions given by parameters
     * @param id_questions ID of the question
     * @param language language of the text to be returned
     * @return an array of texts of the given questions in the text specified by parameter
     */
    public String[] getQuestionsText(int[] id_questions, String language){
        int index = 0;
        String[] result = new String[id_questions.length];

        //TODO: Order of the responses from the Database
        String query = "SELECT * FROM " + DBContract.Questions.TABLE_NAME
                + " WHERE " +  DBContract.Questions.COLUMN_NAME_ID + " IN (" + id_questions[0];

        for(int i = 1; i < id_questions.length; i++){
            query = query + "," + id_questions[i];
        }
        query = query + ")";

        //Launch the query
        Cursor cursor = myDataBase.rawQuery(query, null);

        //Gets the results
        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                switch (language){
                    case "en":
                        result[index] = cursor.getString(2);
                        break;
                    case "es":
                        result[index] = cursor.getString(1);
                        break;
                    case "por":
                        result[index] = cursor.getString(3);
                        break;
                    case "it":
                        result[index] = cursor.getString(4);
                        break;
                    default:
                        //do default
                        break;
                }
                index++;
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /**
     * Returns the ID of the answers for a given question ID
     * @param id_question given question ID
     * @return the IDs of the answers for a given question ID
     */
    public int[] getAnswersIDForQuestion(int id_question){
        String query = "SELECT " + DBContract.Questions_Answers.COLUMN_NAME_ID_ANSWER
                + " FROM " + DBContract.Questions_Answers.TABLE_NAME
                + " WHERE " +  DBContract.Questions_Answers.COLUMN_NAME_ID_QUESTION + " = "
                + String.valueOf(id_question);

        Cursor cursor = myDataBase.rawQuery(query, null);

        int index = 0;
        int[] id_answers = new int[cursor.getCount()];

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                id_answers[index] = cursor.getInt(0);
                index++;

            } while (cursor.moveToNext());
        }
        cursor.close();

        return id_answers;
    }

    /**
     * Returns the next question ID from question and answers IDs
     * @param id_question ID of the question
     * @param id_answer ID of the answer
     * @return the ID of the next question
     */
    public int getNextQuestionID(int id_question, int id_answer){
        String query = "SELECT " + DBContract.Questions_Answers.COLUMN_NAME_ID_NEXT_QUESTION
                + " FROM " + DBContract.Questions_Answers.TABLE_NAME
                + " WHERE " +  DBContract.Questions_Answers.COLUMN_NAME_ID_QUESTION + " = "
                + String.valueOf(id_question) + " AND " + DBContract.Questions_Answers.COLUMN_NAME_ID_ANSWER
                + " = " + String.valueOf(id_answer);

        Cursor cursor = myDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        int id_next_question = cursor.getInt(0);

        cursor.close();

        return id_next_question;
    }

    /**
     * Returns the tag that a question and answer raises, if any
     * @param id_question ID of the question
     * @param id_answer ID of the answer
     * @return the ID of the tag raised - 0 if no tag is raised
     */
    public int getTagRaisedID(int id_question, int id_answer){
        int id_tag_raised = 0;
        String query = "SELECT " + DBContract.Questions_Answers.COLUMN_NAME_ID_TAG_RAISED
                + " FROM " + DBContract.Questions_Answers.TABLE_NAME
                + " WHERE " +  DBContract.Questions_Answers.COLUMN_NAME_ID_QUESTION + " = "
                + id_question + " AND " + DBContract.Questions_Answers.COLUMN_NAME_ID_ANSWER
                + " = " + id_answer;

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            id_tag_raised = cursor.getInt(0);
        }

        cursor.close();

        return id_tag_raised;
    }

    public String getAnswerText(int id_answer, String language){
        return getAnswersText(new int[] {id_answer}, language)[0];
    }

    public String[] getAnswersText(int[] id_answers, String language) {
        int index = 0;
        String[] result = new String[id_answers.length];

        String query = "SELECT * FROM " + DBContract.Answers.TABLE_NAME
                + " WHERE " +  DBContract.Answers.COLUMN_NAME_ID + " IN (" + id_answers[0];

        for(int i = 1; i < id_answers.length; i++){
            query = query + "," + id_answers[i];
        }
        query = query + ") ORDER BY " + DBContract.Answers.COLUMN_NAME_ID + " ASC";

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                switch (language){
                    case "es":
                        result[index] = cursor.getString(1);
                        break;
                    case "en":
                        result[index] = cursor.getString(2);
                        break;
                    case "por":
                        result[index] = cursor.getString(3);
                        break;
                    case "it":
                        result[index] = cursor.getString(4);
                        break;
                    default:
                        //do default
                        break;
                }
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }
    /*********************************************************************************************
    **************************     ENTITIES AND CATEGORIES      **********************************
    *********************************************************************************************/

    /**
     * Returns an ArrayList of entities that match the criteria given by parameters in the specified
     * language. Just one language is recovered
     * @param id_categories id of the category to search in the database
     * @param id_countries id of the country to search in the database
     * @param id_cities id of the city to search in the database
     * @param language the language of the recovered information about the entity
     * @return returns an ArrayList with the entities that match the criteria
     */
    public ArrayList<EntityModel> getEntitiesList(int[] id_categories, int[] id_countries, int[] id_cities, String language){
        boolean previous_clause = false;
        ArrayList<EntityModel> results = new ArrayList<>();

        String query = "SELECT * FROM " + DBContract.Entities.TABLE_NAME +
                " INNER JOIN " + DBContract.Cities.TABLE_NAME + " ON " +
                DBContract.Cities.TABLE_NAME + "." + DBContract.Cities.COLUMN_NAME_ID +
                "=" + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_CITY +
                " INNER JOIN " + DBContract.Countries.TABLE_NAME + " ON " +
                DBContract.Countries.TABLE_NAME + "." + DBContract.Countries.COLUMN_NAME_ID +
                "=" + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_COUNTRY;

        //inner join cities on cities.id = entities.id_city inner join countries on countries.id = entities.id_country

        if(id_cities != null && id_cities[0] != 0){
            query = query + " WHERE " + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_CITY + " IN (" + id_cities[0];
            for(int i = 1; i < id_cities.length; i++){
                query = query + "," + id_cities[i];
            }
            query = query + ")";
            previous_clause = true;
        }

        if(id_countries != null && id_countries[0] != 0){
            if(previous_clause) query = query + " AND ";
            else query = query + " WHERE ";
            query = query + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_COUNTRY + " IN (" + id_countries[0];
            for(int i = 1; i < id_countries.length; i++){
                query = query + "," + id_countries[i];
            }
            query = query + ")";
            previous_clause = true;
        }

        if(id_categories != null && id_categories[0] != 0){
            if(previous_clause) query = query + " AND ";
            else query = query + " WHERE ";
            query = query + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_CATEGORY + " IN (" + id_categories[0];
            for(int i = 1; i < id_categories.length; i++){
                query = query + "," + id_categories[i];
            }
            query = query + ")";
        }

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                EntityModel em = new EntityModel();
                em.setId(cursor.getInt(0));
                em.setLanguage(language);
                em.setEntity_name(cursor.getString(1));
                em.setAddress(cursor.getString(6));
                em.setLongitude(cursor.getDouble(8));
                em.setLatitude(cursor.getDouble(7));
                em.setId_city(cursor.getInt(9));
                em.setId_country(cursor.getInt(10));
                em.setId_category(cursor.getInt(11));
                em.setPhone_number(cursor.getString(12));
                em.setPhone_number2(cursor.getString(13));
                em.setLink(cursor.getString(14));
                em.setEmail(cursor.getString(15));

                switch (language){
                    case "es":
                        em.setEntity_description(cursor.getString(2));
                        em.setCity_name(cursor.getString(17));
                        em.setCountry_name(cursor.getString(23));
                        break;
                    case "en":
                        em.setEntity_description(cursor.getString(3));
                        em.setCity_name(cursor.getString(18));
                        em.setCountry_name(cursor.getString(24));
                        break;
                    case "por":
                        em.setEntity_description(cursor.getString(4));
                        em.setCity_name(cursor.getString(19));
                        em.setCountry_name(cursor.getString(25));
                        break;
                    case "it":
                        em.setEntity_description(cursor.getString(5));
                        em.setCity_name(cursor.getString(20));
                        em.setCountry_name(cursor.getString(26));
                        break;
                    default:
                        //do default
                        break;
                }
                results.add(em);
            } while (cursor.moveToNext());
        }
        cursor.close();



        return results;
    }

    public ArrayList<CategoryModel> getCategoriesList(int[] id_categories, String language){
        ArrayList<CategoryModel> results = new ArrayList<>();

        String query = "SELECT * FROM " + DBContract.Categories.TABLE_NAME;

        if(id_categories != null){
            query = query + " WHERE " + DBContract.Entities.COLUMN_NAME_ID_CATEGORY + " IN (" + id_categories[0];
            for(int i = 1; i < id_categories.length; i++){
                query = query + "," + id_categories[i];
            }
            query = query + ")";
        }

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                CategoryModel cat_model = new CategoryModel();
                cat_model.setId(cursor.getInt(0));
                cat_model.setLanguage(language);
                switch (language){
                    case "es":
                        cat_model.setCategory_name(cursor.getString(1));
                        break;
                    case "en":
                        cat_model.setCategory_name(cursor.getString(2));
                        break;
                    case "por":
                        cat_model.setCategory_name(cursor.getString(3));
                        break;
                    case "it":
                        cat_model.setCategory_name(cursor.getString(4));
                        break;
                    default:
                        //do default
                        break;
                }
                results.add(cat_model);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }



    /********************************************************************************************
     **********************     PARTICLES, SUBJECTS AND TAGS       ******************************
     ********************************************************************************************/

    /**
     * Returns the particles related to the given tag
     * @param particlesMainTags the list of tags
     * @param language the language for the particles
     * @return an ArrayList with the particles related to given tags in specified language
     */
    public ArrayList<ParticleModel> getParticlesByTag(ArrayList<Integer> particlesMainTags, ArrayList<Integer> particlesResidenceTags, String language){
        int[] results;

        String query = "SELECT DISTINCT * FROM " + DBContract.Particles_MainTags.TABLE_NAME + " pm INNER JOIN " + DBContract.Particles_residenceTags.TABLE_NAME + " pr ON pm."
                + DBContract.Particles_MainTags.COLUMN_NAME_ID_PARTICLE + "=pr." + DBContract.Particles_residenceTags.COLUMN_NAME_ID_PARTICLE;

        ArrayList<String> whereClause = new ArrayList<>();
        for (int pm: particlesMainTags){
            for(int pr: particlesResidenceTags){
                whereClause.add("pm." + DBContract.Particles_MainTags.COLUMN_NAME_ID_TAG + "=" + pm + " AND pr." + DBContract.Particles_residenceTags.COLUMN_NAME_ID_TAG + "=" + pr);
            }
        }

        query = query + " WHERE ";
        for(int i=0; i < whereClause.size(); i++){
            query = query + whereClause.get(i);
            if(i < whereClause.size() - 1){
                query = query + " OR ";
            }
        }

        System.out.println("Query test: " + query);
        Cursor cursor = myDataBase.rawQuery(query, null);
        results = new int[cursor.getCount()];

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            int index = 0;
            do {
                results[index] = cursor.getInt(0);
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return getParticles(results, language);
    }

    /**
     * Returns the subjects of the rights by their ID
     * @param id_subjects the id of the subjects
     * @param language the language for the subject to be returned
     * @return an String array with the subjects that match the given IDs
     */
    public String[] getSubjectByID(int[] id_subjects, String language){
        String[] results;

        String query = "SELECT * FROM " + DBContract.Subjects.TABLE_NAME;

        if(id_subjects != null){
            query = query + " WHERE " + DBContract.Subjects.COLUMN_NAME_ID + " IN (" + id_subjects[0];
            for(int i = 1; i < id_subjects.length; i++){
                query = query + "," + id_subjects[i];
            }
            query = query + ")";
        }

        Cursor cursor = myDataBase.rawQuery(query, null);
        results = new String[cursor.getCount()];

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            int index = 0;
            do {
                switch (language){
                    case "es":
                        results[index] = cursor.getInt(0) + " " + cursor.getString(1);
                        break;
                    case "en":
                        results[index] = cursor.getInt(0) + " " +  cursor.getString(2);
                        break;
                    case "por":
                        results[index] = cursor.getString(3);
                        break;
                    case "it":
                        results[index] = cursor.getString(4);
                        break;
                    default:
                        //do default
                        break;
                }
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

    private ArrayList<ParticleModel> getParticles(int[] id_particles, String language){
        boolean previousClause = false;
        ArrayList<ParticleModel> results = new ArrayList<>();

        String query = "select " + DBContract.Particles.TABLE_NAME + ".*, " + DBContract.Subjects.TABLE_NAME + "." + DBContract.Subjects.COLUMN_NAME_TEXT + "_" + language
                + "," + DBContract.Subjects.TABLE_NAME + "." + DBContract.Subjects.COLUMN_NAME_PRIORITY
                + "," + DBContract.Subjects.TABLE_NAME + "." + DBContract.Subjects.COLUMN_NAME_CLUSTER
                + " from " + DBContract.Particles.TABLE_NAME + "," + DBContract.Subjects.TABLE_NAME;

        if(id_particles != null){
            previousClause = true;
            query = query + " WHERE " + DBContract.Particles.TABLE_NAME + "." + DBContract.Particles.COLUMN_NAME_ID + " IN (" + id_particles[0];
            for(int i = 1; i < id_particles.length; i++){
                query = query + "," + id_particles[i];
            }
            query = query + ")";
        }

        if (previousClause) {
            query = query + " and ";
        }else{
            query = query + " where ";
        }

        query = query + DBContract.Subjects.TABLE_NAME + "." + DBContract.Subjects.COLUMN_NAME_ID + " = " + DBContract.Particles.TABLE_NAME + "." + DBContract.Particles.COLUMN_NAME_ID_SUBJECT;
        query = query + " order by " + DBContract.Subjects.TABLE_NAME + "." + DBContract.Subjects.COLUMN_NAME_CLUSTER + " ASC, "
                + DBContract.Subjects.TABLE_NAME + "." + DBContract.Subjects.COLUMN_NAME_PRIORITY + " ASC";

        System.out.println("Query " + query);

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                ParticleModel pm = new ParticleModel();
                pm.setId(cursor.getInt(0));
                pm.setLanguage(language);
                pm.setId_subject(cursor.getInt(5));
                pm.setSubjectText(cursor.getString(6));
                pm.setPriority(cursor.getInt(7));
                pm.setGroup(cursor.getInt(8));
                switch (language){
                    case "es":
                        pm.setText(cursor.getString(1));
                        break;
                    case "en":
                        pm.setText(cursor.getString(2));
                        break;
                    case "por":
                        pm.setText(cursor.getString(3));
                        break;
                    case "it":
                        pm.setText(cursor.getString(4));
                        break;
                    default:
                        //do default
                        break;
                }
                results.add(pm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

    /********************************************************************************************
     **************************     COUNTRIES AND CITIES       **********************************
     ********************************************************************************************/

    public ArrayList<CountryModel> getCountriesList(int[] id, String language){
        ArrayList<CountryModel> results = new ArrayList<>();

        String query = "SELECT * FROM " + DBContract.Countries.TABLE_NAME;

        if(id != null){
            query = query + " WHERE " + DBContract.Countries.COLUMN_NAME_ID + " IN (" + id[0];
            for(int i = 1; i < id.length; i++){
                query = query + "," + id[i];
            }
            query = query + ")";
        }

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                CountryModel cm = new CountryModel();
                cm.setId(cursor.getInt(0));
                cm.setLanguage(language);
                switch (language){
                    case "es":
                        cm.setCountry_name(cursor.getString(1));
                        break;
                    case "en":
                        cm.setCountry_name(cursor.getString(2));
                        break;
                    case "por":
                        cm.setCountry_name(cursor.getString(3));
                        break;
                    case "it":
                        cm.setCountry_name(cursor.getString(4));
                        break;
                    default:
                        //do default
                        break;
                }
                results.add(cm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

    /**
     * Returns the list of cities in a given country
     * @param id_countries ID of the country
     * @param language Current language selected in the app
     * @param sorted boolean 0- false the return will not be sorted 1- true the return will be sorted
     * @return an ArrayList with the cities from a given country
     */
    public ArrayList<CityModel> getCitiesList(int[] id_countries, int[] id_categories, String language, boolean sorted){
        String city_language_column = null;
        ArrayList<CityModel> results = new ArrayList<>();

        //Sets the column with the given language
        switch (language){
            case "es":
                city_language_column = DBContract.Cities.COLUMN_NAME_CITY_ES;
                break;
            case "en":
                city_language_column = DBContract.Cities.COLUMN_NAME_CITY_EN;
                break;
            case "por":
                city_language_column = DBContract.Cities.COLUMN_NAME_CITY_PT;
                break;
            case "it":
                city_language_column = DBContract.Cities.COLUMN_NAME_CITY_IT;
                break;
            default:
                //do default
                break;
        }

        String query = "SELECT DISTINCT " + DBContract.Cities.TABLE_NAME + "." + DBContract.Cities.COLUMN_NAME_ID +
                "," + DBContract.Cities.TABLE_NAME + "." + city_language_column +
                "," + DBContract.Cities.TABLE_NAME + "." + DBContract.Cities.COLUMN_NAME_ID_COUNTRY +
                " FROM " + DBContract.Cities.TABLE_NAME;

        boolean whereStatement = false;
        if(id_categories != null && id_categories[0] != 0){
            whereStatement = true;
            query = query + " INNER JOIN " + DBContract.Entities.TABLE_NAME + " ON " + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_CITY + " = " + DBContract.Cities.TABLE_NAME + "." + DBContract.Cities.COLUMN_NAME_ID +
                    " WHERE " + DBContract.Entities.TABLE_NAME + "." + DBContract.Entities.COLUMN_NAME_ID_CATEGORY + " IN (" + id_categories[0];
            for(int i = 1; i < id_categories.length; i++){
                query = query + "," + id_categories[i];
            }
            query = query + ")";
        }

        if(id_countries != null && id_countries[0] != 0){
            if(whereStatement) query = query + " AND ";
            else query = query + " WHERE ";

            query = query + DBContract.Cities.TABLE_NAME + "." + DBContract.Cities.COLUMN_NAME_ID_COUNTRY + " IN (" + id_countries[0];
            for(int i = 1; i < id_countries.length; i++){
                query = query + "," + id_countries[i];
            }
            query = query + ")";
        }

        if(sorted) query = query + " order by " + city_language_column + " asc";

        System.out.println("QUERY: " + query);

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                CityModel cm = new CityModel();
                cm.setId(cursor.getInt(0));
                cm.setCity_name(cursor.getString(1));
                cm.setId_country(cursor.getInt(2));
                cm.setLanguage(language);
                results.add(cm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

    public ArrayList<CityModel> getCitiesByID(int[] id_cities, String language){
        ArrayList<CityModel> results = new ArrayList<>();

        String query = "SELECT * FROM " + DBContract.Cities.TABLE_NAME;

        if(id_cities != null){
            query = query + " WHERE " + DBContract.Cities.COLUMN_NAME_ID + " IN (" + id_cities[0];
            for(int i = 1; i < id_cities.length; i++){
                query = query + "," + id_cities[i];
            }
            query = query + ")";
        }

         Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                CityModel cm = new CityModel();
                cm.setId(cursor.getInt(0));
                cm.setLanguage(language);
                cm.setId_country(cursor.getInt(5));
                switch (language){
                    case "es":
                        cm.setCity_name(cursor.getString(1));
                        break;
                    case "en":
                        cm.setCity_name(cursor.getString(2));
                        break;
                    case "por":
                        cm.setCity_name(cursor.getString(3));
                        break;
                    case "it":
                        cm.setCity_name(cursor.getString(4));
                        break;
                    default:
                        //do default
                        break;
                }
                results.add(cm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

}
