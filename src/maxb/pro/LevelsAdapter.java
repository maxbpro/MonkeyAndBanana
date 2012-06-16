package maxb.pro;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class LevelsAdapter extends BaseAdapter
{
    private Context mContext = null;
    private static final int LEVEL_SIZE = 150;
    private int mode = 0;

    public LevelsAdapter(Context context, int mode)
    {
        mContext = context;
        this.mode = mode;
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
           LevelView view = null;
           view = new LevelView(mContext, LevelView.State.FINISH, mode, i);
           int s = dpToPx(LEVEL_SIZE);
           view.setLayoutParams(new GridView.LayoutParams(s, s));
           //view.setPadding(5, 5, 5, 5);
           return view;
    }

    private int dpToPx(int dp)
    {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
