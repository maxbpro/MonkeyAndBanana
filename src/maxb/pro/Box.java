package maxb.pro;



import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class Box extends Actor
{


    @Override
    public Drawable getImage(Resources resources)
    {
        return resources.getDrawable(R.drawable.box);
    }


}
