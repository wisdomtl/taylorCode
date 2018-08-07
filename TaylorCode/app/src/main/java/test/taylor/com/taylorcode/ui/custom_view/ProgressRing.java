package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;


import test.taylor.com.taylorcode.util.DimensionUtil;

public class ProgressRing extends android.support.v7.widget.AppCompatImageView {

    private static final float ANGLE_SPAN = 360;

    private float outRingWidth;
    private float progressRingWidth;
    private float START_ANGLE;
    private RectF progressRingRect;
    private Rect textRect;

    private float progress;
    private Paint circlePaint;
    private Paint textPaint;
    private float textSize;
    private String text;
    private float textStrokeWidth;

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

    public void setText(String text) {
        this.text = text;
    }

    public void setOutRingWidth(float outRingWidth) {
        this.outRingWidth = outRingWidth;
    }


    public void setProgressBarWidth(float progressBarWidth) {
        this.progressRingWidth = progressBarWidth;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }

    private void init(Context context) {
        circlePaint = new Paint();

        //get default value
        outRingWidth = DimensionUtil.dp2px(context, 4);
        progressRingWidth = DimensionUtil.dp2px(context, 3);
        START_ANGLE = -90f;
        progress = 0.3f;
        textSize = 25;
        textStrokeWidth = 20;

    }

    private Paint getOutRingPaint() {
        if (circlePaint == null) {
            circlePaint = new Paint();
        }
        circlePaint.setColor(Color.parseColor("#4B4B4B"));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(outRingWidth);
        circlePaint.setAntiAlias(true);
        return circlePaint;
    }

    private Paint getInnerRingPaint() {
        if (circlePaint == null) {
            circlePaint = new Paint();
        }
        circlePaint.setColor(Color.parseColor("#BDBD93"));
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        return circlePaint;
    }

    private Paint getProgressRingPaint() {
        if (circlePaint == null) {
            circlePaint = new Paint();
        }
        circlePaint.setColor(Color.parseColor("#FFDD00"));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(progressRingWidth);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        return circlePaint;
    }

    private Paint getTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
        }
        textPaint.setColor(Color.parseColor("#FFDD00"));
        textPaint.setStrokeWidth(textStrokeWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        return textPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw out ring
        float ringCenterX = getMeasuredWidth() / 2;
        float outRingRadius = getMeasuredWidth() / 2 - outRingWidth / 2;
        //key point:the third param "radius" is inner radius + half of paint stroke width
        canvas.drawCircle(ringCenterX, ringCenterX, outRingRadius, getOutRingPaint());

        //draw inner ring
        float innerRingRadius = getMeasuredWidth() / 2 - outRingWidth;
        canvas.drawCircle(ringCenterX, ringCenterX, innerRingRadius, getInnerRingPaint());

        //draw progress bar
        float progressRingLeft = ringCenterX - outRingRadius + progressRingWidth;
        float progressRingRight = ringCenterX + outRingRadius - progressRingWidth;
        if (progressRingRect == null) {
            progressRingRect = new RectF(progressRingLeft, progressRingLeft, progressRingRight, progressRingRight);
        }
        canvas.drawArc(progressRingRect, START_ANGLE, progress * ANGLE_SPAN, false, getProgressRingPaint());

        //draw text
        if (!TextUtils.isEmpty(text)) {
            Paint paint = getTextPaint();
            if (textRect == null) {
                textRect = new Rect();
                paint.getTextBounds(text, 0, text.length(), textRect);
            }
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            //baseline is the key for drawing text
            int baseline = (getMeasuredHeight() - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top;
            canvas.drawText(text, getMeasuredWidth() / 2, baseline, paint);
        }
        //key point:move super.onDraw() here make the custom drawing below the real content of ImageView
        super.onDraw(canvas);
    }
}
