package test.taylor.com.taylorcode.ui.surface_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.BitmapUtil;
import test.taylor.com.taylorcode.util.DimensionUtil;

/**
 * a SurfaceView which draws bitmaps one after another like frame animation
 */
public class FrameSurfaceView extends BaseSurfaceView {

    private final int[] bitmaps = new int[]{R.drawable.watch_reward_1,
            R.drawable.watch_reward_2,
            R.drawable.watch_reward_3,
            R.drawable.watch_reward_4,
            R.drawable.watch_reward_5,
            R.drawable.watch_reward_6,
            R.drawable.watch_reward_7,
            R.drawable.watch_reward_8,
            R.drawable.watch_reward_9,
            R.drawable.watch_reward_10,
            R.drawable.watch_reward_11,
            R.drawable.watch_reward_12,
            R.drawable.watch_reward_13,
            R.drawable.watch_reward_14,
            R.drawable.watch_reward_15,
            R.drawable.watch_reward_16,
            R.drawable.watch_reward_17,
            R.drawable.watch_reward_18,
            R.drawable.watch_reward_19,
            R.drawable.watch_reward_20,
            R.drawable.watch_reward_21,
            R.drawable.watch_reward_22};

    private int bitmapIndex = 0;
    private Paint paint = new Paint();

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
        if (bitmapIndex >= bitmaps.length) {
            return;
        }
        Log.v("ttaylor", "ProgressRingSurfaceView.onSurfaceDraw()" + "  bitmapIndex=" + bitmapIndex);
        Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getContext().getResources(), bitmaps[bitmapIndex], DimensionUtil.dp2px(54), DimensionUtil.dp2px(54));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmapIndex++;
    }

    /**
     * clear out the drawing on canvas,preparing for the next frame
     * @param canvas
     */
    private void clearCanvas(Canvas canvas) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }
}
