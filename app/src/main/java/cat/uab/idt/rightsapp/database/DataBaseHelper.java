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

    public ArrayList<AnswersModel> getAnswersForQuestion(int id_question){
        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id_question);
        String[] projection = new String[1];
        projection[0] = new String(DBContract.Questions_Answers.COLUMN_NAME_ID_ANSWER);

        Cursor cursor = myDataBase.query(
                DBContract.Questions_Answers.TABLE_NAME,
                projection,
                DBContract.Questions_Answers.COLUMN_NAME_ID_QUESTION,
                selectionArgs,
                null,
                null,
                null
        );//TODO: Comprobar si se hacen bien las consultas

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

    public ArrayList<AnswersModel> getAnswers(String[] id_answers) {
        AnswersModel answersModel = new AnswersModel();
        ArrayList<AnswersModel> result = new ArrayList<>();

        Cursor cursor = myDataBase.query(
                DBContract.Answers.TABLE_NAME,
                null,
                DBContract.Answers.COLUMN_NAME_ID,
                id_answers,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                answersModel.initialize();
                answersModel.setId(cursor.getInt(0));
                answersModel.setText_es(cursor.getString(1));
                answersModel.setText_en(cursor.getString(2));
                answersModel.setText_fr(cursor.getString(3));
                answersModel.setText_it(cursor.getString(4));
                answersModel.setNext_question_id(cursor.getInt(5));

                result.add(answersModel);
            } while (cursor.moveToNext());

        }
        cursor.close();

        return result;
    }


    public AnswersModel getAnswer(int id_answer) {
        AnswersModel answersModel = new AnswersModel();
        String query = "SELECT * FROM " + DBContract.Answers.TABLE_NAME + " WHERE "
                + DBContract.Answers.COLUMN_NAME_ID + " = " + id_answer;

        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                answersModel.initialize();
                answersModel.setId(cursor.getInt(0));
                answersModel.setText_es(cursor.getString(1));
                answersModel.setText_en(cursor.getString(2));
                answersModel.setText_fr(cursor.getString(3));
                answersModel.setText_it(cursor.getString(4));
                answersModel.setNext_question_id(cursor.getInt(5));
            } while (cursor.moveToNext());

        }
        cursor.close();

        return answersModel;

    }

/*    public ArrayList<AnswersModel> getAnswersForQuestion(int id_question){
        AnswersModel answersModel = new AnswersModel();
        ArrayList<AnswersModel> result = new ArrayList<>();
        String query = "SELECT * FROM " + DBContract.Answers.TABLE_NAME;
        //String query = "SELECT * FROM " + DBContract.Answers.TABLE_NAME + " WHERE " + DBContract.Answers.COLUMN_NAME_ID + " = 1";

        Cursor cursor = myDataBase.rawQuery(query, null);

        System.out.println("Column count: " + cursor.getColumnCount());

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                System.out.println("Table1: " + cursor.getString(1));
                answersModel.initialize();
                answersModel.setId(cursor.getInt(0));
                answersModel.setText_es(cursor.getString(1));
                answersModel.setText_en(cursor.getString(2));
                answersModel.setText_fr(cursor.getString(3));
                answersModel.setText_it(cursor.getString(4));
                answersModel.setNext_question_id(cursor.getInt(5));

                result.add(answersModel);
            } while (cursor.moveToNext());

        }
        cursor.close();

        return result;
    }*/

}
