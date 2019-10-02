// ILocalServiceBoundByRemote.aidl
package test.taylor.com.taylorcode;

// a remote activity will bind this local service which is in main process

interface ILocalServiceBoundByRemote {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getString();

    void setString(String string);
}
