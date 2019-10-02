// IRemoeteSingleton.aidl
package test.taylor.com.taylorcode;

// Declare any non-default types here with import statements

interface IRemoteSingleton {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getText2();

    int getCount();

    List<String> getList();

    void setText2(String text);

    void setCount(int count);

    void add(String element);
}
