package test.taylor.com.taylorcode.ui.pagers;

/**
 * Created by taylor on 2017/11/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.util.DimensionUtil;

/**
 * the indicator of ViewPager
 */
public class Indicator extends View implements IPagerNavigator {

    private int longColor;
    private int shortColor;
    private int strokeWidth;
    private int tabIndex;
    private int tabCount;
    private float longTabWidth;
    private float shortTabWidth;
    private float tabGap;
    private float rx;
    private float ry;
    private Paint paint;
    private List<IndicatorInfo> tabs = new ArrayList<>();

    public Indicator(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        strokeWidth = DimensionUtil.dp2px(1);
        longTabWidth = DimensionUtil.dp2px(17);
        shortTabWidth = DimensionUtil.dp2px(6);
        tabGap = DimensionUtil.dp2px(6);
        tabIndex = 0;
        rx = DimensionUtil.dp2px(5);
        ry = DimensionUtil.dp2px(5);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawShortTabs(canvas);
        drawLongTab(canvas);
    }

    private void drawShortTabs(Canvas canvas) {
        paint.setColor(shortColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);
        for (int i = 0, j = tabs.size(); i < j; i++) {
            IndicatorInfo tab = tabs.get(i);
            canvas.drawRoundRect(tab.getLeft(), getTop(), tab.getRight(), getBottom(), rx, ry, paint);

        }
    }

    private void drawLongTab(Canvas canvas) {
        paint.setColor(longColor);
        paint.setStyle(Paint.Style.FILL);
        if (tabs.size() > tabIndex) {
            IndicatorInfo tab = tabs.get(tabIndex);
            canvas.drawRoundRect(tab.getLeft(), getTop(), tab.getRight(), getBottom(), rx, ry, paint);
        }
    }

    private void prepareTabs() {
        tabs.clear();
        if (tabCount > 0) {
            for (int i = 0; i < tabCount; i++) {
                float left;
                if (i == 0) {
                    left = getLeft();
                } else {
                    left = tabs.get(i - 1).getRight() + tabGap;
                }
                IndicatorInfo tab = new IndicatorInfo(left, tabIndex == i ? longTabWidth : shortTabWidth);
                tabs.add(tab);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        tabIndex = position;
        prepareTabs();
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onAttachToMagicIndicator() {

    }

    @Override
    public void onDetachFromMagicIndicator() {

    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        prepareTabs();
    }

    public void setLongColor(int longTabColor) {
        this.longColor = longTabColor;
    }

    public void setShortColor(int shortTabColor) {
        this.shortColor = shortTabColor;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public int getCircleCount() {
        return tabCount;
    }

    public void setCircleCount(int count) {
        tabCount = count;  // 此处不调用invalidate，让外部调用notifyDataSetChanged
    }
}
