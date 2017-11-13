package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RatingBar;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/11/13.
 */

public class RatingBarActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_activity);
    }
}
