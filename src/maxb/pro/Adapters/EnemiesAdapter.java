package maxb.pro.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import maxb.pro.Actors.Enemy;

import java.util.ArrayList;

/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class EnemiesAdapter extends BaseAdapter
{
    private ArrayList<Enemy> enemies = null;
    private Context mContext = null;

    public EnemiesAdapter(Context context,ArrayList<Enemy> enemies)
    {
        this.enemies = enemies;
        mContext = context;
    }

    @Override
    public int getCount()
    {
        return enemies.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View old_view, ViewGroup viewGroup)
    {
        ImageView view = new ImageView(mContext);
        Gallery.LayoutParams params = new Gallery.LayoutParams(dpToPx(50), dpToPx(50));
        view.setLayoutParams(params);
        view.setImageDrawable(enemies.get(i).getImage(mContext.getResources()));
        return view;
    }

    private int dpToPx(int dp)
    {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}
