package maxb.pro.Views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.FrameLayout;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import maxb.pro.Actors.*;
import maxb.pro.Adapters.LevelsAdapter;
import maxb.pro.DataBaseInteract.*;
import maxb.pro.Dialogs.LoadingDialog;
import maxb.pro.Dialogs.ResultDialog;
import maxb.pro.R;
import maxb.pro.SceneActivity;

import java.util.ArrayList;

public class LevelView extends FrameLayout
{

    public enum State{OPEN, CLOSE, FINISH}
    private State mState = State.CLOSE;
    private static final String MODE = "MODE";
    private static final String LEVEL = "LEVEL";
    private Context mContext = null;
    private int mMode = 0;
    private int mLevel = 0;

    public LevelView(Context context, State mState, int mode, int level)
    {
        super(context);
        this.mState = mState;
        this.mContext = context;
        this.mMode = mode;
        this.mLevel = level;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.level_view, this, true);
        initView();
    }

    private void initView()
    {
        TextView title = (TextView)findViewById(R.id.level_text);
        ImageView image = (ImageView)findViewById(R.id.level_image);
        title.setText(String.valueOf(mLevel));
        switch (mState)
        {
            case OPEN:
                image.setImageResource(R.drawable.open);
                initOpenModeLevel();
                break;
            case CLOSE:
                image.setImageResource(R.drawable.close);
                initCloseModeLevel();
                break;
            case FINISH:
                image.setImageResource(R.drawable.happy);
                initFinishModeLevel();
                break;
        }
    }

    private void initFinishModeLevel()
    {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AsyncLoading().execute();
                return true;
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SceneActivity.class);
                intent.putExtra(MODE, mMode);
                intent.putExtra(LEVEL, mLevel);
                ((Activity)mContext).startActivityForResult(intent, 0);
            }
        });

    }

    private void initCloseModeLevel()
    {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {} });
    }

    private void initOpenModeLevel()
    {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SceneActivity.class);
                intent.putExtra(MODE, mMode);
                intent.putExtra(LEVEL, mLevel);
                ((Activity)mContext).startActivityForResult(intent, 0);
            }
        });
    }



    class AsyncLoading extends AsyncTask<Void, Void, Void>
    {
        private ResultDialog dialog = null;
        private Row_User_Levels level = null;
        private ArrayList<Enemy> enemies = null;

        @Override
        protected void onPreExecute()
        {
            dialog = new ResultDialog(mContext, R.style.DialogTheme);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            level = new Row_User_Levels(0,0,0,0,0);
            ArrayList<Row_User_Enemy> row_enemies = getEnemiesInfoAndInitLevelInfo(level);
            //transform in classes
            return null;
        }

        @Override
        protected void onPostExecute(Void vd)
        {
            super.onPostExecute(null);
            dialog.transformDialog(level, enemies);
        }

        private ArrayList<Row_User_Enemy> getEnemiesInfoAndInitLevelInfo(Row_User_Levels level)
        {
            UserDataBaseAdapter adapter = new UserDataBaseAdapter(mContext);
            adapter = adapter.open();
            ArrayList<Row_User_Enemy> enemies =
                    adapter.getAllEntriesByLevelAndByMode(mLevel, mMode);
            adapter.close();


            Row_User_Levels l = enemies.get(0).get_level();
            level.set_bananas(l.get_bananas());
            level.set_time(l.get_time_like_integer());
            level.set_level((l.get_level()));
            level.set_mode(l.get_mode());
            level.set_scores(l.get_scores());

            return enemies;
        }
    }
}
