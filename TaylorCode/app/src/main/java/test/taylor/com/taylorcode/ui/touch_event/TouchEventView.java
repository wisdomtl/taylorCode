package test.taylor.com.taylorcode.ui.touch_event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchEventView extends View {
    public TouchEventView(Context context) {
        super(context);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ttaylor", "TouchEventView.onTouchEvent()" + " event=" + event.getAction());
//        boolean b = super.onTouchEvent(event);
        boolean b = super.onTouchEvent(event);
//        Log.v("ttaylor", "TouchEventView.onTouchEvent()" + "  return " + b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("ttaylor", "TouchEventView.dispatchTouchEvent()" + " event=" + ev.getAction());
        boolean b = super.dispatchTouchEvent(ev);
//        Log.v("ttaylor", "TouchEventView.dispatchTouchEvent()" + "  return " + b);
        return b;
    }

}
