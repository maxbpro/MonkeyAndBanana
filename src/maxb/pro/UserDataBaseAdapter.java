package maxb.pro;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDataBaseAdapter
{
    private static final String DB_NAME = "UserDB";
    private static final String LEVELS_TABLE = "Levels_user";
    private static final String ACTORS_TABLE = "Actors_user";

    private static final String KEY_ID = "_id";
    private static final int KEY_ID_INT = 0;

    private static final String LEVEL_COL = "level";
    private static final int LEVEL_COL_INT = 1;
    private static final String MODE_COL = "mode";
    private static final int MODE_COL_INT = 2;
    private static final String BANANAS_COL = "bananas";
    private static final int BANANAS_COL_INT = 3;
    private static final String TIME_COL = "time";
    private static final int TIME_COL_INT = 4;
    private static final String SCORES_COL = "scores";
    private static final int SCORES_COL_INT = 5;

    private static final String NAME_COL = "name";
    private static final int NAME_COL_INT = 1;
    private static final String NUMBER_COL = "number";
    private static final int NUMBER_COL_INT = 2;
    private static final String LEVEL_ID_COL = "level_id";
    private static final int LEVEL_ID_COL_INT = 3;

    private SQLiteDatabase db = null;
    private Context mContext = null;
    private DataBaseHelper dbHelper = null;

    public UserDataBaseAdapter(Context context)
    {
        this.mContext = context;
        dbHelper = new DataBaseHelper(context, DB_NAME);
    }

    public UserDataBaseAdapter open() throws SQLException
    {
        try
        {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close()
    {
        db.close();
    }

    public Cursor getAllEntries()
    {
        return db.query(DB_NAME, new String[]{KEY_ID, LEVEL_COL, MODE_COL, BANANAS_COL},
                null, null, null, null, null);
    }

    public ArrayList<Row_User_Actors> getAllEntriesByLevelAndByMode(int level, int mode)
    {
        Cursor cursor_level = db.query(LEVELS_TABLE, new String[]{KEY_ID, LEVEL_COL, MODE_COL, BANANAS_COL},
                LEVEL_COL + "=" + level + " and " + MODE_COL + "=" + mode,null, null, null, null,"1" );
        cursor_level.moveToFirst();
        Row_User_Levels row_level = new Row_User_Levels(cursor_level.getInt(KEY_ID_INT),
                cursor_level.getInt(LEVEL_COL_INT),
                cursor_level.getInt(MODE_COL_INT),
                cursor_level.getInt(BANANAS_COL_INT),
                cursor_level.getString(TIME_COL_INT),
                cursor_level.getInt(SCORES_COL_INT)
                );
        ArrayList<Row_User_Actors> listActors = new ArrayList<Row_User_Actors>();
        Cursor cursor_actors = db.query(ACTORS_TABLE, new String[]{KEY_ID, NAME_COL, NUMBER_COL, LEVEL_ID_COL},
                LEVEL_ID_COL + "=" + row_level.get_id(),null, null, null, null );
        cursor_actors.moveToFirst();
        while (!cursor_actors.isAfterLast())
        {
            Row_User_Actors row = new Row_User_Actors(cursor_actors.getInt(KEY_ID_INT),
                    cursor_actors.getString(NAME_COL_INT),
                    cursor_actors.getInt(NUMBER_COL_INT),
                    row_level);
            listActors.add(row);
            cursor_actors.moveToNext();
        }


        return listActors;
    }

    public void removeEntryFromLevelsTable(int index)
    {
        db.delete(LEVELS_TABLE, KEY_ID + "=" + index, null);
        // TODO deleting in ActorsTable by _id
        db.delete(ACTORS_TABLE, LEVEL_ID_COL + "=" + index, null);
    }


}
