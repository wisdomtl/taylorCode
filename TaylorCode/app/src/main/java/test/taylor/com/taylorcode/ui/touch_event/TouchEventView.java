package test.taylor.com.taylorcode.ui.touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

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
        Log.e("ttaylor", "TouchEventView.onTouchEvent()" + "  event = " + event.getAction() );
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("ttaylor", "TouchEventView.dispatchTouchEvent()" + " event = " + ev.getAction() );
        return super.dispatchTouchEvent(ev);
    }

}
