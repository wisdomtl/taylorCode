package test.taylor.com.taylorcode.ui.pagers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/11/13.
 */

public class ViewPagerActivity extends Activity {
    public static final int[] PAGE_LAYOUT_ID = new int[]{R.layout.pager1, R.layout.page2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);
        MyPagerAdapter adapter = new MyPagerAdapter(prepareViews());
        MyViewPager vp = ((MyViewPager) findViewById(R.id.vp));
        vp.setAdapter(adapter);
        vp.setScrollable(false);
        setIndicator(vp);
        TextView tv = ((TextView) adapter.findViewById(R.id.tv_pager1));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPagerActivity.this, "tv1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setIndicator(ViewPager viewPager) {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.mi);
        Indicator indicator = new Indicator(this);
        indicator.setCircleCount(PAGE_LAYOUT_ID.length);
        indicator.setLongColor(Color.DKGRAY);
        indicator.setShortColor(Color.GRAY);
        magicIndicator.setNavigator(indicator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private List<View> prepareViews() {
        List<View> views = new ArrayList<>();
        for (int layoutId : PAGE_LAYOUT_ID) {
            View page = LayoutInflater.from(this).inflate(layoutId, null);
            views.add(page);
        }
        return views;
    }


    /**
     * case1:the basic use of PagerAdapter extension
     */
    private class MyPagerAdapter extends PagerAdapter {

        private List<View> views;

        /**
         * case2:find views in ViewPagers
         * @param id
         * @return
         */
        public View findViewById(int id) {
            for (View view : views) {
                View findView = view.findViewById(id);
                if (findView != null) {
                    return findView;
                }
            }
            return null;
        }

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
            return views.get(position);
        }
    }


}
