package test.taylor.com.taylorcode.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.awt.font.TextAttribute;

import test.taylor.com.taylorcode.R;

/**
 * Created on 17/7/22.
 */

public class HandlerActivity extends Activity {
    private static final int DELAY_MILLISECOND = 30000;
    public static final int WHAT_1 = 1;
    public static final int WHAT_2 = 2;
    public static final String EXTRA_NUMBER = "extra-number";

    private TextView tv;
    /**
     *handler case1:intercept one type of message and keep the rest intact
     */
//    private MyHandler myHandler;

    /**
     * handler case2:
     * this member varialbe hold the reference of HandlerActivity
     * if there is no remaining message in the queue ,this activity wont leak memor.but is there is,memory leak will happen
     * if the handler is static ,it wont holder the reference of Activity
     */
    private static Handler memoryLeakHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private static Runnable memoryLeakRunnable = new Runnable() {
        @Override
        public void run() {
            Log.v("ttaylor", "HandlerActivity.run()" + "  after " + DELAY_MILLISECOND + " millisecond");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_activity);
        tv = (TextView) findViewById(R.id.tv);
        /**
         *
         handler case1
         */
//        myHandler = new MyHandler(myCallback);
//        Message message1 = myHandler.obtainMessage();
//        message1.what = WHAT_1;
//        Intent intent1 = new Intent();
//        intent1.putExtra(EXTRA_NUMBER, "origin 1");
//        message1.obj = intent1;
//        myHandler.sendMessage(message1);
//
//        Message message2 = myHandler.obtainMessage();
//        message2.what = WHAT_2;
//        Intent intent2 = new Intent();
//        intent2.putExtra(EXTRA_NUMBER, "origin 2");
//        message2.obj = intent2;
//        myHandler.sendMessage(message2);

        /**
         *
         handler case2:send an delay message,exit this activity,do GC,and dump memory to see whether this activity is still in memory
         if the runnable is anonymous,it will holde the reference of Activity
         */
//        memoryLeakHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.v("ttaylor", "HandlerActivity.run()" + "  after "+DELAY_MILLISECOND+" millisecond");
//            }
//        }, DELAY_MILLISECOND);
        /**
         *
         handler case3:static handler and static runnable,no Activity reference holding,no memory leak happened
         */
//        memoryLeakHandler.postDelayed(memoryLeakRunnable, DELAY_MILLISECOND);

        /**
         * handler case4:post delay with view,memory leak will happen
         */
        tv.postDelayed(memoryLeakRunnable,DELAY_MILLISECOND) ;
    }

    /**
     * handler case1:intercept one type of message and keep the rest intact
     */
//    private class MyHandler extends Handler {
//        public MyHandler(Callback callback) {
//            super(callback);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Intent intent = ((Intent) msg.obj);
//            String extraNumber;
//            switch (msg.what) {
//                case WHAT_1:
//                    extraNumber = intent.getStringExtra(EXTRA_NUMBER);
//                    Log.v("taylor ttHandler", "MyHandler.handleMessage() " + " WHAT_1.extra_number=" + extraNumber);
//                    break;
//                case WHAT_2:
//                    extraNumber = intent.getStringExtra(EXTRA_NUMBER);
//                    Log.v("taylor ttHandler", "MyHandler.handleMessage() " + " WHAT_2.extra_number=" + extraNumber);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

    /**
     * for intercepting one type of message sent to MyHandler,whose msg.what = 1
     */
//    private Handler.Callback myCallback = new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                //just intercept WHAT_1
//                case WHAT_1:
//                    Intent intent = ((Intent) msg.obj);
//                    intent.putExtra(EXTRA_NUMBER, "tampered 1");
//                    break;
//                default:
//                    break;
//            }
//            // this is the key point.
//            // if return true,this Callback.handleMessage() will take over Handler.handleMessage()
//            // if return false,this Callback.handleMessage() will be invoked before Handler.handleMessage()
//            // the reason why is hide in Handler.dispatchMessage()
//            return false;
//        }
//    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         *
         if this method is called,then no memory leak will happen
         */
//        memoryLeakHandler.removeCallbacksAndMessages(null);
    }
}
