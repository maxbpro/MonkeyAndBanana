package maxb.pro;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class MonkeySingleton extends Actor
{
    private static MonkeySingleton instance = new MonkeySingleton();

    private MonkeySingleton(){}

    public static MonkeySingleton getInstance()
    {
        return instance;
    }



    @Override
    public Drawable getImage(Resources resources)
    {
        return resources.getDrawable(R.drawable.monkey);
    }

}
