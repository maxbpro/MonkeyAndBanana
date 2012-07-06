package maxb.pro.DataBaseInteract;


import android.content.ContentValues;
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

    public ArrayList<Row_User_Enemy> getAllEntriesByLevelAndByMode(int level, int mode)
    {
        Cursor cursor_level = db.query(LEVELS_TABLE, new String[]{KEY_ID,LEVEL_COL, MODE_COL, BANANAS_COL, TIME_COL, SCORES_COL},
                LEVEL_COL + "=" + level + " and " + MODE_COL + "=" + mode,null, null, null, null,"1" );
        cursor_level.moveToFirst();
        Row_User_Levels row_level = new Row_User_Levels(
                cursor_level.getInt(LEVEL_COL_INT),
                cursor_level.getInt(MODE_COL_INT),
                cursor_level.getInt(BANANAS_COL_INT),
                cursor_level.getInt(TIME_COL_INT),
                cursor_level.getInt(SCORES_COL_INT)
                );
        ArrayList<Row_User_Enemy> listActors = new ArrayList<Row_User_Enemy>();
        Cursor cursor_actors = db.query(ACTORS_TABLE, new String[]{NAME_COL, NUMBER_COL, LEVEL_ID_COL},
                LEVEL_ID_COL + "=" + row_level.get_level(),null, null, null, null );
        cursor_actors.moveToFirst();
        while (!cursor_actors.isAfterLast())
        {
            Row_User_Enemy row = new Row_User_Enemy(cursor_actors.getString(NAME_COL_INT),
                    cursor_actors.getInt(NUMBER_COL_INT),
                    row_level);
            listActors.add(row);
            cursor_actors.moveToNext();
        }


        return listActors;
    }


    public void insertEntryLevel(Row_User_Levels level, ArrayList<Row_User_Enemy> actors)
    {
        removingOldLevelInfo(level);

        int level_id = level.get_level();
        ContentValues row_level = new ContentValues();
        row_level.put(LEVEL_COL, level.get_level());
        row_level.put(MODE_COL, level.get_mode());
        row_level.put(TIME_COL, level.get_time_like_integer());
        row_level.put(SCORES_COL, level.get_scores());
        row_level.put(BANANAS_COL, level.get_bananas());
        db.insert(LEVELS_TABLE, null, row_level);
        if(actors != null)
        {
           for(Row_User_Enemy actor : actors)
           {
               ContentValues row_actor = new ContentValues();
               row_actor.put(NAME_COL, actor.get_name());
               row_actor.put(NUMBER_COL, actor.get_number());
               row_actor.put(LEVEL_ID_COL, level_id );
               db.insert(ACTORS_TABLE, null, row_actor);
           }
        }
    }

    public ArrayList<Integer> getAllLevelsName(int mode)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Cursor cursor_levels = db.query(LEVELS_TABLE, new String[]{LEVEL_COL},"mode=" + mode, null, null,null,null);
        cursor_levels.moveToFirst();
        while (!cursor_levels.isAfterLast())
        {
            list.add(cursor_levels.getInt(0));
            cursor_levels.moveToNext();
        }
        return list;


    }

    private void removingOldLevelInfo(Row_User_Levels level)
    {
        db.delete(LEVELS_TABLE, LEVEL_COL + "=" + level.get_level(), null);
        db.delete(ACTORS_TABLE, LEVEL_ID_COL + "=" + level.get_level(), null);
    }
}
