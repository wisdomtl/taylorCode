package test.taylor.com.taylorcode.ui.viewpager3;

import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

final class FakeDrag {
    private final ViewPager2 mViewPager;
    private final ScrollEventAdapter mScrollEventAdapter;
    private final RecyclerView mRecyclerView;

    private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity;
    private float mRequestedDragDistance;
    private int mActualDraggedDistance;
    private long mFakeDragBeginTime;

    FakeDrag(ViewPager2 viewPager, ScrollEventAdapter scrollEventAdapter,
             RecyclerView recyclerView) {
        mViewPager = viewPager;
        mScrollEventAdapter = scrollEventAdapter;
        mRecyclerView = recyclerView;
    }

    boolean isFakeDragging() {
        return mScrollEventAdapter.isFakeDragging();
    }

    @UiThread
    boolean beginFakeDrag() {
        if (mScrollEventAdapter.isDragging()) {
            return false;
        }
        mRequestedDragDistance = mActualDraggedDistance = 0;
        mFakeDragBeginTime = SystemClock.uptimeMillis();
        beginFakeVelocityTracker();

        mScrollEventAdapter.notifyBeginFakeDrag();
        if (!mScrollEventAdapter.isIdle()) {
            // Stop potentially running settling animation
            mRecyclerView.stopScroll();
        }
        addFakeMotionEvent(mFakeDragBeginTime, MotionEvent.ACTION_DOWN, 0, 0);
        return true;
    }

    @UiThread
    boolean fakeDragBy(float offsetPxFloat) {
        if (!mScrollEventAdapter.isFakeDragging()) {
            // Can happen legitimately if user started dragging during fakeDrag and app is still
            // sending fakeDragBy commands
            return false;
        }
        // Subtract the offset, because content scrolls in the opposite direction of finger motion
        mRequestedDragDistance -= offsetPxFloat;
        // Calculate amount of pixels to scroll ...
        int offsetPx = Math.round(mRequestedDragDistance - mActualDraggedDistance);
        // ... and keep track of pixels scrolled so we don't get rounding errors
        mActualDraggedDistance += offsetPx;
        long time = SystemClock.uptimeMillis();

        boolean isHorizontal = mViewPager.getOrientation() == ORIENTATION_HORIZONTAL;
        // Scroll deltas use pixels:
        final int offsetX = isHorizontal ? offsetPx : 0;
        final int offsetY = isHorizontal ? 0 : offsetPx;
        // Motion events get the raw float distance:
        final float x = isHorizontal ? mRequestedDragDistance : 0;
        final float y = isHorizontal ? 0 : mRequestedDragDistance;

        mRecyclerView.scrollBy(offsetX, offsetY);
        addFakeMotionEvent(time, MotionEvent.ACTION_MOVE, x, y);
        return true;
    }

    @UiThread
    boolean endFakeDrag() {
        if (!mScrollEventAdapter.isFakeDragging()) {
            // Happens legitimately if user started dragging during fakeDrag
            return false;
        }

        mScrollEventAdapter.notifyEndFakeDrag();

        // Compute the velocity of the fake drag
        final int pixelsPerSecond = 1000;
        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(pixelsPerSecond, mMaximumVelocity);
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();
        // And fling or snap the ViewPager2 to its destination
        if (!mRecyclerView.fling(xVelocity, yVelocity)) {
            // Velocity too low, trigger snap to page manually
            mViewPager.snapToPage();
        }
        return true;
    }

    private void beginFakeVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
            final ViewConfiguration configuration = ViewConfiguration.get(mViewPager.getContext());
            mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void addFakeMotionEvent(long time, int action, float x, float y) {
        final MotionEvent ev = MotionEvent.obtain(mFakeDragBeginTime, time, action, x, y, 0);
        mVelocityTracker.addMovement(ev);
        ev.recycle();
    }
}
