package com.ist.cadillacpaltform.UI.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ist.cadillacpaltform.R;

/**
 * Created by dearlhd on 2017/3/9.
 */
public class PieChartView extends View {
    public enum PieChartColor { RED, GREEN}

    private final Paint mPaint;
    private final Context mContext;
    private float mSweepAngle = 0;
    private PieChartColor mColor = PieChartColor.GREEN;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setSweepAngle (float angle) {
        mSweepAngle = angle;
    }

    public void setPieChartColor (PieChartColor color) {
        mColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(getResources().getColor(R.color.colorLightGreyLine));
        canvas.drawCircle(getWidth() / 2f, getWidth() / 2f, getWidth() / 2f, mPaint);

        if (mColor == PieChartColor.RED) {
            mPaint.setColor(getResources().getColor(R.color.colorRedText));
        } else if (mColor == PieChartColor.GREEN) {
            mPaint.setColor(getResources().getColor(R.color.colorDarkGreen));
        }
        RectF oval = new RectF(1, 1, getWidth(), getWidth());
        canvas.drawArc(oval, -90, mSweepAngle, true, mPaint);
    }

}
