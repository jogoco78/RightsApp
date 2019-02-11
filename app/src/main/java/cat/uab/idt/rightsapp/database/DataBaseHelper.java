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

import cat.uab.idt.rightsapp.models.AnswerModel;
import cat.uab.idt.rightsapp.models.QuestionModel;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static String DB_NAME = "rightsapp_v1_utf16.db";

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

    //Public helper methods to access and get content from the database

    // QUESTIONS & ANSWERS

    /**
     * Returns the question given by parameter
     * @param id_questions an array with the id of the questions to be returned
     * @return an ArrayList of QuestionModel given by parameter
     */
    public ArrayList<QuestionModel> getQuestions(int[] id_questions){
        ArrayList<QuestionModel> result = new ArrayList<>();
        String query = "SELECT * FROM " + DBContract.Questions.TABLE_NAME
                + " WHERE " +  DBContract.Questions.COLUMN_NAME_ID + " IN (" + id_questions[0];

        for(int i = 1; i < id_questions.length; i++){
            query = query + "," + id_questions[i];
        }
        query = query + ")";

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                QuestionModel questionModel = new QuestionModel();
                questionModel.initialize();
                questionModel.setId(cursor.getInt(0));
                questionModel.setText_es(cursor.getString(1));
                questionModel.setText_en(cursor.getString(2));
                questionModel.setText_fr(cursor.getString(3));
                questionModel.setText_it(cursor.getString(4));

                result.add(questionModel);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }
    /**
     * Returns the answers from a specified question by its id into the database
     * @param id_question the id of the question
     * @return an ArrayList with all the answers related to the given question
     */
    public ArrayList<AnswerModel> getAnswersForQuestion(int id_question){
        String query = "SELECT " + DBContract.Questions_Answers.COLUMN_NAME_ID_ANSWER
                + " FROM " + DBContract.Questions_Answers.TABLE_NAME
                + " WHERE " +  DBContract.Questions_Answers.COLUMN_NAME_ID_QUESTION + " = "
                + String.valueOf(id_question);

        Cursor cursor = myDataBase.rawQuery(query, null);

        int i = 0;
        String[] id_answers = new String[cursor.getCount()];

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                id_answers[i] = String.valueOf(cursor.getInt(0));
                i++;

            } while (cursor.moveToNext());
        }
        cursor.close();

        return this.getAnswers(id_answers);
    }

    /**
     * Returns the answers information into the database
     * @param id_answers the ids of the answers into the database
     * @return an ArrayList with all the information of the answers given by their id
     */
    public ArrayList<AnswerModel> getAnswers(String[] id_answers) {
        ArrayList<AnswerModel> result = new ArrayList<>();
        String query = "SELECT * FROM " + DBContract.Answers.TABLE_NAME
                + " WHERE " +  DBContract.Answers.COLUMN_NAME_ID + " IN (" + id_answers[0];

        for(int i = 1; i < id_answers.length; i++){
            query = query + "," + id_answers[i];
        }
        query = query + ")";

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                AnswerModel answerModel = new AnswerModel();
                answerModel.initialize();
                answerModel.setId(cursor.getInt(0));
                answerModel.setText_es(cursor.getString(1));
                answerModel.setText_en(cursor.getString(2));
                answerModel.setText_fr(cursor.getString(3));
                answerModel.setText_it(cursor.getString(4));
                answerModel.setNext_question_id(cursor.getInt(5));

                result.add(answerModel);
            } while (cursor.moveToNext());

        }
        cursor.close();

        return result;
    }

    //PARTICLES & TAGS

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
