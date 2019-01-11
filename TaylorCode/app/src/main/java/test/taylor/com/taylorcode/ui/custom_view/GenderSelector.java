package test.taylor.com.taylorcode.ui.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;

public class GenderSelector extends Selector {
    private TextView tvTitle;
    private ImageView ivIcon;
    private ImageView ivSelector;

    public GenderSelector(Context context) {
        super(context);
    }

    public GenderSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GenderSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBindView(String text, int iconResId, int indicatorResId, int textColorResId, int textSize) {
        if (tvTitle != null) {
            tvTitle.setText(text);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            tvTitle.setTextColor(textColorResId);
        }
        if (ivIcon != null) {
            ivIcon.setImageResource(iconResId);
        }
        if (ivSelector != null) {
            ivSelector.setImageResource(indicatorResId);
        }
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.selector, null);
        tvTitle = view.findViewById(R.id.tv_title);
        ivIcon = view.findViewById(R.id.iv_icon);
        ivSelector = view.findViewById(R.id.iv_selector);
        return view;
    }
}
