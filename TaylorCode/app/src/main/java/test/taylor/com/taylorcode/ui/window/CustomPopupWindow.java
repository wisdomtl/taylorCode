package test.taylor.com.taylorcode.ui.window;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * window case3:customize PopupWindow
 * the PopupWindow which could be customized by the following aspect:
 * 1. the content of window which is a ViewGroup as a param of constructor
 * 2. the click listener of content's children
 * 3. the animation of showing and dismissing, including duration ,interpolater
 */
public class CustomPopupWindow extends PopupWindow implements View.OnTouchListener {

    private float dimAlpha = 0.5f;
    private int animateDuration = 300;
    private Activity hostActivity;
    private ViewGroup windowView;
    private int gravity;
    private Rect mTouchFrame;
    private OnItemClickListener onItemClickListener;
    protected GestureDetector gestureDetector;

    public CustomPopupWindow(ViewGroup windowView, int width, int height, @NonNull Activity hostActivity) {
        super(windowView, width, height);
        this.windowView = windowView;
        this.hostActivity = hostActivity;
        setTouchInterceptor(windowView);
    }

    private void setTouchInterceptor(ViewGroup windowView) {
        if (windowView == null) {
            Log.v("ttaylor", "CustomPopupWindow.setOnTouchListener()" + "  window view is null");
            return;
        }
        gestureDetector = new GestureDetector(windowView.getContext(), new GestureListener(windowView));
        this.setTouchInterceptor(this);
        //this is a must ,i dont know why. if not,PopupWindow wont receive touch event
        this.setBackgroundDrawable(new BitmapDrawable());
        //this is a must ,or PopupWindow wont receive the touch event when clicking outside of itself
        this.setOutsideTouchable(true);
        //this is a must ,or PopupWindow wont consume the touch event when clicking outside of itself
        this.setFocusable(true);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        this.gravity = gravity;
        prepareShowAnimation(windowView);
        changeBackgroundAlpha(hostActivity, dimAlpha);
    }

    /**
     * prepare the showing animation
     *
     * @param windowView
     */
    private void prepareShowAnimation(final View windowView) {
        if (windowView == null) {
            return;
        }
        switch (gravity) {
            case Gravity.BOTTOM:
                animateFromBottom(windowView, animateDuration);
                break;
        }
    }

    /**
     * make this window animate from the bottom of screen to show up
     *
     * @param windowView
     * @param animateDuration
     */
    private void animateFromBottom(final View windowView, final int animateDuration) {
        //get the real height of window view
        windowView.post(new Runnable() {
            @Override
            public void run() {
                windowView.setTranslationY(windowView.getMeasuredHeight());
            }
        });
        //animate the window view
        windowView.post(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(windowView)
                        .translationY(0)
                        .setInterpolator(new LinearOutSlowInInterpolator())
                        .setDuration(animateDuration)
                        .start();
            }
        });
    }

    @Override
    public void dismiss() {
        //1.do the dismiss animation firstly
        windowView.post(new Runnable() {
            @Override
            public void run() {
                animateToBottom(windowView, animateDuration);
            }
        });

        //2.do the real dismiss secondly, or dismiss animation wont show
        windowView.postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomPopupWindow.super.dismiss();
            }
        }, animateDuration);

        changeBackgroundAlpha(hostActivity, 1.0f);
    }

    /**
     * make this window animate back to screen bottom to dismiss,this is the reverse animation of animateFromBottom()
     *
     * @param windowView
     * @param animateDuration
     */
    private void animateToBottom(final View windowView, int animateDuration) {
        ViewCompat.animate(windowView)
                .translationY(windowView.getMeasuredHeight())
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setDuration(this.animateDuration)
                .start();
    }

    /**
     * dim or light the background of screen when this window show or dismiss
     *
     * @param activity
     * @param alpha
     */
    private void changeBackgroundAlpha(Activity activity, float alpha) {
        if (activity == null) {
            Log.v("ttaylor", "CustomPopupWindow.changeBackgroundAlpha()" + "  host activity is null");
            return;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        //dismiss this window when clicking outside of itself
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            dismiss();
            return true;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    /**
     * this method make it possible that clicking event is implemented outside of PopupWindow
     */
    public interface OnItemClickListener {
        void onWindowItemClick(View view);
    }

    /**
     * convert touch event into click event
     */
    private class GestureListener implements GestureDetector.OnGestureListener {

        private static final int INVALID_POSITION = -1;
        private ViewGroup viewGroup;

        public GestureListener(ViewGroup viewGroup) {
            super();
            this.viewGroup = viewGroup;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.v("ttaylor", "GestureListener.onSingleTapUp()" + "  event=" + e);
            int x = (int) e.getX();
            int y = (int) e.getY();
            int position = pointToPosition(viewGroup, x, y);
            Log.v("ttaylor", "GestureListener.onSingleTapUp()" + "  click position=" + position);
            if (position != INVALID_POSITION) {
                View item = viewGroup.getChildAt(position);
                if (onItemClickListener != null) {
                    onItemClickListener.onWindowItemClick(item);
                    dismiss();
                }
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        /**
         * convert touch point into the position of window's children
         * @param viewGroup
         * @param x
         * @param y
         * @return
         */
        public int pointToPosition(ViewGroup viewGroup, int x, int y) {
            if (viewGroup == null) {
                return INVALID_POSITION;
            }
            Rect frame = mTouchFrame;
            if (frame == null) {
                mTouchFrame = new Rect();
                frame = mTouchFrame;
            }

            final int count = viewGroup.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == View.VISIBLE) {
                    child.getHitRect(frame);
                    if (frame.contains(x, y)) {
                        return i;
                    }
                }
            }
            return INVALID_POSITION;
        }
    }
}