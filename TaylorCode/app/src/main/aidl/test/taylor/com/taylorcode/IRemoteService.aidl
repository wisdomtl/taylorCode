// IShip.aidl
package test.taylor.com.taylorcode;

// Declare any non-default types here with import statements
//[story IPC]0:define protocol used to communicate with server
interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void sail() ;
    boolean isEngineOk() ;
    String getMapValue();
}
///*
// * this is the corresponding java file of aidl,This file is auto-generated.
// * Original file: /Users/taylor/Desktop/TaylorCode/app/src/main/aidl/test/taylor/com/taylorcode/IRemoteService.aidl
// */
//package test.taylor.com.taylorcode;
//// Declare any non-default types here with import statements
//
//public interface IRemoteService extends android.os.IInterface {
//    /**
//     *
//     * Local-side IPC implementation stub class.
//     */
//    public static abstract class Stub extends android.os.Binder implements test.taylor.com.taylorcode.IRemoteService {
//        private static final java.lang.String DESCRIPTOR = "test.taylor.com.taylorcode.IRemoteService";
//
//        /**
//         * Construct the stub at attach it to the interface.
//         */
//        public Stub() {
//            this.attachInterface(this, DESCRIPTOR);
//        }
//
//        /**
//         * Cast an IBinder object into an test.taylor.com.taylorcode.IRemoteService interface,
//         * generating a proxy if needed.
//         * [story IPC]5.during the converting,query local first and then generating a proxy,so query will be hook poiont
//         */
//        public static test.taylor.com.taylorcode.IRemoteService asInterface(android.os.IBinder obj) {
//            if ((obj == null)) {
//                return null;
//            }
//            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
//            if (((iin != null) && (iin instanceof test.taylor.com.taylorcode.IRemoteService))) {
//                return ((test.taylor.com.taylorcode.IRemoteService) iin);
//            }
//            return new test.taylor.com.taylorcode.IRemoteService.Stub.Proxy(obj);
//        }
//
//        @Override
//        public android.os.IBinder asBinder() {
//            return this;
//        }
//
//        @Override
//        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
//            switch (code) {
//                case INTERFACE_TRANSACTION: {
//                    reply.writeString(DESCRIPTOR);
//                    return true;
//                }
//                case TRANSACTION_basicTypes: {
//                    data.enforceInterface(DESCRIPTOR);
//                    int _arg0;
//                    _arg0 = data.readInt();
//                    long _arg1;
//                    _arg1 = data.readLong();
//                    boolean _arg2;
//                    _arg2 = (0 != data.readInt());
//                    float _arg3;
//                    _arg3 = data.readFloat();
//                    double _arg4;
//                    _arg4 = data.readDouble();
//                    java.lang.String _arg5;
//                    _arg5 = data.readString();
//                    this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
//                    reply.writeNoException();
//                    return true;
//                }
//                case TRANSACTION_sail: {
//                    data.enforceInterface(DESCRIPTOR);
//                    this.sail();
//                    reply.writeNoException();
//                    return true;
//                }
//                case TRANSACTION_isEngineOk: {
//                    data.enforceInterface(DESCRIPTOR);
//                    boolean _result = this.isEngineOk();
//                    reply.writeNoException();
//                    reply.writeInt(((_result) ? (1) : (0)));
//                    return true;
//                }
//            }
//            return super.onTransact(code, data, reply, flags);
//        }
//
//        private static class Proxy implements test.taylor.com.taylorcode.IRemoteService {
//            private android.os.IBinder mRemote;
//
//            Proxy(android.os.IBinder remote) {
//                mRemote = remote;
//            }
//
//            @Override
//            public android.os.IBinder asBinder() {
//                return mRemote;
//            }
//
//            public java.lang.String getInterfaceDescriptor() {
//                return DESCRIPTOR;
//            }
//
//            /**
//             * Demonstrates some basic types that you can use as parameters
//             * and return values in AIDL.
//             */
//            @Override
//            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException {
//                android.os.Parcel _data = android.os.Parcel.obtain();
//                android.os.Parcel _reply = android.os.Parcel.obtain();
//                try {
//                    _data.writeInterfaceToken(DESCRIPTOR);
//                    _data.writeInt(anInt);
//                    _data.writeLong(aLong);
//                    _data.writeInt(((aBoolean) ? (1) : (0)));
//                    _data.writeFloat(aFloat);
//                    _data.writeDouble(aDouble);
//                    _data.writeString(aString);
//                    mRemote.transact(Stub.TRANSACTION_basicTypes, _data, _reply, 0);
//                    _reply.readException();
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//            }
//
//            @Override
//            public void sail() throws android.os.RemoteException {
//                android.os.Parcel _data = android.os.Parcel.obtain();
//                android.os.Parcel _reply = android.os.Parcel.obtain();
//                try {
//                    _data.writeInterfaceToken(DESCRIPTOR);
//                    mRemote.transact(Stub.TRANSACTION_sail, _data, _reply, 0);
//                    _reply.readException();
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//            }
//
//            @Override
//            public boolean isEngineOk() throws android.os.RemoteException {
//                android.os.Parcel _data = android.os.Parcel.obtain();
//                android.os.Parcel _reply = android.os.Parcel.obtain();
//                boolean _result;
//                try {
//                    _data.writeInterfaceToken(DESCRIPTOR);
//                    mRemote.transact(Stub.TRANSACTION_isEngineOk, _data, _reply, 0);
//                    _reply.readException();
//                    _result = (0 != _reply.readInt());
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//                return _result;
//            }
//        }
//
//        static final int TRANSACTION_basicTypes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
//        static final int TRANSACTION_sail = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
//        static final int TRANSACTION_isEngineOk = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
//    }
//
//    /**
//     * Demonstrates some basic types that you can use as parameters
//     * and return values in AIDL.
//     */
//    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException;
//
//    public void sail() throws android.os.RemoteException;
//
//    public boolean isEngineOk() throws android.os.RemoteException;
//}
