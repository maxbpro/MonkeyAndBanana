package maxb.pro.app.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.view.animation.*;
import android.view.*;
import android.widget.TextView;
import maxb.pro.app.Actors.Enemy;
import maxb.pro.app.Adapters.EnemiesAdapter;
import maxb.pro.app.DataBaseInteract.Row_User_Levels;
import maxb.pro.app.R;
import maxb.pro.app.Views.myButton;

import java.util.ArrayList;

public class ResultDialog extends Dialog
{
    private Context mContext = null;
    private ArrayList<Enemy> enemies = null;
    private Row_User_Levels level = null;

    public ResultDialog(Context context, int theme)
    {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        setContentView(R.layout.result_loading);
        initAnimationLoadingFinish();


    }

    public void transformDialog(Row_User_Levels level, ArrayList<Enemy> enemies)
    {
        this.enemies = enemies;
        this.level = level;
        setContentView(R.layout.result_loading_finish);
        initAnimationLoading();
        initButtons();
        initText();
        initGallery();
    }

    private void initButtons()
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
                        dismiss();
                        break;
                }
                return true;
            }
        });
    }

    private void initText()
    {
        TextView txt_time = (TextView)findViewById(R.id.result_text_time);
        TextView txt_scores = (TextView)findViewById(R.id.result_text_score);
        txt_time.setText(level.get_time_like_string());
        txt_scores.setText(String.valueOf(level.get_scores()));
    }

    private void initGallery()
    {
        Gallery gallery = (Gallery)findViewById(R.id.result_gallery);
        EnemiesAdapter adapter = new EnemiesAdapter(mContext, enemies);
        gallery.setAdapter(adapter);
    }

    private void initAnimationLoading()
    {
        ImageView image = (ImageView)findViewById(R.id.result_image);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_in);
        image.startAnimation(animation);
    }

    private void initAnimationLoadingFinish()
    {
        Animation animation  = AnimationUtils.loadAnimation(mContext, R.anim.scale_in_repeat);
        ImageView image = (ImageView)findViewById(R.id.result_loading_image);
        image.startAnimation(animation);

    }


}
