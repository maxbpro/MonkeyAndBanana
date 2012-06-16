package maxb.pro;


import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelView extends FrameLayout
{
    public enum State{OPEN, CLOSE, FINISH}
    private State mState = State.CLOSE;
    private static final String MODE = "MODE";
    private static final String LEVEL = "LEVEL";
    private Context mContext = null;
    private int mMode = 0;
    private int mLevel = 0;

    public LevelView(Context context, State mState, int mode, int level)
    {
        super(context);
        this.mState = mState;
        this.mContext = context;
        this.mMode = mode;
        this.mLevel = level;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.level_view, this, true);
        TextView title = (TextView)findViewById(R.id.level_text);
        ImageView image = (ImageView)findViewById(R.id.level_image);
        title.setText(String.valueOf(level));
        switch (mState)
        {
            case OPEN:
                image.setImageResource(R.drawable.open);
                initOpenModeLevel();
                break;
            case CLOSE:
                image.setImageResource(R.drawable.close);
                initCloseModeLevel();
                break;
            case FINISH:
                image.setImageResource(R.drawable.happy);
                initFinishModeLevel();
                break;
        }
    }

    private void initFinishModeLevel()
    {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SceneActivity.class);
                intent.putExtra(MODE, mMode);
                intent.putExtra(LEVEL, mLevel);
                mContext.startActivity(intent);
            }
        });

    }

    private void initCloseModeLevel()
    {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void initOpenModeLevel()
    {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SceneActivity.class);
                intent.putExtra(MODE, mMode);
                intent.putExtra(LEVEL, mLevel);
                mContext.startActivity(intent);
            }
        });
    }

    public State getState()
    {
        return mState;
    }


}
