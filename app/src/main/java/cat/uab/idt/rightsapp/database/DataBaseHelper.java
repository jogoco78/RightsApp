package cat.uab.idt.rightsapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/cat.uab.idt.rightsapp/databases";

    private static String DB_NAME = "rightsapp_v1_utf16.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

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
        boolean dbExists = checkDataBase();

        if(dbExists){
            //do nothing - the database already exists
        }else{
            //creates an empty database in the default path
            this.getReadableDatabase();

            try{
                CopyDataBase();
            }catch(IOException e){
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exists to avoid re-copying the file
     * @return true if it exists, false if it does not
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does not exist yet
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies the database from the assets folder to the default location in the app
     * @throws IOException
     */
    private void CopyDataBase() throws IOException{
        //open local db as input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //path to the created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as outputstream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
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



}
