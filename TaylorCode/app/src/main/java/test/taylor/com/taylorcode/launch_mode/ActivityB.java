package test.taylor.com.taylorcode.launch_mode;


import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2018/2/27.
 */

public class ActivityB extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("STANDARD");
        btn2.setText("FLAG_ACTIVITY_SINGLE_TOP");
        btn3.setText("STANDARD");
        btn4.setText("FLAG_ACTIVITY_NEW_TASK");
    }

    @Override
    String getClassName() {
        return "ActivityB";
    }

    /**
     * launch mode case1:FLAG_ACTIVITY_SINGLE_TOP
     * the initial stack is A--(start B by standard)--A,B--(start B by standard)--A,B,B
     * vs
     * the initial stack is A--(start B by standard)--A,B--(start B by SINGLE_TOP)--A,B
     * and the lifecycle of B is like the following:
     * ActivityB.onPause()
     * ActivityB.onNewIntent()
     * ActivityB.onResume()
     * ActivityB.onPostResume()
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityB.class, null, null);
    }

    /**
     * launch mode case1:FLAG_ACTIVITY_SINGLE_TOP
     * the initial stack is A--(start B by standard)--A,B--(start B by standard)--A,B,B
     * vs
     * the initial stack is A--(start B by standard)--A,B--(start B by SINGLE_TOP)--A,B
     * and the lifecycle of B is like the following:
     * ActivityB.onPause()
     * ActivityB.onNewIntent()
     * ActivityB.onResume()
     * ActivityB.onPostResume()
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityB.class, Intent.FLAG_ACTIVITY_SINGLE_TOP, null);
    }

    /**
     * launch mode case2
     * the initial stack is A--(start B by standard)--A,B--(start C by standard)--A,B,C--(start B by SINGLE_TOP)--A,B,C,B
     */
    @Override
    public void onButton3Click() {
        super.onButton3Click();
        startActivity(ActivityC.class, null, null);
    }

    @Override
    public void onButton4Click() {
        super.onButton4Click();
        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
    }
}
