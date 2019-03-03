package test.taylor.com.taylorcode.ui.touch_event;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import test.taylor.com.taylorcode.R;

public class TouchEventActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_event_activity);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("ttaylor", "TouchEventActivity.dispatchTouchEvent()" + " event=" + ev.getAction());
        boolean b = super.dispatchTouchEvent(ev);
        Log.v("ttaylor", "TouchEventActivity.dispatchTouchEvent()" + "  return " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ttaylor", "TouchEventActivity.onTouchEvent()" + " event=" + event.getAction());
        boolean b = super.onTouchEvent(event);
        Log.v("ttaylor", "TouchEventActivity.onTouchEvent()" + "  return " + b);
        return b;
    }

}
