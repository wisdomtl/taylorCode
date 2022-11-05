/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author : windfall
 * @date : 2018/11/8
 * @email : liuchangjiang@bilibili.com
 */
class PvActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

    private PvFragmentLifeCycleCallback mFragmentLifeCycleCallback;
    private Map<String, Integer> mActivityLoadType = new HashMap<>();

    PvActivityLifeCycleCallback() {
        mFragmentLifeCycleCallback = new PvFragmentLifeCycleCallback();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifeCycleCallback, true);
        }

        if (activity instanceof IPvTracker) {
            String eventId = getEventId(activity);
            if (TextUtils.isEmpty(eventId) || mActivityLoadType == null) {
                return;
            }
            mActivityLoadType.put(PageViewTracker.getUniqueId(activity, eventId), PvStateManager.LOAD_TYPE_ENTER);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        String eventId = getEventId(activity);
        if (!TextUtils.isEmpty(eventId)) {
            onActivityVisibleChanged(activity, true);
        }
    }


    @Override
    public void onActivityPaused(Activity activity) {
        String eventId = getEventId(activity);
        if (!TextUtils.isEmpty(eventId)) {
            onActivityVisibleChanged(activity, false);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (mFragmentLifeCycleCallback == null) {
            return;
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(mFragmentLifeCycleCallback);
        }
        if (activity instanceof IPvTracker && mActivityLoadType != null) {
            String eventId = getEventId(activity);
            if (TextUtils.isEmpty(eventId)) {
                return;
            }
            mActivityLoadType.remove(PageViewTracker.getUniqueId(activity, eventId));
        }
    }

    public PvFragmentLifeCycleCallback getFragmentLifeCycleCallback() {
        return mFragmentLifeCycleCallback;
    }

    private void onActivityVisibleChanged(Activity activity, boolean isVisible) {
        if (activity instanceof IPvTracker) {
            IPvTracker tracker = (IPvTracker) activity;
            if (!tracker.shouldReport()) {
                return;
            }
            String eventId = tracker.getPvEventId();
            Bundle bundle = tracker.getPvExtra();

            if (TextUtils.isEmpty(eventId) || mActivityLoadType == null) {
                return;
            }
            String uniqueEventId = PageViewTracker.getUniqueId(activity, eventId);
            int loadType = mActivityLoadType.get(uniqueEventId) == null ? 0 : mActivityLoadType.get(uniqueEventId);
            if (isVisible) {
                PvStateManager.getInstance().triggerVisible(uniqueEventId, eventId, bundle, loadType);
                mActivityLoadType.remove(uniqueEventId);
            } else {
                PvStateManager.getInstance().triggerInvisible(uniqueEventId);
                if (loadType != PvStateManager.LOAD_TYPE_BACK) {
                    mActivityLoadType.put(uniqueEventId, PvStateManager.LOAD_TYPE_BACK);
                }
            }
        }
    }

    public void switchToBackground() {
        String curVisibleUniqueId = PvStateManager.getInstance().getCurVisibleUniqueId();
        if (TextUtils.isEmpty(curVisibleUniqueId)) {
            return;
        }
        if (mActivityLoadType.containsKey(curVisibleUniqueId)) {
            mActivityLoadType.put(curVisibleUniqueId, PvStateManager.LOAD_TYPE_ENTER);
        }
    }

    private String getEventId(Activity activity) {
        if (activity instanceof IPvTracker) {
            return ((IPvTracker) activity).getPvEventId();
        }
        return "";
    }
}
