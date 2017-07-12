package test.taylor.com.taylorcode.proxy.local;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created on 17/5/2.
 */

public class DynamicProxy implements InvocationHandler {
    private ICar proxyee;
    private IInvasion iInvasion;

    public Object newProxy(ICar proxyee, IInvasion iInvasion) {
        this.proxyee = proxyee;
        this.iInvasion = iInvasion;
        ClassLoader classLoader = proxyee.getClass().getClassLoader();
        Class<?>[] interfaces = proxyee.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (iInvasion != null) {
            iInvasion.before();
        }
        switch(method.getName()){
            case  ICar.METHOD_IS_ENGINE_OK:
                break ;
            case  ICar.METHOD_RUN:
                break ;
            default :
                break ;
        }
        Object result = method.invoke(proxyee, args);
        if (iInvasion != null) {
            iInvasion.after();
        }
        return result;
    }

    /**
     * the invasion code around method invocation
     */
    public interface IInvasion {
        void before();

        void after();
    }
}
