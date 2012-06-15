package maxb.pro;



import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class BoxSingleton extends Actor implements IActivate
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


    @Override
    public Boolean activate(ScoreSingleton score)
    {
         return true;
    }
}
