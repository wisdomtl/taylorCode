package test.taylor.com.taylorcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IntDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import test.taylor.com.taylorcode.proxy.system.FakeActivity;

public class HookSystemServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hook_system_service_activity);
        int[] arr = new int[]{-1,0,1,2,-1,-4};
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HookSystemServiceActivity.this, FakeActivity.class);
                startActivity(intent);
            }
        });
        /**
         * case: @IntDef, limit the value before compile
         */
//        setType(3);// there is an error

    }

    /**
     * case: use new annotation
     * @param type
     */
    private void setType(@MyType int type){

    }

    /**
     * case: define annotation
     */
    @IntDef({TYPE1, TYPE2})
    @interface MyType {
    }

    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;

}