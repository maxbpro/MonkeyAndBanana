package maxb.pro.app.Views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.animation.*;
import maxb.pro.app.R;

public class myButton extends FrameLayout
{
    private Context mContext = null;
    private ImageView mImage = null;
    private enum AnimationMode {TWITCH, ROTATE, LEFT, RIGHT, ROTATE_SLOW}
    private AnimationMode animationMode = AnimationMode.ROTATE;
    private Animation animation = null;

    public myButton(Context mContext, AttributeSet set)
    {
         super(mContext, set);
         this.mContext = mContext;
         LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         li.inflate(R.layout.my_button, this,true);
         mImage = (ImageView)findViewById(R.id.my_button_pic);
         TypedArray a = mContext.obtainStyledAttributes(set, R.styleable.myButton);
         Drawable pic = a.getDrawable(R.styleable.myButton_image);
         animationMode = AnimationMode.values()[a.getInt(R.styleable.myButton_animMode, 0)];
         mImage.setImageDrawable(pic);
         a.recycle();
         initAnimation();
    }


    private void initAnimation()
    {
         switch (animationMode)
         {
             case ROTATE:
                 animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
                 break;
             case LEFT:
                 animation = AnimationUtils.loadAnimation(mContext, R.anim.left);
                 break;
             case RIGHT:
                 animation = AnimationUtils.loadAnimation(mContext, R.anim.right);
                 break;
             case TWITCH:
                 animation = AnimationUtils.loadAnimation(mContext, R.anim.twitch);
                 break;
             case ROTATE_SLOW:
                 animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_slow);
         }
    }

    public ImageView getImageView()
    {
        return  mImage;
    }

    public void startAnimation()
    {
        mImage.startAnimation(animation);
    }

    public void stopAnimation()
    {
        mImage.clearAnimation();
    }
}
