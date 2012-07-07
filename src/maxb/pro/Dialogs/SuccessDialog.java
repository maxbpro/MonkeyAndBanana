package maxb.pro.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import maxb.pro.Actors.Enemy;
import maxb.pro.Adapters.EnemiesAdapter;
import maxb.pro.DataBaseInteract.Row_User_Levels;
import maxb.pro.R;
import maxb.pro.Views.myButton;

import java.util.ArrayList;

/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class SuccessDialog extends Dialog
{
    private Context mContext = null;
    public enum Result {UPDATE,REFRESH, NEXT_LEVEL}
    private Result mResult = Result.UPDATE;
    private Row_User_Levels level = null;
    private ArrayList<Enemy> enemies = null;

    public SuccessDialog(Context context, int theme, Row_User_Levels level, ArrayList<Enemy> enemies)
    {
        super(context, theme);
        mContext = context;
        setContentView(R.layout.success);
        this.level = level;
        this.enemies = enemies;
        initButtons();
        initText();
        initGallery();
    }

    private void initButtons()
    {
        final myButton btn_next_level = (myButton)findViewById(R.id.result_next_level);
        final myButton btn_refresh = (myButton)findViewById(R.id.result_refresh);
        btn_next_level.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_next_level.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        mResult = Result.NEXT_LEVEL;
                        dismiss();
                        break;
                }
                return true;
            }
        });
        btn_refresh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_refresh.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        mResult = Result.REFRESH;
                        dismiss();
                        break;
                }
                return true;
            }
        });

    }

    private void initText()
    {
        TextView txt_time = (TextView)findViewById(R.id.result_text_time);
        TextView txt_scores = (TextView)findViewById(R.id.result_text_score);
        txt_time.setText(level.get_time_like_string());
        txt_scores.setText(String.valueOf(level.get_scores()));
    }

    private void initGallery()
    {
        Gallery gallery = (Gallery)findViewById(R.id.result_gallery);
        EnemiesAdapter adapter = new EnemiesAdapter(mContext ,enemies);
        gallery.setAdapter(adapter);
    }

    public Result getResult()
    {
        return mResult;
    }


}
