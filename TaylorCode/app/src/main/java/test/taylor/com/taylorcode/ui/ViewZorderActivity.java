package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import test.taylor.com.taylorcode.R;

/**
 * Created on 17/8/19.
 */

public class ViewZorderActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_order_activity);
        //case1
        hide1thView() ;
    }

    /**
     * case1:the z order of the view defined in the 1 place im xml(LinearLayout) is bigger than the view defined in the 2 place
     */
    private void hide1thView() {
        //2th should be shown
        findViewById(R.id.tv_1th).setVisibility(View.GONE);
    }
}
