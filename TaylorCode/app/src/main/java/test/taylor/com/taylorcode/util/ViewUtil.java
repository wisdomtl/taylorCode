package test.taylor.com.taylorcode.util;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;

public class ViewUtil {

    //spring anim in java way
    public static void addExtraAnimClickListener(View view, ValueAnimator animator, View.OnClickListener listener) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        animator.start();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        animator.reverse();
                        break;
                }
                return false;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }
}
