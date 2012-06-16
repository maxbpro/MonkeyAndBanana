package maxb.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;


public class LevelsActivity extends Activity
{
    private Context mContext = null;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.levels);
        GridView grid = (GridView)findViewById(R.id.levelsGrid);
        final myButton btn_back = (myButton)findViewById(R.id.levels_back);
        final int mode = getIntent().getIntExtra("mode",0);
        LevelsAdapter adapter = new LevelsAdapter(this, mode);
        grid.setAdapter(adapter);
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



    }
}