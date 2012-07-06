package maxb.pro;

import android.app.Activity;
import android.content.Context;
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
    private UserDataBaseAdapter dbAdapter = null;
    private LevelsAdapter adapter = null;
    private GridView grid = null;
    private int mode = -1;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.levels);

        mContext = this;
        grid = (GridView)findViewById(R.id.levelsGrid);
        mode = getIntent().getIntExtra("mode",0);
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
                        btn_back.stopAnimation();
                        finish();
                        break;
                }
                return true;
            }
        });

        new AsyncManager().execute();

    }

    class AsyncManager extends AsyncTask<Void, Void, ArrayList<Integer>>
    {
        private LoadingDialog dialog = null;

        @Override
        protected void onPreExecute()
        {
            dialog = new LoadingDialog(mContext, R.style.DialogTheme);
            dialog.show();
            dbAdapter = new UserDataBaseAdapter(mContext);
        }

        @Override
        protected ArrayList<Integer> doInBackground(Void... integers)
        {
            dbAdapter.open();
            ArrayList<Integer> list_open_levels = dbAdapter.getAllLevelsName(mode);
            dbAdapter.close();
            return  list_open_levels;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> list_open_levels)
        {
            super.onPostExecute(list_open_levels);
            adapter = new LevelsAdapter(mContext, mode, list_open_levels);
            grid.setAdapter(adapter);
            dialog.dismiss();
        }
    }
}