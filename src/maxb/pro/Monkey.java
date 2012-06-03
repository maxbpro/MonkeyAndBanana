package maxb.pro;


import android.content.Context;
import android.widget.ImageView;

public class Monkey extends ImageView
{
    private Place place = null;

    public Monkey(Context context, int X, int Y)
    {
        super(context);
        place = new Place(X, Y);
        setImageDrawable(getResources().getDrawable(R.drawable.monkey));
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
