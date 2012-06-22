package maxb.pro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class SceneActivity extends Activity
{
    private Thread thread = null;
    private boolean isPauseThread = false;
    private int mMode = 0;
    private int mLevel = 0;
    private SceneModel mGameModel = null;
    private SceneView mGameView = null;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.scene);
        initVariablesForDataBase();
        ArrayList<Row_Game_Actors> actors = getActorsInfo();
        ArrayList<IActivate> activeted = getActivated(actors);
        mGameModel = new SceneModel(actors, 1);
        mGameView = new SceneView(this, 10, mGameModel.getBananasCount(), activeted);

        initTimer();
        initBtnJump();
        initAndShowTouchMeDialog();
    }


    private void initVariablesForDataBase()
    {
        mMode = getIntent().getIntExtra("MODE", 0);
        mLevel = getIntent().getIntExtra("LEVEL", 0);
    }

    private ArrayList<Row_Game_Actors> getActorsInfo()
    {
        GameDataBaseAdapter adapter = new GameDataBaseAdapter(this);
        adapter = adapter.open();
        ArrayList<Row_Game_Actors> actors =
                adapter.getAllEntriesByLevelAndByMode(mLevel, mMode);
        adapter.close();
        return actors;
    }

    private ArrayList<IActivate> getActivated(ArrayList<Row_Game_Actors> actors)
    {
        ArrayList<IActivate> activated = new ArrayList<IActivate>();
        for(Row_Game_Actors actor : actors)
        {
            try
            {
               Object object = Class.forName(actor.get_name()).newInstance();
               if(actor.get_name().equals(FieldView.SNAKE)){
                   Snake instance = (Snake)object;
                   activated.add(instance);
               }
                else if (actor.get_name().equals(FieldView.TELEPORT)){
                   Teleport instance = (Teleport)object;
                   activated.add(instance);
               }
            }
            catch (Exception ex)
            {

            }
        }
        return activated;
    }

    public void activate()
    {
        thread.start();
    }

    public void pause()
    {
         isPauseThread = true;
    }

    public void start()
    {
        isPauseThread = false;
    }

    private void isLostLevel()
    {
        // exit from the level like fail
        final LostDialog dialog = new LostDialog(this, R.style.DialogTheme);
        pause();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //if(dialog.getResult()==LostDialog.Result.REFRESH)

                }
        });
        dialog.show();

    }

    private void isFinishLevel()
    {
        if (mGameModel.getUser_level().get_bananas() == mGameModel.getBananasCount())
        {
            pause();
            final ResultDialog dialog = new ResultDialog(this, R.style.DialogTheme,
                    ResultDialog.Mode.FROM_GAME, null);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    switch (dialog.getResult())
                    {
                        case  REFRESH:
                            break;
                        case NEXT_LEVEL:
                            break;
                    }
                    SaveResultToDB();
                }
            });
            dialog.show();
        }

    }

    public void CurrentBananasCountIncrement()
    {
        mGameModel.getUser_level().Bananas_increment();
    }

    public int getScore()
    {
        return mGameModel.getUser_level().get_scores();
    }

    private void SaveResultToDB()
    {
        UserDataBaseAdapter adapter = new UserDataBaseAdapter(this);
        adapter.open();
        adapter.insertEntryLevel(mGameModel.getUser_level(), mGameModel.getActivated());
        adapter.close();
    }

    private void MoveMonkeyAndCheck(IndicatorRoute.Route route)
    {
        mGameView.getField().moveMonkey(route);
        boolean isContinues  = true;
        if(mGameModel.getUser_level().get_scores()<0)
            isContinues = false;

        if(!isContinues)
            isLostLevel();
        else
            isFinishLevel();

    }

    private void UpdateUI()
    {
        mGameView.getIndicator().updateIndicator();
        mGameView.getTxt_bananas().setText(mGameModel.getUser_level().get_bananas() + " / " + mGameModel.getBananasCount());
        Integer score = mGameModel.getUser_level().get_scores();
        mGameView.getTxt_score().setText(score.toString());
    }

    private void initAndShowTouchMeDialog()
    {
        TouchMeDialog dialog = new TouchMeDialog(this, R.style.DialogTheme);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                activate();
            }
        });
        dialog.show();
    }

    private void initBtnJump()
    {
        mGameView.getIndicator().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        IndicatorRoute.Route route = IndicatorRoute.getRoute();
                        MoveMonkeyAndCheck(route);
                        UpdateUI();
                        break;
                }
                return false;
            }
        });
    }

    private void initTimer()
    {
        final Handler handler = new Handler();
        Runnable RecurringTask = new Runnable()
        {
            public void run()
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isPauseThread == false)
                        {
                            mGameModel.getUser_level().set_time(mGameModel.getUser_level().get_time_like_integer()
                                    + mGameModel.getDelay());
                            String time = mGameModel.getUser_level().get_time_like_string();
                            mGameView.getTxt_time().setText(time);
                            handler.postDelayed(this, mGameModel.getDelay());
                        }
                    }
                }, mGameModel.getDelay());
            }
        };

        thread = new Thread(null,RecurringTask);

    }
}

