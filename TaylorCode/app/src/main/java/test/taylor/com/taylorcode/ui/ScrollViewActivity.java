package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import test.taylor.com.taylorcode.R;

/**
 * Created on 17/8/20.
 */

public class ScrollViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //case1:wrap ScrollView with RelativeLayout for add scrolling ability
        setContentView(R.layout.scrollview_activity);
    }
    //master branch change
}
