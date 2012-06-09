package maxb.pro;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

public class IndicatorView extends View
{
    private OrientationInfo orientation = null;
    private int mStep = 0;
    private int mWidth = 0;
    private int mCenter = 0;

    public IndicatorView(Context context, AttributeSet attr)
    {
        super(context, attr);
        orientation = new OrientationInfo(context);

    }



    @Override
    public void onDraw(Canvas canvas)
    {
        float X = orientation.getX();
        float Y = orientation.getY();
        canvas.drawColor(Color.WHITE);
        Paint paint_red = new Paint();
        paint_red.setColor(Color.RED);
        canvas.drawCircle(mCenter - mStep * X, mCenter + mStep * Y, 20, paint_red);

        //GradientDrawable circle = (GradientDrawable)getResources().getDrawable(R.drawable.circle);

    }




    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec)
    {
        int mH = measureHeight(hMeasureSpec);
        int mW = measureWidth(wMeasureSpec);
        setMeasuredDimension(mH, mW);
        mWidth = getMeasuredWidth();
        mCenter = mWidth/2;
        mStep = mWidth/20;
    }

    private int measureHeight(int measureSpec)
    {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        return specSize;
    }

    private int measureWidth(int measureSpec)
    {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        return specSize;
    }

    public void updateIndicator()
    {
        invalidate();
    }



}

