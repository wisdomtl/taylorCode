package test.taylor.com.taylorcode.ui.custom_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;

public class AgeSelector extends Selector {
    private TextView tvTitle;
    private ImageView ivIcon;
    private ImageView ivSelector;
    private ValueAnimator valueAnimator;

    public AgeSelector(Context context) {
        super(context);
    }

    public AgeSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AgeSelector(Context context, AttributeSet attrs, int defStyleAttr) {
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
            ivSelector.setAlpha(0);
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

    @Override
    protected void onSwitchSelected(boolean isSelect) {
        if (isSelect) {
            playSelectedAnimation();
        } else {
            playUnselectedAnimation();
        }
    }

    private void playUnselectedAnimation() {
        if (ivSelector == null) {
            return;
        }
        if (valueAnimator != null) {
            valueAnimator.reverse();
        }
    }

    private void playSelectedAnimation() {
        if (ivSelector == null) {
            return;
        }
        valueAnimator = ValueAnimator.ofInt(0, 255);
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ivSelector.setAlpha((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }
}
