package maxb.pro;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;


public class LevelsActivity extends Activity
{
    private enum Mode{ONE, TWO,BONUS}
    private Mode mMode = Mode.ONE;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.levels);
        GridView grid = (GridView)findViewById(R.id.levelsGrid);
        final myButton btn_back = (myButton)findViewById(R.id.levels_back);
        LevelsAdapter adapter = new LevelsAdapter(this);
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

        int m = getIntent().getIntExtra("mode",0);
        mMode = Mode.values()[m];
        switch (mMode)
        {
            case ONE:
                break;
            case TWO:
                break;
            case BONUS:
                break;
        }
    }
}