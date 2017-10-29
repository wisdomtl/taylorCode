package test.taylor.com.taylorcode.innerclass;

import android.util.Log;

/**
 * Created by taylor on 2017/10/29.
 */

public class OuterClass {

    private IInterface iInterface ;
    private static String sString = "dfdf" ;
    private static IInterface sIInterface ;

    public OuterClass (){
        //cast1:anonymous inner interface with local variable. After gc, OuterClass and anonymous inner class will all be gone
        IInterface iinterface = new IInterface() {
            @Override
            public void call() {

            }
        };
    }

    public OuterClass(int a){
        Log.v("ttaylor", "OuterClass.OuterClass(int a): ");
        //case2:anonymous inner interface with member variable. After gc, OuterClass and anonymous inner class will all be gone
        iInterface = new IInterface() {
            @Override
            public void call() {

            }
        } ;
    }

    public OuterClass (int a ,int b){
        Log.v("ttaylor", "OuterClass.OuterClass(int a ,int b): ");
        //case3:anonymous inner interface with static member variable. After gc, OuterClass and anonymous inner class will not be gone. Because static member variable will still exist in memory, and anonymous inner class is held by it
        sIInterface = new IInterface() {
            @Override
            public void call() {

            }
        } ;
    }

    /**
     * clear the static member variable
     */
    public static void clear(){
        Log.v("ttaylor", "OuterClass.clear(): ");
        sIInterface = null ;
    }
}
