package maxb.pro;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


public class SettingsActivity extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.settings);
        final myButton btn_back = (myButton)findViewById(R.id.settings_back);
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
}