package maxb.pro.app.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import maxb.pro.app.Views.ModeView;

public class ModesAdapter extends BaseAdapter
{
    private Context mContext = null;
    private static final int MODE_WIDTH = 300;
    private static final int MODE_HEIGHT = 250;


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
        int mode = i+1;
        ModeView view = new ModeView(mContext, String.valueOf(mode));
        int h = dpToPx(MODE_HEIGHT);
        int w = dpToPx(MODE_WIDTH);
        Gallery.LayoutParams params = new Gallery.LayoutParams(w,h);
        view.setLayoutParams(params);
        view.setPadding(30,0,30,0);
        return view;
    }

    private int dpToPx(int dp)
    {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
