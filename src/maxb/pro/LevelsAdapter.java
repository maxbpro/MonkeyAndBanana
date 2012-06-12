package maxb.pro;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class LevelsAdapter extends BaseAdapter
{
    private Context mContext = null;


    public LevelsAdapter(Context context)
    {
        mContext = context;
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
            if (convertView == null)
            {
                view = new LevelView(mContext, String.valueOf(i));
                //int w = GridView.LayoutParams.WRAP_CONTENT;
                //int h = GridView.LayoutParams.WRAP_CONTENT;
                //view.setLayoutParams(new GridView.LayoutParams(w, h));
                view.setPadding(5, 5, 5, 5);
            }
            else
            {
                view= (LevelView) convertView;
            }

            return view;

    }
}
