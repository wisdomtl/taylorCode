package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.util.AttributeSet;
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
     * Selector part3:selector indicator at right top of Selector
     */
    private ImageView ivSelect;
    /**
     * click listener for Selector
     */
    private OnSelectorClick onSelectorClick;

    public Selector(Context context) {
        super(context);
        initView();
    }


    public Selector(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Selector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.selector, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(view, params);
        tvTitle = view.findViewById(R.id.tv_title);
        ivIcon = view.findViewById(R.id.iv_icon);
        ivSelect = view.findViewById(R.id.iv_select);
        this.setOnClickListener(this);
    }


    public TextView getTitle() {
        return tvTitle;
    }

    public ImageView getIcon() {
        return ivIcon;
    }

    public ImageView getSelect() {
        return ivSelect;
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
    }

    public interface OnSelectorClick {
        void onClick();
    }
}
