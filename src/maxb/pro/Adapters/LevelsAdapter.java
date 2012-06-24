package maxb.pro.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import maxb.pro.DataBaseInteract.UserDataBaseAdapter;
import maxb.pro.Views.LevelView;

import java.util.ArrayList;

public class LevelsAdapter extends BaseAdapter
{
    private Context mContext = null;
    private static final int LEVEL_SIZE = 150;
    private final int mode;
    private final ArrayList<Integer> list_open_levels;

    public LevelsAdapter(Context context, int mode)
    {
        mContext = context;
        this.mode = mode;
        UserDataBaseAdapter dbAdapter = new UserDataBaseAdapter(context);
        dbAdapter.open();
        list_open_levels = dbAdapter.getAllLevelsName(mode);
        dbAdapter.close();
    }

    @Override
    public int getCount()
    {
        return 40;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        int level = i+1;
        LevelView.State state = LevelView.State.CLOSE;
        if(list_open_levels.contains(level))
           state =  LevelView.State.FINISH;
        else
           if (level == list_open_levels.size()+1)
               state = LevelView.State.OPEN;
        LevelView view = new LevelView(mContext, state, mode, level);
        int s = dpToPx(LEVEL_SIZE);
        view.setLayoutParams(new GridView.LayoutParams(s, s));
        return view;
    }

    private int dpToPx(int dp)
    {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
