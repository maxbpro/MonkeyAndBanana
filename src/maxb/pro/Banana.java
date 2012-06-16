package maxb.pro;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class Banana extends Actor implements IActivate
{


    @Override
    public Drawable getImage(Resources resources)
    {
         return resources.getDrawable(R.drawable.banana);
    }

    @Override
    public boolean activate(ScoreSingleton score)
    {
        return true;
    }
}
