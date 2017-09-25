package test.taylor.com.taylorcode.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;
import test.taylor.com.taylorcode.R;

/**
 * Created by taylor  on 16/7/25.
 */
public class MultipleTaskEndingActivity extends Activity implements View.OnClickListener {
    public static final String TASK1 = "task1: ";
    public static final String TASK2 = "task2: ";
    private final int TASK1_COUNT = 3000;
    private final int TASK2_COUNT = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = new Button(this) ;
        btn.setOnClickListener(this);
        setContentView(btn);
    }

    /**
     * case2: create Observable from an time-consuming task
     * @param tag
     * @param cost
     * @return
     */
    private Observable<String> generateAsyncObservable(final String tag, final long cost) {
        /**
         *create Observable by SyncOnSubscribe
         */
        return Observable.create(new SyncOnSubscribe<Integer, String>() {
            @Override
            protected Integer generateState() {
                return 1;
            }

            @Override
            protected Integer next(Integer state, Observer<? super String> observer) {
                int sum = 0;
                for (int i = 0; i < cost; i++) {
                    //prove parallel
                    Log.v("taylor ttmulti-end", "call() " + tag + i);
                    sum += i;
                }
                Log.v("ttangliang ttmulti-end", "MultipleTaskEndingActivity.next() " + " thread id=" + Thread.currentThread().getId());
                observer.onNext(tag + " result:" + sum + " has finished");
                observer.onCompleted();
                return 1;
            }
        }).subscribeOn(Schedulers.newThread());
        /**
         *create Observable by AsyncOnSubscribe
         */
//        return Observable.create(new AsyncOnSubscribe<Integer, String>() {
//            @Override
//            protected Integer generateState() {
//                return null;
//            }
//
//            @Override
//            protected Integer next(Integer state, long requested, Observer<Observable<? extends String>> observer) {
//                int sum = 0;
//                for (int i = 0; i < cost; i++) {
//                    //prove parallel
//                    Log.v("taylor ttmulti-end", "call() " + tag + i);
//                    sum += i;
//                }
//                Log.v("ttangliang ttmulti-end", "MultipleTaskEndingActivity.next() " + " thread id=" + Thread.currentThread().getId());
//                observer.onNext(Observable.just(tag + " result:" + sum + " has finished"));
//                observer.onCompleted();
//                return null;
//            }
//        }).subscribeOn(Schedulers.newThread());
        /**
         *create Observable by OnSubscribe
         */
//        return Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                int sum = 0 ;
//                for (int i = 0; i < cost; i++) {
//                    //prove parallel
//                    Log.v("taylor ttmulti-end", "call() " + tag + i);
//                    sum += i ;
//                }
//                subscriber.onNext(tag +" result:" + sum  + " has finished");
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.newThread());
    }

    /**
     * case1: multiply async task execute in parallel and notify when the last task is done
     */
    private void demoMultipleAsyncTask() {
        Observable asyncObservable1 = generateAsyncObservable(TASK1, TASK1_COUNT);
        Observable asyncObservable2 = generateAsyncObservable(TASK2, TASK2_COUNT);

        asyncObservable1.mergeWith(asyncObservable2).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.v("taylor ttmulti-end", "Tasks.onCompleted() " + " thread id=" + Thread.currentThread().getId());
            }

            @Override
            public void onError(Throwable e) {
                Log.v("taylor ttmulti-end", "Tasks.onError() " + e.toString() + " thread id=" + Thread.currentThread().getId());
            }

            @Override
            public void onNext(String o) {
                Log.v("taylor ttmulti-end", o.toString() + " thread id=" + Thread.currentThread().getId());
            }
        });

//        Observable.merge(asyncObservable2, asyncObservable1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Log.v("taylor ttmulti-end", "Tasks.onCompleted() " + " ");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.v("taylor ttmulti-end", "Tasks.onError() " + e.toString());
//            }
//
//            @Override
//            public void onNext(String o) {
//                Log.v("taylor ttmulti-end", o.toString());
//            }
//        });
    }

    private void demoMultipleAsyncTaskOldStyle() {
        final int MSG_TASK1 = 1;
        final int MSG_TASK2 = 2;

        final Handler handler = new Handler() {

            private boolean isTask1End;
            private boolean isTask2End;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_TASK1:
                        isTask1End = true;
                        if (checkEnding()) {
                            //onComplete()
                        }
                        break;
                    case MSG_TASK2:
                        isTask2End = true;
                        if (checkEnding()) {
                            //onComplete()
                        }
                        break;
                    default:
                        break;
                }
            }

            private boolean checkEnding() {
                return isTask2End && isTask1End;
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 300; i++) {
                    sum += i;
                }
                handler.sendEmptyMessage(MSG_TASK2);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < TASK2_COUNT; i++) {
                    sum += i;
                }
                handler.sendEmptyMessage(MSG_TASK1);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        //case1:multiple time-consuming task execute parallel,and notified when the last task is done
        demoMultipleAsyncTask();
//                demoMultipleAsyncTaskOldStyle();
    }
}
