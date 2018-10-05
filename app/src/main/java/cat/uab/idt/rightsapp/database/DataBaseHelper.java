package cat.uab.idt.rightsapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private String DB_PATH = "/data/data/cat.uab.idt.rightsapp/databases/";

    private static String DB_NAME = "rightsapp_v1_utf16.db";

    private SQLiteDatabase myDataBase = null;

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
        System.out.println("Database NOT exists: " + dbFile.getAbsolutePath());
        if(dbFile.exists()){
            System.out.println("Database NOT exists: " + dbFile.getAbsolutePath());
            myDataBase = myContext.openOrCreateDatabase(this.DB_NAME, myContext.MODE_PRIVATE, null);
            myDataBase.close();

            CopyDataBase(dbFile);

        }else{
            System.out.println("Database exists");
        }
    }

    /**
     * Copies the database from the assets folder to the default location in the app
     * @throws IOException
     */
    private void CopyDataBase(File dbFile) {
        System.out.println("INIT COPY DATABASE: " + dbFile.getAbsolutePath());

        try {
            //open local db as input stream
            System.out.println("Database1: " + myContext.databaseList()[0] + " " + myContext.getAssets().open(DB_NAME).toString());
            InputStream myInput = myContext.getAssets().open(DB_NAME);


            System.out.println("FINISH COPY DATABASE: " + dbFile.getAbsolutePath());
            //Open the empty db as outputstream
            OutputStream myOutput = new FileOutputStream(dbFile);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            do {
                length = myInput.read(buffer);
                myOutput.write(buffer, 0, length);
            } while (length > 0);

            //close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch (IOException e){
            System.out.println("Error I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Opens the database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        //open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
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

    public ArrayList<AnswersModel> getAnswers(int id_question){
        AnswersModel answersModel = new AnswersModel();
        ArrayList<AnswersModel> result = new ArrayList<>();
        //String query = new String("SELECT * FROM " + RightsAppDBContract.Answers.TABLE_NAME + " WHERE " + RightsAppDBContract.Answers.COLUMN_NAME_ID + " = 1");
        String query = new String("SELECT name FROM sqlite_master WHERE type='table'");
        System.out.println("Query1: " + query);

        Cursor cursor = myDataBase.rawQuery(query, null);

        //System.out.println("Column count: " + cursor.getColumnCount());

        if(cursor.moveToFirst()){
            // Loop through cursor results if the query has rows
            do {
                System.out.println("Table1: " + cursor.getString(0));
                //answersModel.initialize();
                //answersModel.setId(cursor.getInt(0));
                //answersModel.setText_es(cursor.getString(1));
                //answersModel.setText_en(cursor.getString(2));
                //answersModel.setText_fr(cursor.getString(3));
                //answersModel.setText_it(cursor.getString(4));
                //answersModel.setNext_question_id(cursor.getInt(5));

                //result.add(answersModel);
            } while (cursor.moveToNext());

        }

        return result;
    }

}
