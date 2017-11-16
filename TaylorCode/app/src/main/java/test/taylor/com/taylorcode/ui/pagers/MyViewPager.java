package test.taylor.com.taylorcode.ui.pagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by taylor on 2017/11/16.
 */

public class MyViewPager extends ViewPager {

    /**
     * case1:control the scrolling ability of ViewPager by a Boolean
     */
    private boolean scrollable ;

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public MyViewPager (Context context , AttributeSet attrs){
        super(context,attrs) ;
    }

    public MyViewPager(Context context) {
        super(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!scrollable){
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!scrollable){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
