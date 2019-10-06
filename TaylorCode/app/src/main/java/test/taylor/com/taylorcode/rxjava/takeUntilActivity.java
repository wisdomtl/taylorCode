package test.taylor.com.taylorcode.rxjava;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by taylor on 2017/11/2.
 */

public class takeUntilActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = new Button(this);
        btn.setText("takeUntil");
        btn.setOnClickListener(this);
        setContentView(btn);
    }

    @Override
    public void onClick(View v) {
        takeUntil(getObservable());
    }


    //case1:operator [takeUntil] take the data of one Observable until the other Observable emit an item
    private void takeUntil(Observable flow2) {
        //flow1:emit data continuously ,waiting signal to stop
        final Integer[] datas = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
        Observable.interval(2, TimeUnit.SECONDS)
                .takeUntil(flow2)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.v("ttaylor", "flow1.onCompleted(): ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("ttaylor", "flow1.onError(): error=" + e);
                    }

                    @Override
                    public void onNext(Long integer) {
                        Log.v("ttaylor", "flow1.onNext(): data=" + integer);
                    }
                });
    }

    private Observable<String> getObservable() {
        return Observable.just("end").delay(6, TimeUnit.SECONDS);
    }
}
