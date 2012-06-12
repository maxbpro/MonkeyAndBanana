package maxb.pro;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ModesAdapter extends BaseAdapter
{
    private Context mContext = null;


    public ModesAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public int getCount()
    {
        return 3;
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
    public View getView(int i, View oldView, ViewGroup viewGroup)
    {
        ModeView view = new ModeView(mContext, String.valueOf(i));
        return view;
    }
}
