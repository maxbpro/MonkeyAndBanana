package maxb.pro;


import android.content.Context;

public class Banana extends Actor
{


    public Banana(Context context, Place place)
    {
        super(context, place);
        setImageDrawable(getResources().getDrawable(R.drawable.banana));
    }


}
