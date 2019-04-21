package test.taylor.com.taylorcode.ui.surface_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.util.BitmapUtil;
import test.taylor.com.taylorcode.util.DimensionUtil;

/**
 * a SurfaceView which draws bitmaps one after another like frame animation
 */
public class FrameSurfaceView extends BaseSurfaceView {

    private List<Integer> bitmaps = new ArrayList<>();

    private int bitmapIndex = bitmaps.size();
    private Paint paint = new Paint();

    public void setDuration(int duration) {
        int frameDuration = duration / bitmaps.size();
        setFrameDuration(frameDuration);
    }

    public void setBitmaps(List<Integer> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public FrameSurfaceView(Context context) {
        super(context);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSurfaceDraw(Canvas canvas) {
        clearCanvas(canvas);
        if (isDrawFinish()) {
            return;
        }
        Log.v("ttaylor", "ProgressRingSurfaceView.onSurfaceDraw()" + "  bitmapIndex=" + bitmapIndex);
        Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getContext().getResources(), bitmaps.get(bitmapIndex), DimensionUtil.dp2px(54), DimensionUtil.dp2px(54));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmapIndex++;
    }

    private boolean isDrawFinish() {
        if (bitmapIndex >= bitmaps.size()) {
            return true;
        } else {
            return false;
        }
    }

    public void start() {
        bitmapIndex = 0;
    }


    /**
     * clear out the drawing on canvas,preparing for the next frame
     *
     * @param canvas
     */
    private void clearCanvas(Canvas canvas) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }
}
