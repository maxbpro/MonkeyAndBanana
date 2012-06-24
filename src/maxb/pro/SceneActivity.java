package maxb.pro;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import maxb.pro.Actors.*;
import maxb.pro.DataBaseInteract.*;
import maxb.pro.Dialogs.LostDialog;
import maxb.pro.Dialogs.ResultDialog;
import maxb.pro.Dialogs.TouchMeDialog;
import maxb.pro.Specials.IndicatorRoute;
import maxb.pro.Views.FieldView;
import maxb.pro.Views.IndicatorView;

import java.util.ArrayList;
import java.util.Map;


public class SceneActivity extends Activity
{
    private static final int DELAY = 1;
    private Thread thread = null;
    private boolean isPauseThread = false;
    private int mMode = 0;
    private int mLevel = 0;
    private SceneModel mGameModel = null;
    private SceneView mGameView = null;
    private IndicatorRoute mRoute = null;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.scene);
        initVariablesForDataBase();
        Row_Game_Levels level = new Row_Game_Levels(0,0,0,0);
        ArrayList<IHasName> actors = getActorsInfoAndInitLevelInfo(level);
        mGameModel = new SceneModel(actors, level);
        mGameView = new SceneView(this, 10, mGameModel.getBananasCount(), actors);
        mRoute = new IndicatorRoute(this);

        initTimer();
        initBtnJump();
        initAndShowTouchMeDialog();
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



    // Database interact

    private void initVariablesForDataBase()
    {
        mMode = getIntent().getIntExtra("MODE", 0);
        mLevel = getIntent().getIntExtra("LEVEL", 0);
    }

    private ArrayList<IHasName> getActorsInfoAndInitLevelInfo(Row_Game_Levels level)
    {
        ArrayList<IHasName> actors = new ArrayList<IHasName>();

        GameDataBaseAdapter adapter = new GameDataBaseAdapter(this);
        adapter = adapter.open();
        ArrayList<Row_Game_Actors> rows_actors =
                adapter.getAllEntriesByLevelAndByMode(mLevel, mMode);
        adapter.close();

        for(Row_Game_Actors row_actor : rows_actors)
        {
            try
            {
                Object object = Class.forName(row_actor.get_name()).newInstance();
                if(row_actor.get_name().equals(FieldView.SNAKE)){
                    Snake instance = (Snake)object;
                    actors.add(instance);
                }
                else if (row_actor.get_name().equals(FieldView.TELEPORT)){
                    Teleport instance = (Teleport)object;
                    actors.add(instance);
                }
                else if(row_actor.get_name().equals(FieldView.LIME))
                {
                    Lime instance = (Lime)object;
                    actors.add(instance);
                }

            }
            catch (Exception ex){}
        }

        Row_Game_Levels l = rows_actors.get(0).get_level();
        level.set_bananas(l.get_bananas());
        level.set_id(l.get_id());
        level.set_level(l.get_level());
        level.set_mode(l.get_mode());
        return actors;
    }



    private void SaveResultToDB()
    {
        ArrayList<Row_User_Enemy> rows_enemies = new ArrayList<Row_User_Enemy>();
        Map<Enemy, Integer> enemies = mGameModel.getEnemiesMap();
        for (Enemy enemy : enemies.keySet())
        {
            rows_enemies.add(new Row_User_Enemy(
                    enemy.getClassName(), enemies.get(enemy), mGameModel.getUser_level()));
        }
        UserDataBaseAdapter adapter = new UserDataBaseAdapter(this);
        adapter.open();
        adapter.insertEntryLevel(mGameModel.getUser_level(), rows_enemies);
        adapter.close();
    }







    // Movements

    private void initBtnJump()
    {
        mGameView.getIndicator().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mGameView.getIndicator().switchSmile(IndicatorView.Smiles.SURPRISE);
                        mGameView.getIndicator().update();
                        break;
                    case MotionEvent.ACTION_UP:
                        MoveMonkeyAndCheck(mRoute.getRoute());
                        //UpdateUI();
                        break;
                }
                return true;
            }
        });
    }

    private void MoveMonkeyAndCheck(IndicatorRoute.Route route)
    {
        if(route != null)
        {
            IndicatorView.Smiles smile = mGameView.getField().moveMonkey(route);
            switch (smile)
            {
                case IN_LOVE:
                    mGameModel.getUser_level().Bananas_increment();
                    mGameView.getScene_text().setText("Banana has found!");
                    mGameView.txt_scene_text_scale();
                    mGameView.txt_bananas_scale();
                    break;
                case WINK:
                    mGameView.getIndicator().setEnable();
                    mGameView.getScene_text().setText("Box has found!");
                    mGameView.txt_scene_text_scale();
                    break;
                case ANRGY:
                    mGameView.getScene_text().setText("Take the Box!");
                    mGameView.txt_scene_text_scale();
                    break;
            }

            mGameView.getIndicator().switchSmile(smile);

            boolean isContinues  = true;
            if(mGameModel.getUser_level().get_scores()<0)
                isContinues = false;
            if(!isContinues)
                isLostLevel();
            else
                isFinishLevel();
        }
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

                            UpdateUI();
                            handler.postDelayed(this, DELAY);
                        }
                    }
                }, DELAY);
            }
        };

        thread = new Thread(null,RecurringTask);

    }


    private void UpdateUI()
    {
        mGameView.getIndicator().update();
        mGameView.getTxt_bananas().setText(mGameModel.getUser_level().get_bananas() + " / " + mGameModel.getBananasCount());
        Integer score = mGameModel.getUser_level().get_scores();
        mGameView.getTxt_score().setText(score.toString());
        mGameModel.getUser_level().set_time(mGameModel.getUser_level().get_time_like_integer()
                + DELAY);
        String time = mGameModel.getUser_level().get_time_like_string();
        mGameView.getTxt_time().setText(time);

    }


    // windows

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

    private void isLostLevel()
    {
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

}

