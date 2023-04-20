package test.taylor.com.taylorcode;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.IntDef;

import org.jcheck.generator.primitive.IntegerGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        int[] nnn = new int[]{1,22,7,26,3,4,5,23,2};
        s.heapSort(nnn);
        Log.i("ttaylor", ".()");
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
    public void heapSort(int[] nums) {
        List<? extends  Number> numbers = new ArrayList<Integer>();
        numbers.get(1);

        List<? super  Number> nuuu = new ArrayList<Number>();
        nuuu.add(1);

        for(int i=nums.length/2-1;i>=0;i--){
            shiftDown(nums,i,nums.length);
        }

        for(int i=nums.length-1;i>=0;i--){
            swap(nums,0,i);
            shiftDown(nums,0,i);
        }
        Log.i("ttaylor", ".()");
    }

    public void shiftDown(int[] arr,int low,int len){
        int left = 2*low+1;
        int father=  low;
        while(left<len){
            int large = left+1 < len && arr[left] > arr[left+1] ? left : left+1;
            if(arr[large]>arr[father]) swap(arr,large,father);
            else break;
            father = large;
            left = 2*father +1;
        }
    }

    public void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

//    public void heapSort(int[] nums) {
//        // 构建大顶堆
//        for (int i = nums.length / 2 - 1; i >= 0; i--) {
//            shiftDown(nums, i, nums.length);
//        }
//
//        // 交换堆顶元素与末尾元素,然后调整堆
//        for (int i = nums.length - 1; i > 0; i--) {
//            swap(nums, 0, i);
//            shiftDown(nums, 0, i);
//            Log.i("ttaylor", "heapSort="+nums[0]);
//        }
//    }
//
//    // 向下调整堆
//    private void shiftDown(int[] nums, int i, int len) {
//        int left = 2 * i + 1;
//        while (left < len) {
//            int large = left + 1 < len && nums[left + 1] > nums[left] ? left + 1 : left;
//            large = nums[large] > nums[i] ? large : i;
//            if (large == i) break;
//            swap(nums, i, large);
//            i = large;
//            left = 2 * i + 1;
//        }
//    }

    private long firstSendTimestamp = 0;
    int sendCount = 0;
    int oneMinute = 1000 * 60;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable run  = new Runnable() {
        @Override
        public void run() {
            sendCount = 0 ;
        }
    };

   public boolean isValid(){
       sendCount++;
       if(sendCount <= 10){
           handler.removeCallbacks(run);
           handler.postDelayed(run,oneMinute);
           return true;
       }else {
           return false;
       }
   }

























}