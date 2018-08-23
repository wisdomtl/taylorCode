package test.taylor.com.taylorcode.ui.window;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * suspending window in app,it shows throughout the whole app
 */
public class FloatWindow implements View.OnTouchListener {

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    private static final long WELT_ANIMATION_DURATION = 150;
    /**
     * the content view of window
     */
    private View windowView;
    /**
     * the layout param for windowView
     */
    private WindowManager.LayoutParams layoutParam;
    private int width;
    private int height;
    //    private Rect windowRect;
    private int lastTouchX;
    private int lastTouchY;
    private int lastWeltX;
    private int screenWidth;
    private int screenHeight;
    private Context context;
    private GestureDetector gestureDetector;
    private OnWindowViewClickListener clickListener;
    private OnWindowStatusChangeListener onWindowStatusChangeListener;
    /**
     * this list records the activities which shows this window
     */
    private List<Class> whiteList;
    /**
     * if true,whiteList will be used to depend which activity could show window
     * if false,all activities in app is allow to show window
     */
    private boolean enableWhileList;
    /**
     * whether to show or hide this window
     * true by default
     */
    private boolean enable = true;

    private static volatile FloatWindow INSTANCE;
    /**
     * the animation make window stick to the left or right side of screen
     */
    private ValueAnimator weltAnimator;

    public static FloatWindow getInstance() {
        if (INSTANCE == null) {
            synchronized (FloatWindow.class) {
                if (INSTANCE == null) {
                    //in case of memory leak for singleton
                    INSTANCE = new FloatWindow();
                }
            }
        }
        return INSTANCE;
    }

    public FloatWindow init(Context context) {
        whiteList = new ArrayList<>();
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        prepareScreenDimension(windowManager);
        gestureDetector = new GestureDetector(context, new GestureListener());
        return this ;
    }


    private int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    private FloatWindow() {
    }

    public void updateWindowView(IWindowUpdater updater) {
        if (updater != null) {
            updater.updateWindowView(windowView);
        }
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        if (windowManager != null) {
            windowManager.updateViewLayout(windowView, layoutParam);
        }
    }


