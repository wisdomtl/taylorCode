package test.taylor.com.taylorcode.ui.custom_view.selector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import test.taylor.com.taylorcode.R;

public class SelectorDemoActivity extends Activity implements Selector.OnSelectorStateListener {
    private SelectorGroup selectorGroup = new SelectorGroup();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_demo_activity);
        initView();
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
