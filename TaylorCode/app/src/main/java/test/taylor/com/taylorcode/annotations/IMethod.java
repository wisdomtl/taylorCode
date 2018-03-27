package test.taylor.com.taylorcode.annotations;

/**
 * Created on 2018/3/27.
 */

public interface IMethod {

    /**
     * annotation case2:annotate method
     * annotate method
     */
    @CacheMethod(isCache = false)
    void methodA();

    void methodB();
}
