package test.taylor.com.taylorcode.launch_mode;


import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2018/2/27.
 */

public class ActivityC extends ActivityBase {

    @Override
    String getClassName() {
        return "ActivityC";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("FLAG_ACTIVITY_SINGLE_TOP");
    }

    /**
     * launch mode case2
     * the initial stack is A--(start B by standard)--A,B--(start C by standard)--A,B,C--(start B by SINGLE_TOP)--A,B,C,B
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityB.class, Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityB.class,Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
