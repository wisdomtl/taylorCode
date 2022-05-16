package test.taylor.com.taylorcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IntDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import test.taylor.com.taylorcode.proxy.system.FakeActivity;

public class HookSystemServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hook_system_service_activity);
        Stack<Integer> stack = new Stack<>();
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
        Solution s = new Solution();
        s.isPalindrome(129);
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


class Solution {
    public boolean isPalindrome(int x) {
        if(x<0)
            return false;
        int rem=0,y=0;
        int quo=x;
        /**
         * case: revert a number
         */
        while(quo!=0){
            rem=quo%10;
            y=y*10+rem;
            quo=quo/10;
        }
        return y==x;
    }
}