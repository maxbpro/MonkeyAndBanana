package maxb.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
    private Context mContext = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        mContext = this;
        final myButton btn_forward = (myButton)findViewById(R.id.btn_forward);
        final myButton btn_settings = (myButton)findViewById(R.id.btn_settings);

        btn_forward.getImageView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_forward.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_forward.stopAnimation();
                        startActivity(new Intent(mContext, ModesActivity.class));
                        break;
                }
                return true;
            }
        });

        btn_settings.getImageView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_settings.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_settings.stopAnimation();
                        startActivity(new Intent(mContext, SettingsActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}