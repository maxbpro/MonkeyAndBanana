package maxb.pro;


import android.content.Context;
import android.widget.ImageView;

abstract public class Actor  extends ImageView
{
    private Place place = null;

    public Actor(Context context, Place place)
    {
        super(context);
        this.place = place;
    }

    public Place getPlace()
    {
        return place;
    }

    public int getPlaceCol()
    {
        return place.getCol();
    }

    public int getPlaceRow()
    {
        return place.getRow();
    }

    public void setPlace(int row, int col)
    {
        place.setRow(row);
        place.setCol(col);
    }
}
