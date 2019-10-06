package test.taylor.com.taylorcode.try_catch_finally;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created on 17/7/25.
 */

public class ExceptionActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fun1();
        Log.v("taylor ttexception" , "ExceptionActivity.onCreate() "+ " after fun1") ;

        fun2();
        Log.v("taylor ttexception" , "ExceptionActivity.onCreate() "+ " after fun2") ;

        fun3();
        Log.v("taylor ttexception" , "ExceptionActivity.onCreate() "+ " after fun3") ;
    }

    private void fun1() {
        try {
            Log.v("taylor ttexcption", "ExceptionActivity.fun1() " + " try");
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("taylor ttexception", "ExceptionActivity.fun1() " + " catch");
        } finally {
            Log.v("taylor ttexception", "ExceptionActivity.fun1() " + " finally");
        }
        Log.v("taylor  ttexception" , "ExceptionActivity.fun1() "+ " after finally") ;
    }

    private void fun2(){
        try {
            Log.v("taylor ttexcption", "ExceptionActivity.fun2() " + " try");
            return;//it wont return until finally block is done
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("taylor ttexception", "ExceptionActivity.fun2() " + " catch");
        } finally {
            Log.v("taylor ttexception", "ExceptionActivity.fun2() " + " finally");
        }

        Log.v("taylor ttexception" , "ExceptionActivity.fun2() "+ " after finally") ;//this statement wont be invoked due to the return in fun2
    }


    private void fun3(){
        try {
            Log.v("taylor ttexcption", "ExceptionActivity.fun3() " + " try");
            throw new Exception() ;
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("taylor ttexception", "ExceptionActivity.fun3() " + " catch");
        } finally {
            Log.v("taylor ttexception", "ExceptionActivity.fun3() " + " finally");
        }
    }
}
