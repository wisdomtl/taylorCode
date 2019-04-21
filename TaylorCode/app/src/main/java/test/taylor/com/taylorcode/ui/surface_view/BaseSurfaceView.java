package test.taylor.com.taylorcode.ui.surface_view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEFAULT_FRAME_DURATION_MILLISECOND = 50;

    private HandlerThread handlerThread;
    private SurfaceViewHandler handler;
    private int frameDuration = DEFAULT_FRAME_DURATION_MILLISECOND;
    private Canvas canvas;

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setFrameDuration(int frameDuration) {
        this.frameDuration = frameDuration;
    }

    private void init() {
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawThread();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }

    private void stopDrawThread() {
        handlerThread.quit();
        handler = null;
    }

    private void startDrawThread() {
        handlerThread = new HandlerThread("SurfaceViewThread");
        handlerThread.start();
        handler = new SurfaceViewHandler(handlerThread.getLooper());
        handler.post(new DrawRunnable()) ;
    }

    private class SurfaceViewHandler extends Handler {

        public SurfaceViewHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private class DrawRunnable implements Runnable {

        @Override
        public void run() {
            try {
                canvas = getHolder().lockCanvas();
                onSurfaceDraw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                getHolder().unlockCanvasAndPost(canvas);
            }

            handler.postDelayed(this, frameDuration);
        }
    }

    /**
     * draw things to surface by the canvas
     * @param canvas
     */
    protected abstract void onSurfaceDraw(Canvas canvas);
}
