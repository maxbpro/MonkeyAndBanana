package maxb.pro;


import android.content.Context;
import android.widget.ImageView;

public class Banana extends ImageView
{
    private Place place = null;

    public Banana(Context context, Place place)
    {
        super(context);
        this.place = place;
        setImageDrawable(getResources().getDrawable(R.drawable.banana));
    }

    public Place getPlace()
    {
        return place;
    }

    public void setPlaceX(int X)
    {
        place.setX(X);
    }

    public void setPlaceY(int Y)
    {
        place.setX(Y);
    }

    public void setPlace(int X, int Y)
    {
        place.setX(X);
        place.setY(Y);
    }
}
