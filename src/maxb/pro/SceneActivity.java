package maxb.pro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.*;
import maxb.pro.Actors.*;
import maxb.pro.Adapters.LevelsAdapter;
import maxb.pro.DataBaseInteract.*;
import maxb.pro.Dialogs.*;
import maxb.pro.Specials.IndicatorRoute;
import maxb.pro.Views.FieldView;
import maxb.pro.Views.IndicatorView;

import java.util.ArrayList;
import java.util.Map;


public class SceneActivity extends Activity
{
    private Context mContext = null;
    private static final int DELAY = 1;
    private Thread thread = null;
    private boolean isPauseThread = false;
    private int mMode = 0;
    private int mLevel = 0;
    private SceneModel mGameModel = null;
    private SceneView mGameView = null;
    private IndicatorRoute mRoute = null;
    private  Row_Game_Levels level = null;
    private ArrayList<IHasName> actors = null;
    private Intent resultIntent = null;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.scene);
        resultIntent = new Intent();
        new AsyncLoadingLevel().execute();
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
                String name = row_actor.get_name();
                name = name.toLowerCase();
                char first = name.charAt(0);
                name = name.replace(first, Character.toUpperCase(first));
                Object object = Class.forName("maxb.pro.Actors." + name).newInstance();

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
            catch (Exception ex){
                String t = ex.getMessage();
            }
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
            mGameView.getIndicator().cleatScore();
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
                case CRY:
                    mGameView.getScene_text().setText("ENEMY!");
                    mGameView.txt_scene_text_scale();
                    mGameView.getIndicator().setScore(mGameView.getField().getDeltaScores());
                    mGameModel.getUser_level().add_to_scores(mGameView.getField().getDeltaScores());
                    mGameView.txt_scores_scale();
                    //mGameView.getIndicator().
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



    private void isLostLevel()
    {
        final LostDialog dialog = new LostDialog(this, R.style.DialogTheme);
        pause();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(dialog.getResult()==LostDialog.Result.REFRESH)
                {
                    resultIntent.putExtra("RESULT", mGameModel.getUser_level().get_level());
                    setResult(0, resultIntent);
                }

            }
        });
        dialog.show();

    }

    private void isFinishLevel()
    {
        if (mGameModel.getUser_level().get_bananas() == mGameModel.getBananasCount())
        {
            // updateUI  ????
            pause();
            final SuccessDialog dialog = new SuccessDialog(this, R.style.DialogTheme,
                    mGameModel.getUser_level(), mGameModel.getEnemies());
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    switch (dialog.getResult())
                    {
                        case  REFRESH:
                            resultIntent.putExtra("RESULT", mGameModel.getUser_level().get_level());
                            break;
                        case NEXT_LEVEL:
                            // check last level
                            resultIntent.putExtra("RESULT", mGameModel.getUser_level().get_level()+1);
                            break;
                    }
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
            dialog.show();
            SaveResultToDB();
            resultIntent.putExtra("RESULT", -1);
            setResult(RESULT_OK, resultIntent);
        }
    }

    class AsyncLoadingLevel extends AsyncTask<Void, Void, Void>
    {
        private TouchMeDialog dialog = null;

        @Override
        protected void onPreExecute()
        {
            dialog = new TouchMeDialog(mContext, R.style.DialogTheme);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            initVariablesForDataBase();
            level = new Row_Game_Levels(0,0,0,0);
            actors = getActorsInfoAndInitLevelInfo(level);
            return null;
        }

        @Override
        protected void onPostExecute(Void voids)
        {
            super.onPostExecute(null);
            transformDialog();
            mGameModel = new SceneModel(actors, level);
            mGameView = new SceneView((SceneActivity)mContext, 10, mGameModel.getBananasCount(), actors);
            mRoute = new IndicatorRoute(mContext);
            initTimer();
            initBtnJump();
        }

        private void transformDialog()
        {

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    activate();
                }
            });
            dialog.transform();
        }
    }

}

