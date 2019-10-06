package test.taylor.com.taylorcode.rxjava;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import kotlin.Unit;
import test.taylor.com.taylorcode.R;

public class LoginActivity extends AppCompatActivity {
    public static final int VALID_CODE_LENGTH = 6;
    public static final int VALID_NUMBER_LENGTH = 11;
    public static final int COUNT_DOWN_SPAN_IN_MINUTE = 10;

    private EditText etNumber;
    private EditText etCode;
    private Button btnSend;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();

        Observable<Boolean> codeObservable = getLengthObservable(etCode, VALID_CODE_LENGTH);
        Observable<Boolean> numberObservable = getLengthObservable(etNumber, VALID_NUMBER_LENGTH);

        //1
        setupCode(numberObservable);

        //2
        setupLogin(codeObservable, numberObservable);

        //3
        setupCountdown();
    }

    private void setupCode(Observable<Boolean> numberObservable) {
        numberObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnSend.setEnabled(aBoolean);
            }
        });
    }

    private void setupLogin(Observable<Boolean> codeObservable, Observable<Boolean> numberObservable) {
        Observable.combineLatest(codeObservable, numberObservable, new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean aBoolean, Boolean aBoolean2) throws Exception {
                return aBoolean && aBoolean2;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnLogin.setEnabled(aBoolean);
            }
        });
    }

    @NonNull
    private void setupCountdown() {
        RxView.clicks(btnSend).throttleFirst(2, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        btnSend.setEnabled(false);
                        Observable.interval(0, 1, TimeUnit.SECONDS)
                                .take(COUNT_DOWN_SPAN_IN_MINUTE+1)
                                .map(new Function<Long, String>() {
                                    @Override
                                    public String apply(Long aLong) throws Exception {
                                        return "" + (COUNT_DOWN_SPAN_IN_MINUTE - aLong) + "s";
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(String string) {
                                        btnSend.setText(string);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        btnSend.setEnabled(true);
                                        btnSend.setText("SEND CODE");
                                    }
                                });
                    }
                });
    }

    private void initView() {
        etNumber = findViewById(R.id.et_phone_number);
        etCode = findViewById(R.id.et_verify_code);
        btnSend = findViewById(R.id.btn_send_code);
        btnLogin = findViewById(R.id.btn_login);
    }

    private Observable<Boolean> getLengthObservable(EditText et, int length) {
        return RxTextView.textChanges(et).map(new Function<CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence) throws Exception {
                return isValidNumber(charSequence, length);
            }
        });
    }

    private boolean isValidNumber(CharSequence ch, int length) {
        if (!TextUtils.isEmpty(ch) && ch.length() == length) {
            return true;
        } else {
            return false;
        }
    }

}
