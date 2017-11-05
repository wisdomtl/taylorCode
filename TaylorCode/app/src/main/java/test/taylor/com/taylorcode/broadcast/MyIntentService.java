package test.taylor.com.taylorcode.broadcast;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

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
    }
}
