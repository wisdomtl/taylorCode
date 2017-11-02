package test.taylor.com.taylorcode.innerclass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/10/29.
 */

public class InnerClassActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_class_activity);
    }

    public void create(View view) {
        Log.v("ttaylor", "InnerClassActivity.create(): ");
        new OuterClass(1,2,'d');
    }

    public void clear(View view) {
        Log.v("ttaylor", "InnerClassActivity.clear(): ");
        OuterClass.clear();
    }

}
