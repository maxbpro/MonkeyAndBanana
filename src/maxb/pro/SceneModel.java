package maxb.pro;

import java.util.ArrayList;

public class SceneModel
{
    private final int mBananasCount;
    private final long mDelay;
    private final Row_Game_Levels mLevelInfo;
    private final ArrayList<Row_Game_Actors> mActorsInfo ;

    private ArrayList<Row_User_Actors> activeted = null;
    private Row_User_Levels user_level = null;

    public SceneModel(ArrayList<Row_Game_Actors> mActorsInfo, int delay )
    {
        this.mDelay = delay;
        this.mActorsInfo = mActorsInfo;
        mLevelInfo = mActorsInfo.get(0).get_level();
        mBananasCount = mLevelInfo.get_bananas();
        activeted = new ArrayList<Row_User_Actors>();
        user_level = new Row_User_Levels(mLevelInfo.get_level(), mLevelInfo.get_mode());
    }

    public int getBananasCount()
    {
        return mBananasCount;
    }

    public long getDelay()
    {
        return mDelay;
    }

    public ArrayList<Row_User_Actors> getActivated()
    {
        return activeted;
    }

    public void addActivated(Row_User_Actors actor)
    {
        activeted.add(actor);
    }

    public Row_User_Levels getUser_level()
    {
        return user_level;
    }
}
