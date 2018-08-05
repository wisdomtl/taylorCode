package test.taylor.com.taylorcode.ui.window;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SuspendWindow {
    /**
     * the content view of window
     */
    private View windowView;
    /**
     * the layout param for windowView
     */
    private WindowManager.LayoutParams layoutParam;

    private Context context;

    private static volatile SuspendWindow INSTANCE;

    public static SuspendWindow getInstance() {
        if (INSTANCE == null) {
            synchronized (SuspendWindow.class) {
                if (INSTANCE == null) {
                    //in case of memory leak for singleton
                    INSTANCE = new SuspendWindow();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    private SuspendWindow() {
    }

    public void show(Context context) {
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
        if (layoutParam == null) {
            layoutParam = generateDefaultLayoutParam();
        }
        windowManager.addView(windowView, layoutParam);
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
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 0;
        layoutParams.y = screenHeight / 3;
        return layoutParams;
    }

    private View generateDefaultWindowView() {
        TextView tv = new TextView(context);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText("window view");
        tv.setTextSize(30);
        return tv;
    }

    public void dismiss() {
        if (context == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.removeView(windowView);
        }
    }

    public void hide() {
        if (windowView != null) {
            windowView.setVisibility(View.GONE);
        }
    }

    public SuspendWindow setView(View view) {
        this.windowView = view;
        return this;
    }

    public SuspendWindow setLayoutParam(WindowManager.LayoutParams layoutParam) {
        this.layoutParam = layoutParam;
        return this;
    }
}
