package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/11/13.
 */

public class ViewPagerActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);
        MyPagerAdapter adapter = new MyPagerAdapter(prepareViews()) ;
        ViewPager vp = ((ViewPager) findViewById(R.id.vp)) ;
        vp.setAdapter(adapter);
    }

    private List<View> prepareViews() {
        List<View> views = new ArrayList<>() ;
        for (int i = 0 ; i < 3 ; i++){
            TextView tv = new TextView(this) ;
            tv.setText(""+i);
            tv.setTextSize(30);
            views.add(tv) ;
        }
        return views ;
    }


    private class MyPagerAdapter extends PagerAdapter {

        private List<View> views;

        public MyPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (views != null && views.size() > position) {
                container.removeView(views.get(position));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position) ;
        }
    }
}
