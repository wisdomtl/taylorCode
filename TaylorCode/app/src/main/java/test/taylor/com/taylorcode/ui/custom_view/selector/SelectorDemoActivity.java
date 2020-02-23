package test.taylor.com.taylorcode.ui.custom_view.selector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import test.taylor.com.taylorcode.R;

public class SelectorDemoActivity extends Activity implements Selector.OnSelectorStateListener {
    private SelectorGroup selectorGroup = new SelectorGroup();

    private Boolean a1 = true;
    private Boolean[] aArray = new Boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_demo_activity);
        initView();

        //the value wont be changed
        testValueDeliver(a1);
        Log.v("ttaylor", "LockScreenActivity.onCreate()" + "  a = " + a1);

        //the value will be changed
        aArray[0] = true ;
        testValueDeliver(aArray);
        Log.v("ttaylor", "SelectorDemoActivity.onCreate()" + "  a array="+aArray[0]);
    }

    private void testValueDeliver(Boolean a) {
        a = false;
    }

    private void testValueDeliver(Boolean[] a) {
        a[0] = false;
    }

    private void initView() {
        Selector teenageSelector = findViewById(R.id.selector_10);
        Selector manSelector = findViewById(R.id.selector_20);
        Selector oldManSelector = findViewById(R.id.selector_30);

        teenageSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
        manSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
        oldManSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
    }

    @Override
    public void onStateChange(Selector selector, boolean isSelect) {
        String tag = selector.getTag();
        if (isSelect) {
            Toast.makeText(this, tag + " is selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, tag + " is unselected", Toast.LENGTH_SHORT).show();
        }
    }
}
