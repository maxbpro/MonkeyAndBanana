package maxb.pro;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class IndicatorView extends View
{
    private boolean enabled = false;
    private OrientationInfo orientation = null;
    private int mStep = 0;
    private int mWidth = 0;
    private int mCenter = 0;
    private int SMILE_WIDTH = 64;
    public enum Smiles {NORMAL, HAPPY, SAD, ANRGY, SURPRISE, WINK, CRY,
        CRY_MUCH, IN_LOVE, FEAR }
    private Bitmap bmp_current = null;
    private Bitmap bmp_normal = null;
    private Bitmap bmp_happy = null;
    private Bitmap bmp_sad = null;
    private Bitmap bmp_angry = null;
    private Bitmap bmp_surprise = null;
    private Bitmap bmp_wink = null;
    private Bitmap bmp_cry = null;
    private Bitmap bmp_cry_much = null;
    private Bitmap bmp_in_love = null;
    private Bitmap bmp_fear = null;

    public IndicatorView(Context context, AttributeSet attr)
    {
        super(context, attr);
        orientation = new OrientationInfo(context);
        initAllBitmap();
        switchSmile(Smiles.NORMAL);
        SMILE_WIDTH = dpToPx(SMILE_WIDTH);
    }

    public void setWidth(int width)
    {
        this.mWidth = width;
    }

    public void setEnable()
    {
        enabled = true;
    }

    public boolean IsEnabled()
    {
        return enabled;
    }

    public void switchSmile(Smiles smile)
    {
        switch (smile)
        {
            case NORMAL:
                bmp_current = bmp_normal;
                break;
            case HAPPY:
                bmp_current = bmp_happy;
                break;
            case FEAR:
                bmp_current = bmp_fear;
                break;
            case CRY:
                bmp_current = bmp_cry;
                break;
            case CRY_MUCH:
                bmp_current = bmp_cry_much;
                break;
            case WINK:
                bmp_current = bmp_wink;
                break;
            case SURPRISE:
                bmp_current = bmp_surprise;
                break;
            case ANRGY:
                bmp_current = bmp_angry;
                break;
            case IN_LOVE:
                bmp_current = bmp_in_love;
                break;
            case SAD:
                bmp_current = bmp_sad;
                break;
        }
    }

    public void update()
    {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(enabled)
        {
           float X = orientation.getX();
           float Y = orientation.getY();
           //canvas.drawCircle(mCenter - mStep * X, mCenter + mStep * Y, 20, paint_red);
           canvas.drawBitmap(bmp_current, mCenter - mStep * X, mCenter + mStep * Y, new Paint());
        }
        else
        {
            canvas.drawBitmap(bmp_current, mCenter, mCenter, new Paint());
        }

    }


    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec)
    {
        setMeasuredDimension(mWidth, mWidth);
        mWidth = getMeasuredWidth();
        mCenter = (mWidth - SMILE_WIDTH)/2;
        mStep = (mWidth - SMILE_WIDTH)/20;
    }


    private void initAllBitmap()
    {
        bmp_angry = BitmapFactory.decodeResource(getResources(), R.drawable.angry);
        bmp_happy = BitmapFactory.decodeResource(getResources(), R.drawable.happy);
        bmp_sad = BitmapFactory.decodeResource(getResources(), R.drawable.sad);
        bmp_cry = BitmapFactory.decodeResource(getResources(), R.drawable.cry);
        bmp_cry_much = BitmapFactory.decodeResource(getResources(), R.drawable.cry_much);
        bmp_fear = BitmapFactory.decodeResource(getResources(), R.drawable.fear);
        bmp_surprise = BitmapFactory.decodeResource(getResources(), R.drawable.surprise);
        bmp_normal = BitmapFactory.decodeResource(getResources(), R.drawable.normal);
        bmp_in_love = BitmapFactory.decodeResource(getResources(), R.drawable.in_love);
        bmp_wink = BitmapFactory.decodeResource(getResources(), R.drawable.wink);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp*density);
    }

}

