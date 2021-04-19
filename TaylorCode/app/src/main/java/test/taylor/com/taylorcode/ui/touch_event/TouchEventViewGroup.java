package test.taylor.com.taylorcode.ui.touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TouchEventViewGroup extends RelativeLayout {
    public TouchEventViewGroup(Context context) {
        super(context);
    }

    public TouchEventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            b = true;
//        }
        Log.w("ttaylor", "TouchEventViewGroup.onTouchEvent()"+" event = "+event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("ttaylor", "TouchEventViewGroup.dispatchTouchEvent()"+" event = "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            b = true;
//        }
//        Log.v("ttaylor", "TouchEventViewGroup.onInterceptTouchEvent()" + "  return " + b);
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            return  true;
        }
        Log.w("ttaylor", "TouchEventViewGroup.onInterceptTouchEvent()" + " event=" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }
}
