package maxb.pro;


import android.content.Context;

public class Box extends Actor
{

    public Box(Context context, Place place)
    {
        super(context, place);
        setImageDrawable(getResources().getDrawable(R.drawable.box));

    }



}
