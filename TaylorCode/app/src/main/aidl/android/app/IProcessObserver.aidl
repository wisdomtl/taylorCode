

//this file is copied from android source,in purpose of implementing in app
package android.app;

/** {@hide} */
oneway interface IProcessObserver {
    void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities);
    void onProcessStateChanged(int pid, int uid, int procState);
    void onProcessDied(int pid, int uid);
}
