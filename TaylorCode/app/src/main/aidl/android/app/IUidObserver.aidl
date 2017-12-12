

//this file is copied from android source,in purpose of implementing in app
package android.app;

/** {@hide} */
oneway interface IUidObserver {
    void onUidStateChanged(int uid, int procState);
    void onUidGone(int uid);
}
