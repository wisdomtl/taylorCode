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
        Solution s = new Solution();
        s.threeSum(arr);
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

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> rets = new ArrayList();
        if( nums.length < 3) return rets;
        Arrays.sort(nums);
        for(int i =0;i<nums.length-2;i++){
            if(nums[i+1] == nums[i]) continue;
            int left = i+1;
            int right = nums.length -1;
            while(left< right){
                int sum = nums[left]+nums[right]+nums[i];
                if(sum == 0){
                    List<Integer> ret = new ArrayList();
                    ret.add(nums[left]);
                    ret.add(nums[right]);
                    ret.add(nums[i]);
                    rets.add(ret);
                    while(nums[left+1]==nums[left] && left < right) left++;
                    left++;
                    while(nums[right-1] == nums[right] && right > left) right--;
                    right--;
                }else if(sum <0) {
                    left++;
                } else {
                    right--;
                }
            }

        }
        return rets;
    }

}