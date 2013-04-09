package maxb.pro.app.Actors;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import maxb.pro.app.R;

public class Banana extends Thing
{


    @Override
    public Drawable getImage(Resources resources)
    {
         return resources.getDrawable(R.drawable.banana);
    }

}
