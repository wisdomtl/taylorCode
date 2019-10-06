package test.taylor.com.taylorcode.rxjava.create_observable;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.AsyncOnSubscribe;
import rx.schedulers.Schedulers;

/**
 * Created on 17/9/17.
 */

public class CreateObservableActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("taylor ttobservable", "CreateObservableActivity.onCreate() " + " ");
        //Observable.from()
        fromCallable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.v("taylor ttfromcallable", "CreateObservableActivity.onCompleted() " + " ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("taylor  ttfromcallable", "CreateObservableActivity.onError() " + " error=" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.v("taylor ttfromcallable", "CreateObservableActivity.onNext() " + " result=" + s);
                    }
                });

        //Observable.create()
        createAsyncOnSubscribe();

        //Observable.just()
        just();

    }

    private Observable<String> fromCallable() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //RxJava will invoke onNext , onError() and onComplete() internally
                return loadFromWeb("aaa");
            }
        });
    }

    private String loadFromWeb(String param) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param + " by Taylor";
    }

    /**
     * multiple value emitted by one Observable
     *
     * @return
     */
    private Observable<Object> createAsyncOnSubscribe() {
        return Observable.create(new AsyncOnSubscribe<Integer, Object>() {
            @Override
            protected Integer generateState() {
                return null;
            }

            @Override
            protected Integer next(Integer state, long requested, Observer<Observable<?>> observer) {
                return null;
            }
        });
    }

    private void from() {

    }

    private void just() {

    }
}
