package maxb.pro.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.*;
import android.widget.TextView;
import android.view.animation.*;
import maxb.pro.app.Dialogs.AttentionDialog;
import maxb.pro.app.Dialogs.ResultDialog;
import maxb.pro.app.Views.myButton;

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
        initButtons();
    }

    private void initButtons()
    {
        final myButton btn_forward = (myButton)findViewById(R.id.btn_forward);
        final myButton btn_settings = (myButton)findViewById(R.id.btn_settings);
        startAnimation();
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

    @Override
    public void onBackPressed()
    {

        final AttentionDialog dialog = new AttentionDialog(this, R.style.DialogTheme,
                getResources().getString(R.string.question_about_exit));

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(dialog.getResult() == AttentionDialog.Results.AGREE)
                    finish();
            }
        });

        dialog.show();

    }



    private void startAnimation()
    {
        final TextView txt_title = (TextView)findViewById(R.id.main_game_title);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        txt_title.startAnimation(animation);
    }
}