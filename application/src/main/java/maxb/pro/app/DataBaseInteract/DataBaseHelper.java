package maxb.pro.app.DataBaseInteract;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/maxb.pro/databases/";
    private String db_name = null;
    private SQLiteDatabase myDataBase = null;
    private Context myContext = null;


    public DataBaseHelper(Context context, String db_name) {

        super(context, db_name, null, 1);
        this.myContext = context;
        this.db_name = db_name;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }
        else
        {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            //this.getReadableDatabase();
            this.getReadableDatabase();
            this.close();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = DB_PATH + db_name;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        }
        catch(SQLiteException e)
        {
            //database does't exist yet.
        }

        if(checkDB != null)
        {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(db_name);
        // Path to the just created empty db
        String outFileName = DB_PATH + db_name;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException
    {
        String myPath = DB_PATH + db_name;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Invoke for creating UserDataBase
        String query = "CREATE TABLE Levels_user ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "level INTEGER NOT NULL, mode INTEGER NOT NULL, time TEXT NOT NULL, " +
                "scores INTEGER NOT NULL, bananas INTEGER NOT NULL);";
        db.execSQL(query);
        query = "CREATE TABLE Actors_user (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, number INTEGER NOT NULL, level_id INTEGER NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
         // Invoke for UserDataBase
        Log.w("DateBaseAdapter", "Upgrading from version " + oldVersion +
        " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Levels_user");
        db.execSQL("DROP TABLE IF EXISTS Actors_user");
        onCreate(db);
    }


}