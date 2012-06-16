package maxb.pro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class SceneActivity extends Activity
{
    private Handler handler = null;
    private Runnable RecurringTask = null;
    private long DELAY = 100;
    private Thread thread = null;
    private FieldView field = null;
    private IndicatorView indicator = null;
    private static final int THICKNESS = 10;
    // for interact with the database
    private int mMode = 0;
    private int mLevel = 0;
    private boolean isPauseThread = false;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.scene);
        initVariablesForDataBase();
        initField();
        initPanel();
        initIndicator();

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                activate();
            }
        });
        handler = new Handler();
        RecurringTask = new Runnable()
        {
            public void run()
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isPauseThread == false)
                        {
                           field.moveMonkey();
                           indicator.updateIndicator();
                           handler.postDelayed(this, DELAY);
                        }
                        else
                        {

                        }
                    }
                }, DELAY);
            }
        };

        thread = new Thread(null,RecurringTask);
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
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(new Point(4,6));
        list.add(new Point(5,3));
        list.add(new Point(7,8));
        field.initField(10, list);
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

    private void initVariablesForDataBase()
    {
        mMode = getIntent().getIntExtra("MODE", 0);
        mLevel = getIntent().getIntExtra("LEVEL", 0);
    }

    /*private void start()
    {
        RecurringTask = new Runnable()
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
     */

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


}

