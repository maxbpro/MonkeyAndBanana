package maxb.pro.Views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import maxb.pro.Specials.OrientationInfo;
import maxb.pro.R;

public class IndicatorView extends View
{
    private Context mContext = null;
    private enum DefaultOrientations{LANDSCAPE, PORTRAIT}
    private DefaultOrientations defaultOrientation = DefaultOrientations.LANDSCAPE;
    private boolean enabled = false;
    private OrientationInfo orientation = null;
    private int mStep = 0;
    private int mWidth = 0;
    private int mCenter = 0;
    private Paint paint_red = null;
    private Paint paint_green = null;
    private int score = 0;
    private int SMILE_WIDTH = 64; // in dp
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
        mContext = context;
        orientation = new OrientationInfo(context);
        determineOrientation();
        initSmileSize();
        initPaints();
        initAllBitmap();
        switchSmile(Smiles.NORMAL);
    }

    private void determineOrientation()
    {
        int default_orientation = (((Activity)mContext).getWindowManager().getDefaultDisplay().getOrientation());
        switch (default_orientation)
        {
            case Surface.ROTATION_0:
                defaultOrientation = DefaultOrientations.LANDSCAPE;
                break;
            case Surface.ROTATION_90:
                defaultOrientation = DefaultOrientations.PORTRAIT;
                break;
        }
    }

    private void initPaints()
    {
        paint_red = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_red.setColor(Color.RED);
        paint_red.setTextSize(20);
        paint_green = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_green.setColor(Color.GREEN);
        paint_green.setTextSize(20);
        paint_green.setStyle(Paint.Style.FILL);
    }

    private void initSmileSize()
    {
        int screenLayout = getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
            SMILE_WIDTH = 32;
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

    public void setScore(int score)
    {
        this.score = score;
    }

    public void cleatScore()
    {
        score = 0;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        if (defaultOrientation == DefaultOrientations.LANDSCAPE)
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
               if(score>0)
                  canvas.drawText("+" + score, mCenter + SMILE_WIDTH, mCenter - 10, paint_green);
               else if (score<0)
                  canvas.drawText("-" + score, mCenter + SMILE_WIDTH, mCenter - 10, paint_red );
           }
        }
        else
        {
            if(enabled)
            {
                float X = orientation.getY();
                float Y = orientation.getX();
                //canvas.drawCircle(mCenter - mStep * X, mCenter + mStep * Y, 20, paint_red);
                canvas.drawBitmap(bmp_current, mCenter + mStep * X, mCenter + mStep * Y, new Paint());
            }
            else
            {
                canvas.drawBitmap(bmp_current, mCenter, mCenter, new Paint());
                if(score>0)
                    canvas.drawText("+" + score, mCenter + SMILE_WIDTH, mCenter - 10, paint_green);
                else if (score<0)
                    canvas.drawText("-" + score, mCenter + SMILE_WIDTH, mCenter - 10, paint_red );
            }
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
        bmp_angry  = getResizeBitmap((BitmapDrawable) getResources().getDrawable(R.drawable.angry));
        bmp_happy  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.happy));
        bmp_sad  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.sad));
        bmp_cry  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.cry));
        bmp_cry_much  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.cry_much));
        bmp_fear  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.fear));
        bmp_surprise  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.surprise));
        bmp_normal = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.normal));
        bmp_in_love  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.in_love));
        bmp_wink  = getResizeBitmap((BitmapDrawable)getResources().getDrawable(R.drawable.wink));
    }

    private Bitmap getResizeBitmap(BitmapDrawable bd)
    {
        Bitmap bmp = bd.getBitmap();
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int newWidth = SMILE_WIDTH;
        int newHeight = SMILE_WIDTH;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap new_bmp = Bitmap.createBitmap(bmp, 0, 0,
                width, height, matrix, true);
        return new_bmp;
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp*density);
    }

}

