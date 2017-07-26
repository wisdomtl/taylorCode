package test.taylor.com.taylorcode.proxy.system;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import test.taylor.com.taylorcode.R;

/**
 * Created on 17/7/26.
 */

public class FakeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_activity);
    }
}
