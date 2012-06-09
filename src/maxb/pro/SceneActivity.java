package maxb.pro;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class SceneActivity extends Activity
{
    private Handler handler = null;
    private long DELAY = 100;
    private FieldView field = null;
    private IndicatorView indicator = null;
    private static final int THICKNESS = 10;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene);
        initPanel();
        initField();
        initIndicator();

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                start();
            }
        });
        handler = new Handler();
    }

    private void initPanel()
    {
        LinearLayout panel = (LinearLayout)findViewById(R.id.panel);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        params.setMargins(THICKNESS/2,THICKNESS,THICKNESS,THICKNESS);
        panel.setLayoutParams(params);
    }


    private void initField()
    {
        field = (FieldView)findViewById(R.id.field);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(field.getSize(), field.getSize());
        params.setMargins(THICKNESS,THICKNESS,THICKNESS/2,THICKNESS);
        field.setLayoutParams(params);
    }

    private void initIndicator()
    {
        indicator = (IndicatorView)findViewById(R.id.indicator);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screen_width = dm.widthPixels;
        int size = screen_width - field.getSize() - 30;
        if(size>dm.heightPixels/2)
            size = dm.heightPixels/2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(0,THICKNESS,0,0);
        indicator.setLayoutParams(params);
    }

    private void start()
    {
        Runnable RecurringTask = new Runnable()
        {
            public void run()
            {
                field.moveMonkey();
                indicator.updateIndicator();
                handler.postDelayed(this, DELAY);
            }
        };
        handler.postDelayed(RecurringTask, DELAY);
    }
}

