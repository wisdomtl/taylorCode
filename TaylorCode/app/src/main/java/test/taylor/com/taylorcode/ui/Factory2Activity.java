package test.taylor.com.taylorcode.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import test.taylor.com.taylorcode.R;

public class Factory2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //hook view case1:hook view from LayoutInflater which will affect all the view in this activity
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                //hook view case2: calculate view creating time consume
                long start = System.currentTimeMillis();
                View view = delegate.createView(parent, name, context, attrs);
                long end = System.currentTimeMillis();
                Log.v("ttaylor", "Factory2Activity.onCreateView()" + "  creating view time consuming=" + (end - start)+ " ms");
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(Color.BLACK);
                }
                return view;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory2_activity);

        findViewById(R.id.btn_start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Factory2Activity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.factory_view, null);
        ((ViewGroup) findViewById(R.id.container)).addView(view);
    }
}
