package maxb.pro.app.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;
import maxb.pro.app.R;
import android.view.animation.*;

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
public class LoadingDialog extends Dialog
{

    public LoadingDialog(Context context, int styleDef)
    {
        super(context, styleDef);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_levels);
        ImageView image = (ImageView)findViewById(R.id.loading_image);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_in_repeat);
        image.startAnimation(animation);
    }
}
