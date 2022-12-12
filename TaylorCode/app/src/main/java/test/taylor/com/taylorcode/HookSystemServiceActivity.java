package test.taylor.com.taylorcode;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IntDef;

import java.util.ArrayList;
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

        String vdd = "01";
       int ddd =  Integer.parseInt(vdd);
       Log.v("ttaylor","ddd="+ddd);
        /**
         * case: @IntDef, limit the value before compile
         */
//        setType(3);// there is an error
        Solution s = new Solution();
        int[] nnn = new int[3];
        nnn[0] = 1;
        nnn[1] = 1;
        nnn[2] = 2;
        s.removeDuplicates(nnn);
    }

    /**
     * case: use new annotation
     * @param type
     */
    private void setType(@MyType int type){
        GoodDialogFragmentKt.ddd(null);
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
    public int removeDuplicates(int[] nums) {
        int j, k, size = nums.length;
        for(int i = 0; i< nums.length;i++){
            j = i+1;
            while(j< nums.length && nums[j] == nums[i]) j++;
            int gap = j -i -1;
            k = i+1;
            while(j < size){
                nums[k++]=nums[j++];
            }
            size = size - gap;
        }
        return size;
    }
}