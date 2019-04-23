package test.taylor.com.taylorcode.ui.surface_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.util.BitmapUtil;


/**
 * a SurfaceView which draws bitmaps one after another like frame animation
 */
public class FrameSurfaceView extends BaseSurfaceView {
    public static final int INVALID_BITMAP_INDEX = -1;

    private List<Integer> bitmaps = new ArrayList<>();
    private Bitmap frameBitmap;
    private int bitmapIndex = INVALID_BITMAP_INDEX;
    private Paint paint = new Paint();
    private BitmapFactory.Options options = new BitmapFactory.Options();
    private Rect srcRect;
    private Rect dstRect = new Rect();
    private int defaultWidth;
    private int defaultHeight;

    public void setDuration(int duration) {
        int frameDuration = duration / bitmaps.size();
        setFrameDuration(frameDuration);
    }

    public void setBitmaps(List<Integer> bitmaps) {
        if (bitmaps == null || bitmaps.size() == 0) {
            return;
        }
        this.bitmaps = bitmaps;
        //by default, take the first bitmap's dimension into consideration
        getBitmapDimension(bitmaps.get(0));
    }

    private void getBitmapDimension(Integer integer) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.getResources(), integer, options);
        defaultWidth = options.outWidth;
        defaultHeight = options.outHeight;
        srcRect = new Rect(0, 0, defaultWidth, defaultHeight);
        Log.v("ttaylor", "FrameSurfaceView.getBitmapDimension()" + "  defaultWidth=" + defaultWidth + " defaultHeight=" + defaultHeight);
        //we have to re-measure to make defaultWidth in use in onMeasure()
        requestLayout();

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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        dstRect.set(0, 0, getWidth(), getHeight());
    }

    @Override
    protected int getDefaultWidth() {
        return defaultWidth;
    }

    @Override
    protected int getDefaultHeight() {
        return defaultHeight;
    }

    @Override
    protected void onFrameDrawFinish() {
        recycleOneFrame();
    }

    private void recycleOneFrame() {
        if (frameBitmap != null) {
            frameBitmap.recycle();
            frameBitmap = null;
        }
    }

    @Override
    protected void onFrameDraw(Canvas canvas) {
        clearCanvas(canvas);
        drawOneFrame(canvas);
    }

    private void drawOneFrame(Canvas canvas) {
        if (allowDraw()) {
            Log.v("ttaylor", "ProgressRingSurfaceView.onFrameDraw()" + "  bitmapIndex=" + bitmapIndex + " measureWidth=" + getMeasuredWidth());
            frameBitmap = BitmapUtil.decodeOriginBitmap(getResources(), bitmaps.get(bitmapIndex), options);
            canvas.drawBitmap(frameBitmap, srcRect, dstRect, paint);
            bitmapIndex++;
        }
    }

    private boolean allowDraw() {
        //frame animation has not started
        if (bitmapIndex == INVALID_BITMAP_INDEX) {
            return false;
        }
        //frame animation is doing
        return bitmapIndex < bitmaps.size();
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
