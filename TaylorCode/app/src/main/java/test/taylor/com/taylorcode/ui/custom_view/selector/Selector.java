package test.taylor.com.taylorcode.ui.custom_view.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import test.taylor.com.taylorcode.R;

/**
 * it is a customized view acts like a checkbox.
 * it can be selected or unselected, the background will change accordingly. wrapping this business logic into a single view for clean code in Fragment
 */
public abstract class Selector extends FrameLayout implements View.OnClickListener {
    /**
     * click listener for Selector
     */
    private OnSelectorStateListener stateListener;
    /**
     * the unique tag for a selector
     */
    private String tag;
    /**
     * the group which this Selector belongs to
     */
    private SelectorGroup selectorGroup;

    public Selector(Context context) {
        super(context);
        initView(context, null);
    }

    public Selector(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public Selector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //inflate views
        View view = onCreateView();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(view, params);
        this.setOnClickListener(this);

        //read declared attributes
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Selector);
            String text = typedArray.getString(R.styleable.Selector_text);
            int iconResId = typedArray.getResourceId(R.styleable.Selector_img, 0);
            int selectorResId = typedArray.getResourceId(R.styleable.Selector_indicator, 0);
            int textColor = typedArray.getColor(R.styleable.Selector_text_color, Color.parseColor("#FF222222"));
            int textSize = typedArray.getInteger(R.styleable.Selector_text_size, 15);
            tag = typedArray.getString(R.styleable.Selector_tag);
            onBindView(text, iconResId, selectorResId, textColor, textSize);
            typedArray.recycle();
        }
    }

    public Selector setSelectorGroup(SelectorGroup selectorGroup) {
        this.selectorGroup = selectorGroup;
        selectorGroup.addSelector(this);
        return this;
    }

    /**
     * bind the resource to the view which is created from {@link #onCreateView()}
     *
     * @param text           the title of Selector
     * @param iconResId      the image of Selector
     * @param indicatorResId the image show when Selector is in selected state
     * @param textColorResId text color of Selector's text
     * @param textSize       text size of Selector's text
     */
    protected abstract void onBindView(String text, int iconResId, int indicatorResId, int textColorResId, int textSize);

    /**
     * design how the selector looks like
     *
     * @return
     */
    protected abstract View onCreateView();

    public String getTag() {
        return tag;
    }

    public Selector setOnSelectorStateListener(OnSelectorStateListener stateListener) {
        this.stateListener = stateListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        boolean isSelect = switchSelector();
        if (selectorGroup != null) {
            selectorGroup.setSelected(this);
        }
        if (stateListener != null) {
            stateListener.onStateChange(this, isSelect);
        }
    }

    public boolean switchSelector() {
        boolean isSelect = this.isSelected();
        this.setSelected(!isSelect);
        onSwitchSelected(!isSelect);
        return !isSelect;
    }

    protected abstract void onSwitchSelected(boolean isSelect);

    @Override
    public int hashCode() {
        return this.tag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Selector) {
            return ((Selector) obj).tag.equals(this.tag);
        }
        return false;
    }

    public interface OnSelectorStateListener {
        /**
         * it will be invoked when Selector is selected or unselected
         *
         * @param selector the Selector itself
         * @param isSelect the state of Selector
         */
        void onStateChange(Selector selector, boolean isSelect);
    }
}
