package maxb.pro.app.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import maxb.pro.app.R;
import maxb.pro.app.Views.myButton;

public class LostDialog extends Dialog
{
    public enum Result {REFRESH, BACK}
    private Result mResult = Result.BACK;

    public LostDialog(Context context, int theme)
    {
       super(context, theme);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.lost);
       final myButton btn_refresh = (myButton)findViewById(R.id.lost_refresh);
       final myButton btn_back = (myButton)findViewById(R.id.lost_back);
       btn_refresh.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               switch(motionEvent.getAction())
               {
                   case MotionEvent.ACTION_DOWN:
                       btn_refresh.startAnimation();
                       break;
                   case MotionEvent.ACTION_UP:
                       mResult = Result.REFRESH;
                       dismiss();
                       break;
               }
               return true;
           }
       });
        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_back.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        mResult = Result.BACK;
                        dismiss();
                        break;
                }
                return true;
            }
        });

    }

    public Result getResult()
    {
        return mResult;
    }
}
