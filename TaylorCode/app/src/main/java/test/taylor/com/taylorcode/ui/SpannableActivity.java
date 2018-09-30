package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import org.w3c.dom.Text;

import test.taylor.com.taylorcode.R;

public class SpannableActivity extends Activity {

    private TextView tvDifferentTypeface;
    private TextView tvDifferentStyle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spannable_activity);

        initView();
    }

    private void initView() {
        //spannable case1: different text size and different lines
        String text = "Check in successfully! \n You got 4 coins";
        tvDifferentTypeface = ((TextView) findViewById(R.id.tv_different_typeface));
        Spannable textSpan = new SpannableStringBuilder(text);
        textSpan.setSpan(new AbsoluteSizeSpan(50), 0, text.indexOf('!') + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(30), text.indexOf('!') + 2, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvDifferentTypeface.setText(textSpan);

        //spannable case2: different style bold and color
        String text2 = "Taylor @lisa we do want to love each other";
        tvDifferentStyle = ((TextView) findViewById(R.id.tv_different_style));
        Spannable textSpan2 = new SpannableStringBuilder(text2);
        int atIndex = text2.indexOf('@');
        textSpan2.setSpan(new StyleSpan(Typeface.BOLD), 0, atIndex - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan2.setSpan(new ForegroundColorSpan(Color.RED),atIndex,atIndex+3,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvDifferentStyle.setText(textSpan2);
    }
}
