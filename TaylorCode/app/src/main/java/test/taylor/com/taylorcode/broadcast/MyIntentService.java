package test.taylor.com.taylorcode.broadcast;

import android.app.IntentService;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import test.taylor.com.taylorcode.Constant;

/**
 * Created by taylor on 2017/11/5.
 */

public class MyIntentService extends IntentService {
    //this is a must ,or error will be in AndroidManifest.xml "no default constructor"
    public MyIntentService (){
        super("time-consuming task");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //time-consuming task
        Log.v("ttaylor", "MyIntentService.onHandleIntent(): time-consuming task start to run ,intent=" + intent +" ,thread-id="+Thread.currentThread().getId());
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("ttaylor", "MyIntentService.onHandleIntent(): time-consuming task is running ,i=" + i);
        }
        Log.v("ttaylor", "MyIntentService.onHandleIntent(): time-consuming task stops ,intent=" + intent);
        Intent intent1 = new Intent(Constant.ACTION_INTENT_SERVICE_END) ;
        //case3:notify when one task is done
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent1) ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //case2:when all the tasks in the IntentService is done,this method will be invoked
        Log.v("ttaylor", "MyIntentService.onDestroy(): ");
    }
}
