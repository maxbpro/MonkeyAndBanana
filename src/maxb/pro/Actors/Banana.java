package maxb.pro.Actors;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import maxb.pro.R;

public class Banana extends Thing
{


    @Override
    public Drawable getImage(Resources resources)
    {
         return resources.getDrawable(R.drawable.banana);
    }

}
