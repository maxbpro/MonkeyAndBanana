package maxb.pro.app.Actors;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import maxb.pro.app.R;

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
