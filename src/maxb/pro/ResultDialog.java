package maxb.pro;


import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.animation.*;

import java.util.ArrayList;

public class ResultDialog extends Dialog
{
    private Context mContext = null;
    public enum Mode {FROM_GAME, FROM_MENU}
    private Mode mMode = Mode.FROM_GAME;
    public enum Result {REFRESH, BACK, NEXT_LEVEL}
    private Result mResult = Result.REFRESH;
    private ArrayList<Actor> actors = null;

    public ResultDialog(Context context, int theme, Mode mMode, ArrayList<Actor> actors )
    {
        super(context, theme);
        this.mContext = context;
        this.mMode = mMode;
        this.actors = actors;
        switch (mMode)
        {
            case FROM_GAME:
                setContentView(R.layout.result_from_scene);
                initFROM_GAME_widgets();
                break;
            case FROM_MENU:
                setContentView(R.layout.result_from_levels);
                initFROM_MENU_widgets();
                break;
        }
        initImageAnimation();
    }

    private void initFROM_GAME_widgets()
    {
        final myButton btn_next_level = (myButton)findViewById(R.id.result_next_level);
        final myButton btn_refresh = (myButton)findViewById(R.id.result_refresh);
        btn_next_level.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_next_level.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        mResult = Result.NEXT_LEVEL;
                        dismiss();
                        break;
                }
                return true;
            }
        });
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

    }

    private void initFROM_MENU_widgets()
    {
        final myButton btn_back = (myButton)findViewById(R.id.result_back);
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

    private void initImageAnimation()
    {
        ImageView image = (ImageView)findViewById(R.id.result_image);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_in);
        image.startAnimation(animation);
    }
}
