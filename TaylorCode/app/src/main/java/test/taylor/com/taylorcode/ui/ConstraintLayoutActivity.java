package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.View;

import test.taylor.com.taylorcode.R;

public class ConstraintLayoutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout_activity);

        final Group group = ((Group) findViewById(R.id.group1));
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.setVisibility(View.GONE);
            }
        });

        //ConstraintLayout  case7:one member of chain is gone,the rest will full the chain
        findViewById(R.id.btn5).setVisibility(View.GONE);
    }
}