    public void setOnClickListener(OnWindowViewClickListener listener) {
        clickListener = listener;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setOnWindowStatusChangeListener(OnWindowStatusChangeListener onWindowStatusChangeListener) {
        this.onWindowStatusChangeListener = onWindowStatusChangeListener;
    }

    public void show(Context context) {
        if (!enable) {
            return;
        }
        if (windowView == null) {
            return;
        }
        this.context = context ;
        layoutParam = generateLayoutParam(screenWidth, screenHeight);
        //in case of "IllegalStateException :has already been added to the window manager."
        if (windowView.getParent() == null) {
            WindowManager windowManager = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE));
            windowManager.addView(windowView, layoutParam);
            if (onWindowStatusChangeListener != null) {
                onWindowStatusChangeListener.onShow();
            }
        }
    }

    private WindowManager.LayoutParams generateLayoutParam(int screenWidth, int screenHeight) {
        if (context == null) {
            return new WindowManager.LayoutParams();
        }

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = this.width == 0 ? DEFAULT_WIDTH : this.width;
        layoutParams.height = this.height == 0 ? DEFAULT_HEIGHT : this.height;
        int defaultX = screenWidth - layoutParams.width;
        int defaultY = 2 * screenHeight / 3;
        layoutParams.x = layoutParam == null ? defaultX : layoutParam.x;
        layoutParams.y = layoutParam == null ? defaultY : layoutParam.y;
        return layoutParams;
    }

    public WindowManager.LayoutParams getLayoutParam() {
        return layoutParam;
    }

    public void dismiss() {
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        if (windowManager != null) {
            //in case of "IllegalStateException :not attached to window manager."
            if (windowView.getParent() != null) {
                windowManager.removeViewImmediate(windowView);
                if (onWindowStatusChangeListener != null) {
                    onWindowStatusChangeListener.onDismiss();
                }
            }
        }
    }

    public boolean isShowing() {
        if (windowView == null) {
            return false;
        }
        if (windowView.getParent() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setWhiteList(List<Class> whiteList) {
        enableWhileList = true;
        this.whiteList = whiteList;
    }

    /**
     * invoking this method is a must ,or window has no content to show
     *
     * @param view the content view of window
     * @return
     */
    public FloatWindow setView(View view) {
        this.windowView = view;
        windowView.setOnTouchListener(this);
        return this;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                onActionDown(event);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                onActionMove(event);
//                break;
//            default:
//                break;
//        }
        //let GestureDetector take care of touch event,in order to parsing touch event into different gesture
        gestureDetector.onTouchEvent(event);
        //there is no ACTION_UP event in GestureDetector
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                onActionUp(event, screenWidth, width);
                break;
            default:
                break;
        }
        return true;
    }

    private void onActionUp(MotionEvent event, int screenWidth, int width) {
        int upX = ((int) event.getRawX());
        int endX;
        if (upX > screenWidth / 2) {
            endX = screenWidth - width;
        } else {
            endX = 0;
        }

        if (weltAnimator == null) {
            weltAnimator = ValueAnimator.ofInt(layoutParam.x, endX);
            weltAnimator.setInterpolator(new LinearInterpolator());
            weltAnimator.setDuration(WELT_ANIMATION_DURATION);
            weltAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int x = ((int) animation.getAnimatedValue());
                    if (layoutParam != null) {
                        layoutParam.x = x;
                    }
                    WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
                    if (windowManager != null) {
                        windowManager.updateViewLayout(windowView, layoutParam);
                    }
                }
            });
        }
        weltAnimator.setIntValues(layoutParam.x, endX);
        weltAnimator.start();
    }


    private void onActionMove(MotionEvent event) {
        int currentX = (int) event.getRawX();
        int currentY = (int) event.getRawY();
        int dx = currentX - lastTouchX;
        int dy = currentY - lastTouchY;
        lastTouchX = currentX;
        lastTouchY = currentY;
        layoutParam.x += dx;
        layoutParam.y += dy;
//        windowRect.set(layoutParam.x, layoutParam.y, layoutParam.x + layoutParam.width, layoutParam.y + layoutParam.height);
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        if (windowManager != null) {
            int rightMost = screenWidth - layoutParam.width;
            int leftMost = 0;
            int topMost = 0;
            int bottomMost = screenHeight - layoutParam.height - getNavigationBarHeight(context);
            WindowManager.LayoutParams partnerParam = null;
            if (onWindowStatusChangeListener != null) {
                partnerParam = onWindowStatusChangeListener.onWindowMove(dx, dy, screenWidth, screenHeight, layoutParam);
            }
            //adjust move area according to partner window
            if (partnerParam != null) {
                if (partnerParam.x < layoutParam.x) {
                    leftMost = partnerParam.width - layoutParam.width / 2;
                } else if (partnerParam.x > layoutParam.x) {
                    rightMost = screenWidth - (layoutParam.width / 2 + partnerParam.width);
                }
            }

            //make window float inside screen
            if (layoutParam.x < leftMost) {
                layoutParam.x = leftMost;
            }
            if (layoutParam.x > rightMost) {
                layoutParam.x = rightMost;
            }
            if (layoutParam.y < topMost) {
                layoutParam.y = topMost;
            }
            if (layoutParam.y > bottomMost) {
                layoutParam.y = bottomMost;
            }
            windowManager.updateViewLayout(windowView, layoutParam);
        }

    }

    private void onActionDown(MotionEvent event) {
        lastTouchX = (int) event.getRawX();
        lastTouchY = (int) event.getRawY();
    }


    private void prepareScreenDimension(WindowManager windowManager) {
        if (screenWidth != 0 && screenHeight != 0) {
            return;
        }
        if (windowManager != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Display display = windowManager.getDefaultDisplay();
            if (display != null) {
                windowManager.getDefaultDisplay().getMetrics(dm);
                screenWidth = dm.widthPixels;
                screenHeight = dm.heightPixels;
            }
        }
    }

    /**
     * let ui decide how to update window
     */
    public interface IWindowUpdater {
        void updateWindowView(View windowView);
    }

    public interface OnWindowViewClickListener {
        void onWindowViewClick();
    }

    public interface OnWindowStatusChangeListener {
        void onShow();

        void onDismiss();

        WindowManager.LayoutParams onWindowMove(float dx, float dy, int screenWidth, int screenHeight, WindowManager.LayoutParams layoutParams);
    }

    private class GestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            onActionDown(e);
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (clickListener != null) {
                clickListener.onWindowViewClick();
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            onActionMove(e2);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

    }
}