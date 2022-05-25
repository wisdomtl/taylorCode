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
        int[] arr = new int[]{4,3,2,7,8,2,3,1};
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
    public int findIndex(int[] nums,int target) {
        for(int i=0;i<nums.length;i++){
            if(nums[i] == target) return i;
        }
        return -1;
    }
}