package test.taylor.com.taylorcode.innerclass;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * conclusion:
 * 1.static member will survive after gc
 * 2.anonymous inner class is holding reference of outer class
 * 3.static anonymous inner class initialized before outer class(initialize immediately after declare) does not hold outer class instance ,but static anonymous inner class initialized after outer class holds.
 * 3.if static member refer to anonymous inner class,memory leak of outer class will happen
 */

public class OuterClass {

    private IInterface iInterface;
    private static String sString = "dfdf";
    //case10:this static member will survive from gc
    private static ClassA sClassA = new ClassA();
    //case3.1:static member refer to anonymous inner class initialized once declared ,after gc ,OuterClass will be gone ,but sIInterface will still be in memory.
    // because sIInterface is not holding reference of outer class ,the time sIInterface is initialized ,the instance of OuterClass haven't initialized
    private static IInterface sIInterface = new IInterface() {
        @Override
        public void call() {

        }
    };
    private static IInterface sIInterface2;
    private ClassA classA;
    private InnerClass2 innerClass2;

    public OuterClass() {
        //cast1:anonymous inner interface with local variable. After gc, OuterClass and anonymous inner class will all be gone
        IInterface iinterface = new IInterface() {
            @Override
            public void call() {

            }
        };
    }

    public OuterClass(int a) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a): ");
        //case2:anonymous inner interface with member variable. After gc, OuterClass and anonymous inner class will all be gone
        iInterface = new IInterface() {
            @Override
            public void call() {

            }
        };
    }

    public OuterClass(int a, int b) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a ,int b): ");
        //case3:memory leak case ,static member refer to anonymous inner class initialized in constructor class ,After gc, OuterClass and anonymous inner class will not be gone.
        //Because static member variable exists longer than outer class ,and anonymous inner class holding the instance of outer class .
        sIInterface2 = new IInterface() {
            @Override
            public void call() {

            }
        };
    }

    public OuterClass(int a, int b, int c) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a,int b,int c): ");
        //case4:normal class with local variable. After gc, OuterClass will all be gone
        ClassA classA = new ClassA();
    }

    public OuterClass(int a, int b, int c, int d) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a,int b,int c,int d): ");
        //case5:normal class with member variable. After gc, OuterClass will all be gone
        classA = new ClassA();
    }

    public OuterClass(String a) {
        Log.v("ttaylor", "OuterClass.OuterClass(String a): ");
        //case5:inner class with local variable. After gc, OuterClass will all be gone
        InnerClass innerClass = new InnerClass();
    }

    public OuterClass(int a, String b) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a, String b): ");
        //case6:static inner class with local variable. After gc, OuterClass will all be gone
        InnerClass2 innerClass2 = new InnerClass2();
    }

    public OuterClass(int a, int b, String c) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a,int b,String c): ");
        //case7:static inner class with member variable. After gc, OuterClass will all be gone
//        innerClass2 = new InnerClass2();
        //case8:memory leak case ,anonymous handler with delay message. After gc, OuterClass will not be gone ,if message is not delayed ,memory wont be leaked
//        handler.sendEmptyMessageDelayed(1, 900000);
    }

    public OuterClass(int a, int b, String c, int d) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a,int b,String c,int d): ");
        //case9:anonymous static handler with delay message. After gc, OuterClass will be gone
        //because anonymous inner handler initialized before outer class does not hold outer class instance
        handler2.sendEmptyMessageDelayed(1, 90000);
    }

    public OuterClass(int a, int b, char c) {
        Log.v("ttaylor", "OuterClass.OuterClass(int a,int b,char c): ");
        //case11:anonymous inner thread ,after gc ,OuterClass will be gone
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("ttaylor", "OuterClass.run(): Thread id=" + Thread.currentThread().getId() + " start");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("ttaylor", "OuterClass.run(): Thread id=" + Thread.currentThread().getId() + " end");
            }
        }).start();
    }

    /**
     * clear the static member variable referred to anonymous inner class which initialized after outer class initialization ,it is a must ,or OuterClass will not be gc
     */
    public static void clear() {
        Log.v("ttaylor", "OuterClass.clear(): ");
        sIInterface = null;
    }


    /**
     * inner class holding an reference of OuterClass
     */
    private class InnerClass {
    }

    /**
     *
     */
    private static class InnerClass2 {

    }

    /**
     * non-static anonymous inner class hold outer class instance
     */
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.v("ttaylor", "OuterClass.handleMessage(): mas.what=" + msg.what);
//        }
//    };

    /**
     * static anonymous inner class doesn't hold outer class instance
     */
    private static Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("ttaylor", "OuterClass.handleMessage(): mas.what=" + msg.what);
        }
    };
}
