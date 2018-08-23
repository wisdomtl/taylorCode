package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;

public class SpannableActivity extends Activity {

    private TextView tvDifferentTypeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spannable_activity);

        initView();
    }

    private void initView() {
        String text = "Check in successfully! \n You got 4 coins";
        tvDifferentTypeface = ((TextView) findViewById(R.id.tv_different_typeface));
        Spannable textSpan = new SpannableStringBuilder(text);
        textSpan.setSpan(new AbsoluteSizeSpan(50), 0, text.indexOf('!')+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(30), text.indexOf('!') + 2, text.length() , Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvDifferentTypeface.setText(textSpan);
    }
}
