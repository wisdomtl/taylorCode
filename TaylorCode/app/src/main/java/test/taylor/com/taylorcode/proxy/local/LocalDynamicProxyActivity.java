package test.taylor.com.taylorcode.proxy.local;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created on 17/5/2.
 */

public class LocalDynamicProxyActivity extends Activity {
    public static final String EXTRA_DEST = "extra-dest" ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //case1
        ICar car1 = new Car();
        ICar car1Proxy = (ICar) new DynamicProxy().newProxy(car1, new LogInvasion());
        car1Proxy.run();
        //case2
        CarShop carShop = new CarShop();
        carShop.startCar();
        ICar car2Proxy = (ICar) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{ICar.class}, invocationHandler);
        Log.e("taylor ClassLoader", "LocalDynamicProxyActivity.onCreate() " + " isEngineOk=" + car2Proxy.isEngineOk());
        //case3
        ICar car3Proxy = (ICar) Proxy.newProxyInstance(this.getClass().getClassLoader() , new Class[]{ICar.class}, paramInvocationHandler);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEST,"JiaDing") ;
        drive(1 , intent , car3Proxy);
    }


    /**
     * case1:proxyee implements the interface
     */
    public class Car implements ICar {

        @Override
        public void run() {
            Log.v("taylor", "Car.run() ");
        }

        @Override
        public boolean isEngineOk() {
            boolean isEngineOk = false;
            Log.v("taylor", "Car.isEngineOk() " + "=" + isEngineOk);
            return isEngineOk;
        }

        @Override
        public void drive(int direction, Intent intent) {
            Log.v("taylor ", "Car.drive() " + " direction=" + direction + ",intent=" + intent);
        }

    }

    public class LogInvasion implements DynamicProxy.IInvasion {

        @Override
        public void before() {
            Log.e("taylor", "LogInvasion.before() ");
        }

        @Override
        public void after() {
            Log.e("taylor", "LogInvasion.after() ");
        }
    }

    /**
     * case2:proxyee has the interface(intercept)
     */
    public class CarShop {
        ICar iCar;

        public CarShop() {
            iCar = new ICar() {
                @Override
                public void run() {
                    Log.v("taylor intercept", "CarShop.run() " + " ");
                }

                @Override
                public boolean isEngineOk() {
                    boolean isEnglishOk = false;
                    Log.v("taylor intercept", "CarShop.isEngineOk() " + " ok=" + isEnglishOk);
                    return isEnglishOk;
                }

                @Override
                public void drive(int direction, Intent intent) {
                    Log.v("taylor " , "CarShop.drive() "+ " direction="+direction+",intent="+intent) ;
                }
            };
        }

        public void startCar() {
            iCar.run();
            iCar.isEngineOk();
        }
    }

    private InvocationHandler invocationHandler = new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case ICar.METHOD_IS_ENGINE_OK:
                    return true;
                case ICar.METHOD_RUN:
                    break;
                default:
                    break;
            }
            return null;
        }
    };

    /**
     * case3:make a proxy of method param
     * @param direction
     * @param intent
     * @param iCar
     */
    private void drive(int direction ,Intent intent,ICar iCar){
        iCar.drive(direction , intent);
        //read intent
        String dest = intent.getStringExtra(EXTRA_DEST) ;
        Log.e("taylor ttparam" , "LocalDynamicProxyActivity.drive() "+ " dest="+dest) ;
    }

    private InvocationHandler paramInvocationHandler = new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case ICar.METHOD_IS_ENGINE_OK:
                    return true;
                case ICar.METHOD_RUN:
                    break;
                case ICar.METHOD_DRIVE :
                   drive(args) ;
                default:
                    break;
            }
            return null;
        }

        private void drive(Object[] args){
            //find intent among params
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            //tamper intent extra
            Intent intent = ((Intent) args[index]);
            intent.putExtra(EXTRA_DEST , "HeChuanLu") ;
            args[index] = intent ;
        }
    } ;
}
