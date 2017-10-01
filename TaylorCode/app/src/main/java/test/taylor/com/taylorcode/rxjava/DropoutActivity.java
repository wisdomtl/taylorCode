package test.taylor.com.taylorcode.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created on 17/9/27.
 */

public class DropoutActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dropoutInObservable();
    }

    /**
     * dropout in observable flow for updating ui in half way
     */
    private void dropoutInObservable() {
        Observable.just(1)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        Log.v("ttangliang ttdropout", "DropoutActivity.flatMap().call() " + " thread id=" + Thread.currentThread().getId());
                        return Observable.just(integer + 20);
                    }
                })
                //switch to ui thread for updating ui in half way
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.v("ttangliang ttdropout", "DropoutActivity.doOnNext().call() dropout result=" + integer + " thread id=" + Thread.currentThread().getId());
                        setupUi(integer);
                    }
                })
                //switch to background thread for time-consuming operation
                .observeOn(Schedulers.computation())
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        int map = integer*100 ;
                        Log.v("ttangliang ttdropout", "DropoutActivity.map().call() dropout result=" + map + " thread id=" + Thread.currentThread().getId());
                        return map;
                    }
                })
                .subscribeOn(Schedulers.computation())
                //the final result will be in ui thread
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ttangliang  ttdropout", "DropoutActivity.onError() error=" + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.v("ttangliang ttdropout", "DropoutActivity.onNext() " + " result=" + integer);
                        updateUi(integer);
                    }
                });
    }

    /**
     * update ui
     *
     * @param integer
     */
    private void updateUi(Integer integer) {
        Log.v("ttangliang ttdropout", "DropoutActivity.updateUi() " + " result=" + integer + " thread id=" + Thread.currentThread().getId());
    }

    /**
     * initialize ui
     *
     * @param integer
     */
    private void setupUi(Integer integer) {
        Log.v("ttangliang ttdropout", "DropoutActivity.setupUi() " + " result=" + integer + " thread id=" + Thread.currentThread().getId());
    }
    //private branch change
}
