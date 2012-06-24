package maxb.pro.Actors;



import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import maxb.pro.R;

public class BoxSingleton extends Actor
{
    private static BoxSingleton instance = new BoxSingleton();
    private BoxSingleton(){}
    public static BoxSingleton getInstance()
    {
        return instance;
    }

    @Override
    public Drawable getImage(Resources resources)
    {
        return resources.getDrawable(R.drawable.box);
    }

}
