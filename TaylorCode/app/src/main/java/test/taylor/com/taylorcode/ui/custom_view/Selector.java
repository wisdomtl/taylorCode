package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.taylor.com.taylorcode.R;

/**
 * it is a customized view acts like a checkbox.
 * it can be selected or unselected, the background will change accordingly. wrapping this business logic into a single view for clean code in Fragment
 * this class couple with a layout (R.layout.selector)
 */
public class Selector extends FrameLayout implements View.OnClickListener {
    /**
     * Selector part2:title for Selector
     */
    private TextView tvTitle;
    /**
     * Selector part1:image for Selector
     */
    private ImageView ivIcon;
    /**
     * Selector part3:selector indicator shows selector state
     */
    private ImageView ivSelect;
    /**
     * click listener for Selector
     */
    private OnSelectorStateListener stateListener;

    private String tag;

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
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.selector, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(view, params);
        tvTitle = view.findViewById(R.id.tv_title);
        ivIcon = view.findViewById(R.id.iv_icon);
        ivSelect = view.findViewById(R.id.iv_selector);
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

            tvTitle.setText(text);
            tvTitle.setTextColor(textColor);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            ivIcon.setImageResource(iconResId);
            ivSelect.setImageResource(selectorResId);

            typedArray.recycle();
        }
    }

    public String getTag() {
        return tag;
    }

    public void setImage(@DrawableRes int id) {
        if (ivIcon != null) {
            ivIcon.setImageResource(id);
        }
    }

    public void setSelectorStateListener(OnSelectorStateListener stateListener) {
        this.stateListener = stateListener;
    }

    @Override
    public void onClick(View v) {
        boolean isSelect = switchSelector();
        if (stateListener != null) {
            stateListener.onStateChange(this, isSelect);
        }
    }

    public boolean switchSelector() {
        boolean isSelect = ivSelect.isSelected();
        ivSelect.setSelected(!isSelect);
        //make Selector's select state accord with the ivSelect
        this.setSelected(!isSelect);
        return !isSelect;
    }

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
        void onStateChange(Selector selector, boolean isSelect);
    }
}
