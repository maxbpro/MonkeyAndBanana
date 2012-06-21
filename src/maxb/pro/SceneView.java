package maxb.pro;


import android.graphics.Point;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SceneView
{
    private static final int THICKNESS = 10;
    private int panel_width = 0;
    private SceneActivity scene = null;
    private final FieldView field;
    private final IndicatorView indicator;
    private final TextView txt_bananas;
    private final TextView txt_score;
    private final TextView txt_time;

    public SceneView(SceneActivity scene, int size, int bananasCount,
                     ArrayList<IActivate> actors)
    {
        this.scene = scene;
        field = (FieldView)scene.findViewById(R.id.field);
        indicator = (IndicatorView)scene.findViewById(R.id.indicator);
        txt_bananas = (TextView)scene.findViewById(R.id.scene_bananas);
        txt_score = (TextView)scene.findViewById(R.id.scene_scores);
        txt_time = (TextView)scene.findViewById(R.id.scene_time);
        field.initField(size, bananasCount, actors);
        initField();
        initIndicator();
        initPanel();
        initTexts(bananasCount);
    }

    private void initField()
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(field.getSize(), field.getSize());
        params.setMargins(THICKNESS,THICKNESS,THICKNESS/2,THICKNESS);
        field.setLayoutParams(params);
    }


    private void initPanel()
    {
        RelativeLayout panel = (RelativeLayout)scene.findViewById(R.id.panel);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(panel_width, LinearLayout.LayoutParams.FILL_PARENT);
        params.setMargins(THICKNESS/2,THICKNESS,THICKNESS,THICKNESS);
        panel.setLayoutParams(params);
        panel.getBackground().setAlpha(80);
    }



    private void initIndicator()
    {
        DisplayMetrics dm = new DisplayMetrics();
        scene.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screen_width = dm.widthPixels;
        panel_width = screen_width - field.getSize() - 30;
        if(panel_width > dm.heightPixels/2)
            panel_width = dm.heightPixels/2;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(panel_width, panel_width);
        params.setMargins(0,200,0,0);
        indicator.setLayoutParams(params);
        //indicator.getBackground().setAlpha(50);
    }

    private void initTexts(int count)
    {
        txt_bananas.setText("0 / " + count);
    }

    public FieldView getField()
    {
        return field;
    }

    public IndicatorView getIndicator()
    {
        return indicator;
    }

    public TextView getTxt_bananas()
    {
        return txt_bananas;
    }

    public TextView getTxt_score()
    {
        return  txt_score;
    }

    public TextView getTxt_time()
    {
        return txt_time;
    }

}
