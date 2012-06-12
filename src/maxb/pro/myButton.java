package maxb.pro;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.animation.*;

public class myButton extends FrameLayout
{
    private ImageView mImage = null;
    private AnimationSet animSet = null;

    public myButton(Context mContext, AttributeSet set)
    {
         super(mContext, set);
         LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         li.inflate(R.layout.my_button, this,true);
         mImage = (ImageView)findViewById(R.id.my_button_pic);
         TypedArray a = mContext.obtainStyledAttributes(set, R.styleable.myButton);
         Drawable pic = a.getDrawable(R.styleable.myButton_image);
         mImage.setImageDrawable(pic);
         a.recycle();

         initAnimation();
    }

    private void initAnimation()
    {
        animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation animRotate = new RotateAnimation(0.0f, -360.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(1500);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);


    }

    public ImageView getImageView()
    {
        return  mImage;
    }

    public void startAnimation()
    {
        mImage.startAnimation(animSet);
    }

    public void stopAnimation()
    {
        mImage.clearAnimation();
    }
}
