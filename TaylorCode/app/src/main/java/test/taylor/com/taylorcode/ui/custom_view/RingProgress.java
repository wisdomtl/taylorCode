package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.util.DimensionUtil;

public class RingProgress extends View {

    private static final float END_ANGLE = 360;

    private float INNER_RING_RADIUS;
    private float OUT_RING_WIDTH;
    private float INNER_RING_WIDTH;
    private float PROGRESS_RING_WIDTH;
    private float START_ANGLE;

    private float progress;
    private Paint paint;

    public RingProgress(Context context) {
        super(context);
        init(getContext());
    }

    public RingProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(getContext());
    }

    public RingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(getContext());
    }

    public void setInnerRingRadius(float innerRingRadius) {
        this.INNER_RING_RADIUS = innerRingRadius;
    }

    public void setOutRingWidth(float outRingWidth) {
        this.OUT_RING_WIDTH = outRingWidth;
    }

    public void setInnerRingWidth(float innerRingWidth) {
        this.INNER_RING_WIDTH = innerRingWidth;
    }

    public void setProgressBarWidth(float progressBarWidth) {
        this.PROGRESS_RING_WIDTH = progressBarWidth;
    }

    private void init(Context context) {
        paint = new Paint();

        //get default value
        INNER_RING_RADIUS = DimensionUtil.dp2px(context, 24);
        OUT_RING_WIDTH = DimensionUtil.dp2px(context, 4);
        INNER_RING_WIDTH = DimensionUtil.dp2px(context, 6);
        PROGRESS_RING_WIDTH = DimensionUtil.dp2px(context, 3);
        START_ANGLE = -90f;
        progress = 0.3f;
    }

    private Paint getOutRingPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.parseColor("#4B4B4B"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(OUT_RING_WIDTH);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint getInnerRingPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.parseColor("#BDBD93"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(INNER_RING_WIDTH);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint getProgressRingPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.parseColor("#FFDD00"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(PROGRESS_RING_WIDTH);
        paint.setAntiAlias(true);
        return paint;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw out ring
        float ringCenterX = getWidth() / 2;
        float outRingRadius = getWidth() / 2 - OUT_RING_WIDTH;
        canvas.drawCircle(ringCenterX, ringCenterX, outRingRadius, getOutRingPaint());

        //draw inner ring
        float innerRingRadius = this.INNER_RING_RADIUS - INNER_RING_WIDTH;
        canvas.drawCircle(ringCenterX, ringCenterX, innerRingRadius, getInnerRingPaint());

        //draw progress bar
        float progressRingLeft = ringCenterX - outRingRadius + PROGRESS_RING_WIDTH;
        float progressRingRight = ringCenterX + outRingRadius - PROGRESS_RING_WIDTH;
        canvas.drawArc(progressRingLeft, progressRingLeft, progressRingRight, progressRingRight, START_ANGLE, progress * END_ANGLE, false, getProgressRingPaint());
    }
}
