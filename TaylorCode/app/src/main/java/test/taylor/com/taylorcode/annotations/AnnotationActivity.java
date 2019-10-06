package test.taylor.com.taylorcode.annotations;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created on 2018/2/14.
 */

public class AnnotationActivity extends Activity {

    /**
     * annotation case1:use annotation as enum
     * 1.define annotation server as enum,which is more memory-efficient than enum
     */
    @IntDef({Job.SAMAN, Job.HUNTER, Job.MAGIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Job {
        int SAMAN = 1;
        int HUNTER = 2;
        int MAGIC = 3;
    }

    /**
     * annotation case1:use annotation as enum
     * 2.describe variable by annotation
     */
    @Job
    int job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * annotation case1:use annotation as enum
         * 3.work like an enum
         */
        job = Job.HUNTER;
        switch (job) {
            case Job.HUNTER:
                break;
            case Job.MAGIC:
                break;
            case Job.SAMAN:
                break;
        }

        //annotation case2:annotate method
        IMethod iMethod = (IMethod) Proxy.newProxyInstance(this.getClassLoader(), new Class[]{IMethod.class}, new MethodInvocationHandler());
        iMethod.methodA();
        iMethod.methodB();
    }

    /**
     * annotation case2:annotate method
     */
    public class MethodInvocationHandler implements InvocationHandler {

        private boolean isCacheMethod(Method method) {
            /**
             * annotation case2:annotate method
             * read annotation of method
             */
            CacheMethod cacheMethod = method.getAnnotation(CacheMethod.class);
            return cacheMethod != null && cacheMethod.isCache();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isCacheMethod(method)) {
                Log.v("ttaylor", "method.invoke(): is cache method");
            } else {
                Log.v("ttaylor", "method.invoke(): is not cache method");
            }
            return null;
        }
    }
}
