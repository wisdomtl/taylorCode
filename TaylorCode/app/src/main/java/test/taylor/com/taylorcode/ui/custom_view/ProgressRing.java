package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.util.DimensionUtil;

public class ProgressRing extends View {

    private static final float ANGLE_SPAN = 360;

    private float innerRingRadius;
    private float outRingWidth;
    private float innerRingWidth;
    private float progressRingWidth;
    private float START_ANGLE;
    private RectF progressRingRect;

    private float progress;
    private Paint paint;

    public ProgressRing(Context context) {
        super(context);
        init(getContext());
    }

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(getContext());
    }

    public ProgressRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(getContext());
    }

    public void setInnerRingRadius(float innerRingRadius) {
        this.innerRingRadius = innerRingRadius;
    }

    public void setOutRingWidth(float outRingWidth) {
        this.outRingWidth = outRingWidth;
    }

    public void setInnerRingWidth(float innerRingWidth) {
        this.innerRingWidth = innerRingWidth;
    }

    public void setProgressBarWidth(float progressBarWidth) {
        this.progressRingWidth = progressBarWidth;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    private void init(Context context) {
        paint = new Paint();

        //get default value
        innerRingRadius = DimensionUtil.dp2px(context, 24);
        outRingWidth = DimensionUtil.dp2px(context, 4);
        innerRingWidth = DimensionUtil.dp2px(context, 6);
        progressRingWidth = DimensionUtil.dp2px(context, 3);
        START_ANGLE = -90f;
        progress = 0.3f;
    }

    private Paint getOutRingPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.parseColor("#4B4B4B"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(outRingWidth);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint getInnerRingPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.parseColor("#BDBD93"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(innerRingWidth);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint getProgressRingPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.parseColor("#FFDD00"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(progressRingWidth);
        paint.setAntiAlias(true);
        return paint;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw out ring
        float ringCenterX = getWidth() / 2;
        float outRingRadius = getWidth() / 2 - outRingWidth;
        canvas.drawCircle(ringCenterX, ringCenterX, outRingRadius, getOutRingPaint());

        //draw inner ring
        float innerRingRadius = this.innerRingRadius - innerRingWidth;
        canvas.drawCircle(ringCenterX, ringCenterX, innerRingRadius, getInnerRingPaint());

        //draw progress bar
        float progressRingLeft = ringCenterX - outRingRadius + progressRingWidth;
        float progressRingRight = ringCenterX + outRingRadius - progressRingWidth;
        if (progressRingRect == null) {
            progressRingRect = new RectF(progressRingLeft, progressRingLeft, progressRingRight, progressRingRight);
        }
        canvas.drawArc(progressRingRect, START_ANGLE, progress * ANGLE_SPAN, false, getProgressRingPaint());
    }
}
