package maxb.pro.app;

import android.graphics.Color;
import android.view.View;
import android.view.animation.*;
import android.util.DisplayMetrics;
import android.widget.*;
import maxb.pro.app.Actors.IActivate;
import maxb.pro.app.Actors.IHasName;
import maxb.pro.app.Views.FieldView;
import maxb.pro.app.Views.IndicatorView;

import java.util.ArrayList;

public class SceneView
{
    private static final int THICKNESS = 5;
    private int panel_width = 0;
    private SceneActivity scene = null;
    private final FieldView field;
    private final IndicatorView indicator;
    private final TextView txt_bananas;
    private final TextView txt_score;
    private final TextView txt_time;
    private final ImageView pic_bananas;
    private final ImageView pic_scores;
    private final TextView scene_text;
    private final Gallery gallery_activated;
    private Animation animation = null;

    public SceneView(SceneActivity scene, int size, int bananasCount,
                     ArrayList<IHasName> actors)
    {
        this.scene = scene;
        field = (FieldView)scene.findViewById(R.id.field);
        indicator = (IndicatorView)scene.findViewById(R.id.indicator);
        txt_bananas = (TextView)scene.findViewById(R.id.scene_bananas);
        txt_score = (TextView)scene.findViewById(R.id.scene_scores);
        txt_time = (TextView)scene.findViewById(R.id.scene_time);
        pic_bananas = (ImageView)scene.findViewById(R.id.scene_bananas_pic);
        pic_scores = (ImageView)scene.findViewById(R.id.scene_scores_pic);
        scene_text = (TextView)scene.findViewById(R.id.scene_text);
        gallery_activated = (Gallery)scene.findViewById(R.id.scene_gallery_activated);
        field.initField(size, bananasCount, actors);
        initField();
        initIndicator();
        initPanel();
        initScroll();
        initTexts(bananasCount);
        initAnimation();
    }

    private void initField()
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(field.getSize(), field.getSize());
        params.setMargins(THICKNESS,THICKNESS,THICKNESS/2,THICKNESS);
        field.setLayoutParams(params);
    }

    private void initIndicator()
    {
        DisplayMetrics dm = new DisplayMetrics();
        scene.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screen_width = dm.widthPixels;
        panel_width = screen_width - field.getSize() - 15;
        if(panel_width > field.getSize()/2)
            panel_width = field.getSize()/2;
        indicator.setWidth(panel_width);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(panel_width, panel_width);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        indicator.setLayoutParams(params);
        //indicator.getBackground().setAlpha(50);
    }

    private void initPanel()
    {
        RelativeLayout panel = (RelativeLayout)scene.findViewById(R.id.panel);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(panel_width, LinearLayout.LayoutParams.FILL_PARENT);
        params.setMargins(THICKNESS/2,THICKNESS,THICKNESS,THICKNESS);
        panel.setLayoutParams(params);
        panel.getBackground().setAlpha(80);
    }

    private void initScroll()
    {
        ScrollView scroll = (ScrollView)scene.findViewById(R.id.scene_scroll);
        int height = field.getSize() - panel_width;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(panel_width, height);
        scroll.setLayoutParams(params);
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

    public Gallery getGallery_activated()
    {
        return gallery_activated;
    }

    public TextView getScene_text()
    {
        return scene_text;
    }



    public void txt_bananas_scale()
    {
        pic_bananas.startAnimation(animation);
        txt_bananas.startAnimation(animation);

    }

    public void txt_scores_scale()
    {
        pic_scores.startAnimation(animation);
        txt_score.startAnimation(animation);
    }

    public void txt_scene_text_scale()
    {
        scene_text.startAnimation(animation);
    }

    public void setText(String text)
    {
        scene_text.setText(text);
    }

    private void initAnimation()
    {
        animation = AnimationUtils.loadAnimation(scene, R.anim.scale_in);
    }

    private void initTexts(int count)
    {
        txt_bananas.setText("0 / " + count);
    }

}
