package test.taylor.com.taylorcode.system;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created on 2017/12/5.
 */

public class AppStatusActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new AppStatusService(this).setActivityController();
        //case1:monitor the other apps running status
        new AppStatusService(this).registerProcessObserver();
        new AppStatusService(this).registerUidObserver();
    }
}
