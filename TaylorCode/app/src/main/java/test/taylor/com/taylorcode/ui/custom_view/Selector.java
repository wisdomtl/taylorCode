package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;

/**
 * wrap business logic into a single view
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
    private OnSelectorClick onSelectorClick;


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

            tvTitle.setText(text);
            tvTitle.setTextColor(textColor);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            ivIcon.setImageResource(iconResId);
            ivSelect.setImageResource(selectorResId);

            typedArray.recycle();
        }
    }

    public void setOnSelectorClick(OnSelectorClick onSelectorClick) {
        this.onSelectorClick = onSelectorClick;
    }

    @Override
    public void onClick(View v) {
        switchSelector();
        if (onSelectorClick != null) {
            onSelectorClick.onClick();
        }
    }

    private void switchSelector() {
        boolean isSelect = ivSelect.isSelected();
        ivSelect.setSelected(!isSelect);
        //make Selector's select state accord with the ivSelect
        this.setSelected(!isSelect);
    }

    public interface OnSelectorClick {
        void onClick();
    }
}
