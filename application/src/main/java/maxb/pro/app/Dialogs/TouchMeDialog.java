package maxb.pro.app.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import maxb.pro.app.R;

public class TouchMeDialog extends Dialog
{
    private TextView txt = null;
    private ImageView image = null;
    private Context mContext = null;

    public TouchMeDialog(Context context, int style)
    {
        super(context, style);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.touch_me);
        mContext  = context;
        txt = (TextView)findViewById(R.id.touch_me_txt);
        image = (ImageView)findViewById(R.id.touch_me_image);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                return true;
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_in_repeat);
        image.startAnimation(animation);
    }

    public void transform()
    {
        String text =  mContext.getResources().getString(R.string.touch_me);
        txt.setText(text);
        image.clearAnimation();
    }
}
