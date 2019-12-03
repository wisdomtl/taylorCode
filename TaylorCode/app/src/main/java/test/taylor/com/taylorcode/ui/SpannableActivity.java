package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import test.taylor.com.taylorcode.R;

public class SpannableActivity extends Activity {

    private TextView tvDifferentTypeface;
    private TextView tvDifferentStyle;
    private TextView tv3;
    private TextView tv4;

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
        String text2 = "Taylo6r @lisa we do want to love each other";
        tvDifferentStyle = ((TextView) findViewById(R.id.tv_different_style));
        Spannable textSpan2 = new SpannableStringBuilder(text2);
        int atIndex = text2.indexOf('@');
        textSpan2.setSpan(new StyleSpan(Typeface.BOLD), 0, atIndex - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan2.setSpan(new ForegroundColorSpan(Color.RED),atIndex,atIndex+3,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvDifferentStyle.setText(textSpan2);

        //spannable case3: different typeface without api limitation
        tv3 = findViewById(R.id.tv_3);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("我们 lo6ve 你").setSpan(new CharacterStyle(){
            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setTypeface(ResourcesCompat.getFont(SpannableActivity.this,R.font.gilroy_bold_4));
            }
        },3,8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv3.setText(ssb);


        //spannable case4: clickable spannable
        tv4 = findViewById(R.id.tv_4);
        SpannableStringBuilder ssb2 = new SpannableStringBuilder();
        ssb2.append("ttt 999 dddd").setSpan(new ClickableSpan(){
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(SpannableActivity.this,"click span clicked",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setTypeface(ResourcesCompat.getFont(SpannableActivity.this,R.font.gilroy_bold_4));
                textPaint.setTextSize(90);
            }
        },0,3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv4.setMovementMethod(LinkMovementMethod.getInstance());
        tv4.setHighlightColor(Color.TRANSPARENT);
        tv4.setText(ssb2);



        //case: measure text width in TextView
        tv4.post(() -> {
            TextPaint tp = tv4.getPaint();
            float width2 = tp.measureText(tv4.getText(),0,tv4.getText().length());
            Log.v("ttaylor", "SpannableActivity.initView()" + "  text width2 = "+width2+" view width="+tv4.getWidth());
        });


    }
}
