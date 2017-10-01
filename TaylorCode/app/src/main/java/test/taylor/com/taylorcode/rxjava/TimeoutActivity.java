package test.taylor.com.taylorcode.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created on 17/9/28.
 */

public class TimeoutActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeoutObservable();
    }

    private void timeoutObservable() {
        Observable.just(1)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return integer + 100;
                    }
                })
                .timeout(2000, TimeUnit.MILLISECONDS)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("ttangliang  tttimeout", "TimeoutActivity.call() ,error=" + throwable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.v("ttangliang tttimeout", "TimeoutActivity.onCompleted() " + " ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ttangliang  tttimeout", "TimeoutActivity.onError() error=" + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.v("ttangliang tttimeout", "TimeoutActivity.onNext() integer=" + integer);
                    }
                });
        //master branch change
    }
}
