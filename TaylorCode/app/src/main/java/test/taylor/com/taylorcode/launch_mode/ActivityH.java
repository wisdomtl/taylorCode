package test.taylor.com.taylorcode.launch_mode;

import android.os.Bundle;

/**
 * Created on 2018/3/5.
 */

public class ActivityH extends ActivityBase {

    @Override
    String getClassName() {
        return "ActivityH";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("SINGLE_INSTANCE_EXIST_ACTIVITY");
    }

    /**
     * launch mode9:SINGLE_INSTANCE
     * restart an singleInstance activity with value kept in intent
     * the lifecycle is like this:
     * onNewIntent()
     * onRestart()
     * onStart()
     * onResume()
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityG.class,null, "from ActivityH");
    }
}
