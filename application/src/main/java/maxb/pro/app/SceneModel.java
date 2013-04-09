package maxb.pro.app;

import maxb.pro.app.Actors.Enemy;
import maxb.pro.app.Actors.IActivate;
import maxb.pro.app.Actors.IHasName;
import maxb.pro.app.DataBaseInteract.Row_Game_Levels;
import maxb.pro.app.DataBaseInteract.Row_User_Levels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SceneModel
{
    private final int mBananasCount; // need count
    private final Row_Game_Levels mLevelInfo;
    private final ArrayList<IHasName> mActorsInfo ;
    private ArrayList<IActivate> activeted = null;
    private ArrayList<Enemy> enemies = null;

    private Row_User_Levels user_level = null;

    public SceneModel(ArrayList<IHasName> mActorsInfo, Row_Game_Levels mLevelInfo )
    {
        this.mActorsInfo = mActorsInfo;
        this.mLevelInfo = mLevelInfo;
        this.mBananasCount = mLevelInfo.get_bananas();
        this.activeted = new ArrayList<IActivate>();
        this.enemies = new ArrayList<Enemy>();
        this.user_level = new Row_User_Levels(mLevelInfo.get_level(), mLevelInfo.get_mode());
    }

    public int getBananasCount()
    {
        return mBananasCount;
    }


    public ArrayList<IActivate> getActivated()
    {
        return activeted;
    }

    public Map<Enemy, Integer> getEnemiesMap()
    {
        Map<Enemy, Integer> map = new LinkedHashMap<Enemy, Integer>();
        for(Enemy enemy : enemies)
        {
            int counter = map.get(enemy);
            map.put(enemy, counter==0 ? 1 : counter+1);
        }
        return map;
    }

    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    public void addActivated(IActivate actor)
    {
        activeted.add(actor);
    }

    public void addEnemy(Enemy enemy)
    {
        enemies.add(enemy);
    }

    public Row_User_Levels getUser_level()
    {
        return user_level;
    }
}
