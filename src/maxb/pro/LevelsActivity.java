package maxb.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import maxb.pro.Adapters.LevelsAdapter;
import maxb.pro.DataBaseInteract.UserDataBaseAdapter;
import maxb.pro.Dialogs.LoadingDialog;
import maxb.pro.Views.myButton;

import java.util.ArrayList;


public class LevelsActivity extends Activity
{
    private Context mContext = null;
    private int mode = -1;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.levels);
        mContext = this;
        mode = getIntent().getIntExtra("mode",0);
        initButtons();
        new AsyncManager().execute();
    }

    private void initButtons()
    {
        final myButton btn_back = (myButton)findViewById(R.id.levels_back);
        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_back.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        //btn_back.stopAnimation();
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode)
        {
            case 0:
                if(resultCode==Activity.RESULT_OK)
                {
                    int value = intent.getIntExtra("RESULT", -2);
                    switch (value)
                    {
                        case -2:
                            // nothing
                            break;
                        case -1:
                            // update
                            onCreate(null);
                            break;
                        default:
                            // new intent
                            Intent activity = new Intent(mContext, SceneActivity.class);
                            activity.putExtra("MODE", mode);
                            activity.putExtra("LEVEL", value);
                            ((Activity)mContext).startActivityForResult(activity, 0);
                            break;
                    }
                }
                else
                {
                    // nothing
                }
                break;
        }
    }

    class AsyncManager extends AsyncTask<Void, Void, ArrayList<Integer>>
    {
        private LoadingDialog dialog = null;

        @Override
        protected void onPreExecute()
        {
            dialog = new LoadingDialog(mContext, R.style.DialogTheme);
            dialog.show();
        }

        @Override
        protected ArrayList<Integer> doInBackground(Void... integers)
        {
            UserDataBaseAdapter dbAdapter = new UserDataBaseAdapter(mContext);
            dbAdapter.open();
            ArrayList<Integer> list_open_levels = dbAdapter.getAllLevelsName(mode);
            dbAdapter.close();
            return  list_open_levels;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> list_open_levels)
        {
            super.onPostExecute(list_open_levels);
            GridView grid = (GridView)findViewById(R.id.levelsGrid);
            LevelsAdapter adapter = new LevelsAdapter(mContext, mode, list_open_levels);
            grid.setAdapter(adapter);
            dialog.dismiss();
        }
    }
}