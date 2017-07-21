package test.taylor.com.taylorcode.proxy.local;

import android.content.Intent;

/**
 * Created on 17/5/2.
 */

public interface ICar {
    String METHOD_IS_ENGINE_OK = "isEngineOk";
    String METHOD_RUN = "run";
    String METHOD_DRIVE = "drive" ;

    void run();

    /**
     * make proxy of interface for tampering it's return value
     * @return
     */
    boolean isEngineOk();

    /**
     * make proxy of interface for tampering it's param
     * @param direction
     * @param intent
     */
    void drive(int direction, Intent intent) ;
}
