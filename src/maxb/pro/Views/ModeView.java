package maxb.pro.Views;


import android.content.Context;
import android.widget.FrameLayout;
import android.view.*;
import android.widget.TextView;
import maxb.pro.R;

public class ModeView extends FrameLayout
{

    public ModeView(Context context, String titleText)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mode_view, this, true);
        TextView title = (TextView)findViewById(R.id.mode_text);
        title.setText(titleText);
    }


}
