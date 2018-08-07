package test.taylor.com.taylorcode.ui.window;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.DimensionUtil;

/**
 * suspending window in app,it shows throughout the whole app
 */
public class FloatWindow implements View.OnTouchListener {
    /**
     * the content view of window
     */
    private View windowView;
    /**
     * the layout param for windowView
     */
    private WindowManager.LayoutParams layoutParam;
    private float x;
    private float y;
    private float lastX;
    private float lastY;
    private Context context;
    /**
     * show or dismiss the window according to the app lifecycle
     */
    private AppStatusListener appStatusListener;
    /**
     * this list records the activities which shows this window
     */
    private List<Class> whiteList;
    /**
     * if true,whiteList will be used to depend which activity could show window
     * if false,all activities in app is allow to show window
     */
    private boolean enableWhileList;

    private static volatile FloatWindow INSTANCE;

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

    private FloatWindow() {
        appStatusListener = new AppStatusListener();
        whiteList = new ArrayList<>();
    }

    public AppStatusListener getAppStatusListener() {
        return appStatusListener;
    }

    public void updateWindowView(IWindowUpdater updater) {
        if (updater != null) {
            updater.updateWindowView(windowView);
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.updateViewLayout(windowView, layoutParam);
        }
    }

    private void show(Context context) {
        if (context == null) {
            return;
        }
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return;
        }
        if (windowView == null) {
            windowView = generateDefaultWindowView();
        }
        windowView.setOnTouchListener(this);
        if (layoutParam == null) {
            layoutParam = generateDefaultLayoutParam();
        }
        //in case of "IllegalStateException :has already been added to the window manager."
        if (windowView.getParent() == null) {
            windowManager.addView(windowView, layoutParam);
        }
        windowView.setVisibility(View.VISIBLE);
    }


    private WindowManager.LayoutParams generateDefaultLayoutParam() {
        if (context == null) {
            return new WindowManager.LayoutParams();
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;//this is the key point let window be above all activity
//        layoutParams.token = getWindow().getDecorView().getWindowToken();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = DimensionUtil.dp2px(context ,54);
        layoutParams.height = DimensionUtil.dp2px(context,54) ;
        layoutParams.x = 0;
        layoutParams.y = screenHeight / 3;
        return layoutParams;
    }

    private View generateDefaultWindowView() {
        View view = LayoutInflater.from(context).inflate(R.layout.float_window, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return view;
    }

    private void dismiss() {
        if (context == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.removeViewImmediate(windowView);
        }
    }

    public void setWhiteList(List<Class> whiteList) {
        enableWhileList = true;
        this.whiteList = whiteList;
    }

    public FloatWindow setView(View view) {
        this.windowView = view;
        return this;
    }

    public FloatWindow setLayoutParam(WindowManager.LayoutParams layoutParam) {
        this.layoutParam = layoutParam;
        return this;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event);
                break;
            default:
                break;
        }
        return false;
    }

    private void onActionMove(MotionEvent event) {
        float dx = event.getRawX() - lastX;
        float dy = event.getRawY() - lastY;
        lastX = event.getRawX();
        lastY = event.getRawY();
        Log.v("ttaylor", "SuspendWindow.onActionMove()" + "  dx=" + dx + " ,dy=" + dy);
        layoutParam.x += dx;
        layoutParam.y += dy;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.updateViewLayout(windowView, layoutParam);
        }

    }

    private void onActionDown(MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY();
        lastX = x;
        lastY = y;
        Log.v("ttaylor", "SuspendWindow.onActionDown()" + "  x=" + x + " ,y=" + y);
    }

    /**
     * the listener control the timing of showing or dismissing the window
     */
    private class AppStatusListener implements Application.ActivityLifecycleCallbacks {

        private int foregroundActivityCount = 0;
        private boolean isConfigurationChange = false;


        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (isConfigurationChange) {
                isConfigurationChange = false;
                return;
            }

            //show the window when app is in foreground again
            if (enableWhileList) {
                if (whiteList.contains(activity.getClass())) {
                    show(activity.getApplicationContext());
                } else {
                    dismiss();
                }
            } else {
                show(activity.getApplicationContext());
            }
            foregroundActivityCount++;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                isConfigurationChange = true;
                return;
            }
            foregroundActivityCount--;
            if (foregroundActivityCount == 0) {
                //dismiss the window when app is in background
                dismiss();
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

    /**
     * let ui decide how to update window
     */
    public interface IWindowUpdater {
        void updateWindowView(View windowView);
    }
}
