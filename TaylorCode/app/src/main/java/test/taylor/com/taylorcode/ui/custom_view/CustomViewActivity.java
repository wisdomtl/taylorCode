package test.taylor.com.taylorcode.ui.custom_view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import test.taylor.com.taylorcode.R;

public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_activity);
    }
}
