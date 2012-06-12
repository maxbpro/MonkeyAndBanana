package maxb.pro;


import android.content.Context;
import android.widget.FrameLayout;
import android.view.*;
import android.widget.TextView;

public class LevelView extends FrameLayout
{

    public LevelView(Context context, String titleText)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.level_view, this, true);
        TextView title = (TextView)findViewById(R.id.level_text);
        title.setText(titleText);
    }


}
