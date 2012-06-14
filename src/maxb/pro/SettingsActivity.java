package maxb.pro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;


public class SettingsActivity extends Activity
{
    private static final String MUSIC = "MUSIC";
    private static final String SOUNDS = "SOUNDS";
    private static final String VIBRO = "VIBRO";

    private CheckBox music_check = null;
    private CheckBox sounds_check = null;
    private CheckBox vibro_check = null;

    private SharedPreferences preferences = null;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.settings);
        music_check = (CheckBox)findViewById(R.id.music_check);
        sounds_check = (CheckBox)findViewById(R.id.sounds_check);
        vibro_check = (CheckBox)findViewById(R.id.vibro_check);
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
                        savePreferences();
                        finish();
                        break;
                }
                return true;
            }
        });
        initPreferences();
        updateElementState();
    }

    private void initPreferences()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void updateElementState()
    {
        boolean music_on = preferences.getBoolean(MUSIC, true);
        boolean sounds_on = preferences.getBoolean(SOUNDS, true);
        boolean vibro_on = preferences.getBoolean(VIBRO, true);
        music_check.setChecked(music_on);
        sounds_check.setChecked(sounds_on);
        vibro_check.setChecked(vibro_on);
    }

    private void savePreferences()
    {
        boolean music_on = music_check.isChecked();
        boolean sounds_on = sounds_check.isChecked();
        boolean  vibro_on = vibro_check.isChecked();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(MUSIC, music_on);
        editor.putBoolean(SOUNDS, sounds_on);
        editor.putBoolean(VIBRO, vibro_on);
        editor.commit();
        updateEnvironment();
    }

    private void updateEnvironment()
    {

    }

    @Override
    public void onBackPressed()
    {
        savePreferences();
        finish();
    }
}