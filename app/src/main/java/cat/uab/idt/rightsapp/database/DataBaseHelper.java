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
    private static String DB_NAME = "rightsapp_v4_utf16.db";

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

    // QUESTIONS & ANSWERS

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

    public ArrayList<CityModel> getCitiesList(int[] id_country, String language){
        ArrayList<CityModel> results = new ArrayList<>();

        String query = "SELECT * FROM " + DBContract.Cities.TABLE_NAME;

        if(id_country != null){
            query = query + " WHERE " + DBContract.Cities.COLUMN_NAME_ID_COUNTRY + " IN (" + id_country[0];
            for(int i = 1; i < id_country.length; i++){
                query = query + "," + id_country[i];
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

    public ArrayList<EntityModel> getEntitiesList(int[] id_cities, int[] id_countries, int[] id_categories, String language){
        boolean previous_clause = false;
        ArrayList<EntityModel> results = new ArrayList<>();

        String query = "SELECT * FROM " + DBContract.Entities.TABLE_NAME;

        if(id_cities != null){
            query = query + " WHERE " + DBContract.Entities.COLUMN_NAME_ID_CITY + " IN (" + id_cities[0];
            for(int i = 1; i < id_cities.length; i++){
                query = query + "," + id_cities[i];
            }
            query = query + ")";
            previous_clause = true;
        }

        if(id_countries != null){
            if(previous_clause) query = query + " AND ";
            query = query + " WHERE " + DBContract.Entities.COLUMN_NAME_ID_COUNTRY + " IN (" + id_countries[0];
            for(int i = 1; i < id_countries.length; i++){
                query = query + "," + id_countries[i];
            }
            query = query + ")";
            previous_clause = true;
        }

        if(id_categories != null){
            if(previous_clause) query = query + " AND ";
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
                EntityModel em = new EntityModel();
                em.setId(cursor.getInt(0));
                em.setLanguage(language);
                em.setEntity_name(cursor.getString(1));
                em.setAddress(cursor.getString(6));
                em.setLatitude(cursor.getDouble(7));
                em.setLongitude(cursor.getDouble(8));
                em.setId_city(cursor.getInt(9));
                em.setId_country(cursor.getInt(10));
                em.setId_category(cursor.getInt(11));
                switch (language){
                    case "es":
                        em.setEntity_description(cursor.getString(2));
                        break;
                    case "en":
                        em.setEntity_description(cursor.getString(3));
                        break;
                    case "por":
                        em.setEntity_description(cursor.getString(4));
                        break;
                    case "it":
                        em.setEntity_description(cursor.getString(5));
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
     *************************     PARTICLES AND TAGS       *************************************
     ********************************************************************************************/

    public ArrayList<ParticleModel> getParticle(int[] id_particles){
        ArrayList<ParticleModel> result = new ArrayList<>();

        return result;
    }
    /**
     * Returns the tags associated to a given particle
     * @param id_particle the id of the particle
     * @return a String array with the name of the tags associated with the given particle
     */
    public String[] getTagsFromParticle(int id_particle){
        String query = "SELECT " + DBContract.Particles_Tags.COLUMN_NAME_ID_TAG
                + " FROM " + DBContract.Particles_Tags.TABLE_NAME
                + " WHERE " +  DBContract.Particles_Tags.COLUMN_NAME_ID_PARTICLE + " = "
                + String.valueOf(id_particle);

        Cursor cursor = myDataBase.rawQuery(query, null);

        int i = 0;
        String[] id_tags = new String[cursor.getCount()];

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                id_tags[i] = String.valueOf(cursor.getInt(0));
                i++;

            } while (cursor.moveToNext());
        }
        cursor.close();

        return this.getTags(id_tags);
    }

    /**
     * Returns the tags given by the parameter
     * @param id_tags an array with the id of the tags to be returned
     * @return a String array with the name of the tags given by parameter
     */
    public String[] getTags(String[] id_tags){
        String query = "SELECT " + DBContract.Tags.COLUMN_NAME_TAG + " FROM " + DBContract.Tags.TABLE_NAME
                + " WHERE " +  DBContract.Tags.COLUMN_NAME_ID + " IN (" + id_tags[0];

        for(int i = 1; i < id_tags.length; i++){
            query = query + "," + id_tags[i];
        }
        query = query + ")";

        Cursor cursor = myDataBase.rawQuery(query, null);

        String[] tags = new String[cursor.getCount()];

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            int i = 0;
            do {
                tags[i] = cursor.getString(0);
                i++;
            } while (cursor.moveToNext());

        }
        cursor.close();

        return tags;
    }

}
