package test.taylor.com.taylorcode.proxy.local;

/**
 * Created on 17/5/2.
 */

public interface ICar {
    String METHOD_IS_ENGINE_OK = "isEngineOk";
    String METHOD_RUN = "run";

    void run();


    boolean isEngineOk();
}
