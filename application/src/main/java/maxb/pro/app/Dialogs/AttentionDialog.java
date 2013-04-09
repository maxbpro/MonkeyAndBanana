package maxb.pro.app.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.*;
import maxb.pro.app.R;

public class AttentionDialog extends Dialog
{
    private Animation animation = null;
    public enum Results {AGREE, CANCEL}
    private Results mResult = Results.AGREE;

    public AttentionDialog(Context context, int theme, String text)
    {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Dialog dialog = this;
        animation = AnimationUtils.loadAnimation(context, R.anim.scale_in);
        setContentView(R.layout.attention);
        ((TextView)findViewById(R.id.attention_text)).setText(text);
        final Button btn_agree = (Button)findViewById(R.id.attention_agree);
        final Button btn_cancel = (Button)findViewById(R.id.attention_cancel);
        btn_agree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_agree.startAnimation(animation);
                        break;
                    case MotionEvent.ACTION_UP:
                        mResult = Results.AGREE;
                        dialog.dismiss();
                        break;
                }
                return true;
            }

        });
        btn_cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_cancel.startAnimation(animation);
                        break;
                    case MotionEvent.ACTION_UP:
                        mResult = Results.CANCEL;
                        dialog.dismiss();
                        break;
                }
                return true;
            }
        });

    }

    public Results getResult()
    {
        return mResult;
    }

}
