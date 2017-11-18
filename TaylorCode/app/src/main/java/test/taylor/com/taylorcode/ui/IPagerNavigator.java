package test.taylor.com.taylorcode.ui;

/**
 * Created by taylor on 2017/11/15.
 */

public interface IPagerNavigator {

    //the interface to connect with ViewPager
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    //the interface to connect with ViewPager
    void onPageSelected(int position);

    //the interface to connect with ViewPager
    void onPageScrollStateChanged(int state);

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    void onAttachToMagicIndicator();

    /**
     * 当IPagerNavigator从MagicIndicator上移除时调用
     */
    void onDetachFromMagicIndicator();

    /**
     * ViewPager内容改变时需要先调用此方法，自定义的IPagerNavigator应当遵守此约定
     */
    void notifyDataSetChanged();
}
