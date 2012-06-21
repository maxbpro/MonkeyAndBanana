package maxb.pro;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class TouchMeDialog extends Dialog
{
    public TouchMeDialog(Context context, int style)
    {
        super(context, style);
        setContentView(R.layout.touch_me);
        FrameLayout frame = (FrameLayout)findViewById(R.id.touch_me_frame);
        frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                return true;
            }
        });
    }
}
