package maxb.pro;


import android.content.Context;
import android.widget.ImageView;

public class Box extends ImageView
{
    private Place place = null;

    public Box(Context context, Place place)
    {
        super(context);
        setImageDrawable(getResources().getDrawable(R.drawable.box));
        this.place = place;
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
