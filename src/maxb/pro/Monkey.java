package maxb.pro;


import android.content.Context;

public class Monkey extends Actor
{

    public Monkey(Context context, Place place)
    {
        super(context, place);
        setImageDrawable(getResources().getDrawable(R.drawable.monkey));
    }




}
