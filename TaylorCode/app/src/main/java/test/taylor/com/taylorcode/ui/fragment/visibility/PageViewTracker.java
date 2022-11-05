/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : windfall
 * @date : 2018/11/8
 * @email : liuchangjiang@bilibili.com
 */
public class PageViewTracker {
    private static final String TAG = "PageViewTracker";

    @Nullable
    private static volatile PageViewTracker sInstance;
    private PvActivityLifeCycleCallback mActivityLifeCycleCallback;

    @Nullable
    private PvStateManager.PvPageInfo mLastVisiblePageInfo;
    private List<OnSwitchToBackgroundListener> mSwitchListener = new CopyOnWriteArrayList<>();
    private List<OnReceiveEventIdFromListener> mEventIdFromListener = new CopyOnWriteArrayList<>();
    private List<OnReceiveCurrentEventIdListener> mCurrentEventIdListeners = new CopyOnWriteArrayList<>();
    private String currentPageViewId = "";

    // 最近一次上报的pv
    private PageViewsEvent lastEndPv = null;

    private PageViewTracker() {
        mActivityLifeCycleCallback = new PvActivityLifeCycleCallback();
    }

    public static PageViewTracker getInstance() {
        if (sInstance == null) {
            synchronized (PageViewTracker.class) {
                if (sInstance == null) {
                    sInstance = new PageViewTracker();
                }
            }
        }
        return sInstance;
    }

    public void init(Application app, IPVInterceptor interceptor) {
        app.registerActivityLifecycleCallbacks(mActivityLifeCycleCallback);
        PageViews.init(interceptor);
    }

    /**
     * 设置当前fragment是否可见
     *
     * @param fragment  当前fragment
     * @param isVisible 当前fragment是否可见
     */
    public void setFragmentVisibility(Fragment fragment, boolean isVisible) {
        if (mActivityLifeCycleCallback.getFragmentLifeCycleCallback() != null) {
            mActivityLifeCycleCallback.getFragmentLifeCycleCallback().onFragmentVisibleChanged(fragment, isVisible, true);
        }
    }

    /**
     * 设置上报所需的额外字段
     * 若直接调用PageViewTracker#start() 和 PageViewTracker#end()方法，无须调用该方法
     *
     * @param uniqueId 当前页面eventId + uniqueKey(默认为hashcode)
     * @param extra    额外字段
     */
    public void setExtra(String uniqueId, Bundle extra) {
        PvStateManager.getInstance().setExtra(uniqueId, extra);
    }

    /**
     * 设置上报所需的额外字段
     * 若直接调用PageViewTracker#start() 和 PageViewTracker#end()方法，无须调用该方法
     *
     * @param fragment 当前页面
     * @param eventId  当前页面eventId
     * @param extra    额外字段
     */
    public void setExtra(Fragment fragment, String eventId, Bundle extra) {
        PvStateManager.getInstance().setExtra(getUniqueId(fragment, eventId), extra);
    }

    /**
     * 设置上报所需的额外字段
     * 若直接调用PageViewTracker#start() 和 PageViewTracker#end()方法，无须调用该方法
     *
     * @param activity 当前页面
     * @param eventId  当前页面eventId
     * @param extra    额外字段
     */
    public void setExtra(Activity activity, String eventId, Bundle extra) {
        PvStateManager.getInstance().setExtra(getUniqueId(activity, eventId), extra);
    }

    /**
     * 监听viewpager + fragment中的viewpager 变化
     *
     * @param viewPager 需要监听的viewpager
     */
    public void observePageChange(ViewPager viewPager) {
        if (mActivityLifeCycleCallback.getFragmentLifeCycleCallback() != null) {
            mActivityLifeCycleCallback.getFragmentLifeCycleCallback().observePageChange(viewPager);
        }
    }

    /**
     * 用于viewpager2 + fragment的pv上报
     *
     * @param viewpager 要上报的viewpager
     * @param fm        承载fragment的FragmentManger，用于查找fragment
     */
    public void observePageChange(ViewPager2 viewpager, FragmentManager fm) {
        PvFragmentLifeCycleCallback callback = mActivityLifeCycleCallback.getFragmentLifeCycleCallback();
        if (callback != null) {
            callback.observePageChange(viewpager, fm);
        }
    }

    /**
     * viewpager + fragment 父fragment onHiddenChanged时调用该方法
     *
     * @param viewPager 父fragment 中的viewpager
     * @param isVisible 父fragment 是否可见
     */
    public void observeCurPageChange(ViewPager viewPager, boolean isVisible) {
        if (mActivityLifeCycleCallback.getFragmentLifeCycleCallback() != null) {
            mActivityLifeCycleCallback.getFragmentLifeCycleCallback().observeCurPageChange(viewPager, isVisible);
        }
    }

    /**
     * 只针对于需要使用pv框架，但需要手动触发页面可见或隐藏，需要传递全部参数
     *
     * @param eventId   页面 eventId
     * @param hashCode  页面hashcode，用来和eventId组合使用，保证id唯一性
     * @param loadType  页面浏览类型：0，正常浏览；1，回退浏览
     * @param extra     页面额外字段
     * @param isVisible 页面是否可见
     */
    public void onPageVisibleChange(String eventId, String hashCode, int loadType, Bundle extra, boolean isVisible) {
        if (isVisible) {
            PvStateManager.getInstance().triggerVisible(eventId + hashCode, eventId, extra, loadType);
        } else {
            PvStateManager.getInstance().triggerInvisible(eventId + hashCode);
        }
    }

    public void switchToBackground() {
        if (mActivityLifeCycleCallback == null) {
            return;
        }
        mActivityLifeCycleCallback.switchToBackground();
        if (mActivityLifeCycleCallback.getFragmentLifeCycleCallback() != null) {
            mActivityLifeCycleCallback.getFragmentLifeCycleCallback().switchToBackground();
        }
        if (mSwitchListener.isEmpty()) {
            return;
        }
        for (OnSwitchToBackgroundListener listener : mSwitchListener) {
            if (listener != null) {
                listener.switchToBackground();
            }
        }
    }

    public void registerSwitchToBackgroundListener(OnSwitchToBackgroundListener listener) {
        if (mSwitchListener == null || listener == null || mSwitchListener.contains(listener)) {
            return;
        }
        mSwitchListener.add(listener);
    }

    public void unregisterSwitchToBackgroundListener(OnSwitchToBackgroundListener listener) {
        if (listener == null) {
            return;
        }
        mSwitchListener.remove(listener);
    }

    public interface OnSwitchToBackgroundListener {
        void switchToBackground();
    }

    /**
     * 注册接收到event_id_from监听
     */
    public void registerReceiveEventIdFromListener(OnReceiveEventIdFromListener listener) {
        if (listener == null || mEventIdFromListener.contains(listener)) {
            return;
        }
        mEventIdFromListener.add(listener);
    }

    /**
     * 解注册接收到event_id_from监听
     */
    public void unregisterReceiveEventIdFromListener(OnReceiveEventIdFromListener listener) {
        if (listener == null) {
            return;
        }
        mEventIdFromListener.remove(listener);
    }

    void onReceiveEventIdFrom(String eventIdFrom) {
        for (OnReceiveEventIdFromListener listener : mEventIdFromListener) {
            if (listener != null) {
                listener.onReceive(eventIdFrom);
            }
        }
    }

    public interface OnReceiveEventIdFromListener {
        void onReceive(String eventIdFrom);
    }

    public interface OnReceiveCurrentEventIdListener {
        void onGetCurrentId(@Nullable String currentEventId);
    }

    /**
     * 注册接收到 当前 event_id监听
     */
    public void registerReceiveCurrentEventIdListener(OnReceiveCurrentEventIdListener listener) {
        if (listener == null || mCurrentEventIdListeners.contains(listener)) {
            return;
        }
        mCurrentEventIdListeners.add(listener);
    }

    /**
     * 解注册接收到 当前 event_id监听
     */
    public void unregisterReceiveCurrentEventIdListener(OnReceiveCurrentEventIdListener listener) {
        if (listener == null) {
            return;
        }
        mCurrentEventIdListeners.remove(listener);
    }

    void onReceiveCurrentEventId(@Nullable String currentEventId) {
        currentPageViewId = currentEventId;
        for (OnReceiveCurrentEventIdListener listener : mCurrentEventIdListeners) {
            if (listener != null) {
                listener.onGetCurrentId(currentEventId);
            }
        }
    }

    public String getCurrentPageViewId() {
        return currentPageViewId;
    }

    public void triggerLastPageVisible(boolean isVisible) {
        if (mActivityLifeCycleCallback == null || mActivityLifeCycleCallback.getFragmentLifeCycleCallback() == null) {
            return;
        }
        mActivityLifeCycleCallback.getFragmentLifeCycleCallback().onInterceptFragmentReport(!isVisible);
        if (isVisible) {
            if (mLastVisiblePageInfo == null) {
                return;
            }
            PvStateManager.getInstance().triggerVisible(mLastVisiblePageInfo.eventKey, mLastVisiblePageInfo.eventId, mLastVisiblePageInfo.bundle,
                    PvStateManager.LOAD_TYPE_BACK);
            mActivityLifeCycleCallback.getFragmentLifeCycleCallback().setLastVisibleUniqueId(mLastVisiblePageInfo.eventKey);
        } else {
            String uniqueId = mActivityLifeCycleCallback.getFragmentLifeCycleCallback().getLastVisibleUniqueId();
            mLastVisiblePageInfo = PvStateManager.getInstance().getLastVisiblePageInfo(uniqueId);
            PvStateManager.getInstance().triggerInvisibleManual();
            mActivityLifeCycleCallback.getFragmentLifeCycleCallback().setLastVisibleUniqueId("");
        }
    }

    /**
     * 可根据业务特殊情况进行拦截fragment 的onResume、onPause的生命周期中的上报，参靠NavigationFragmentV2/BiliMainSearchActivity
     * <p>
     * 业务方慎用！！！如果必须要使用请联系liuchangjiang
     *
     * @param intercept true: 拦截所有fragment生命周期中的pv上报
     *                  false: 不拦截
     */
    public void onInterceptFragmentReport(boolean intercept) {
        if (mActivityLifeCycleCallback == null || mActivityLifeCycleCallback.getFragmentLifeCycleCallback() == null) {
            return;
        }

        mActivityLifeCycleCallback.getFragmentLifeCycleCallback().onInterceptFragmentReport(intercept);
    }

    public boolean isInterceptFragmentReport() {
        if (mActivityLifeCycleCallback == null || mActivityLifeCycleCallback.getFragmentLifeCycleCallback() == null) {
            return false;
        }

        return mActivityLifeCycleCallback.getFragmentLifeCycleCallback().isInterceptFragmentReport();
    }

    /**
     * 调用pageview接口直接上报
     *
     * @param eventId  页面eventId
     * @param loadType 页面浏览类型：0，正常浏览；1，回退浏览
     * @param eventKey 可区分pv的key，可用时间戳或随机数，但要保证每个pv的start和end相同，pv之间的eventKey不同
     * @param extra    页面额外字段
     */
    public static void start(@NonNull String eventId, int loadType, String eventKey, Map<String, String> extra) {
        if (extra == null) {
            PageViews.start(eventId, loadType, eventKey, new HashMap<>());
        } else {
            PageViews.start(eventId, loadType, eventKey, extra);
        }
    }

    /**
     * 调用pageview接口直接上报
     *
     * @param eventId  页面eventId
     * @param loadType 页面浏览类型：0，正常浏览；1，回退浏览
     * @param eventKey 可区分pv的key，可用时间戳或随机数，但要保证每个pv的start和end相同，pv之间的eventKey不同
     * @param extra    页面额外字段
     */
    public static void end(@NonNull String eventId, int loadType, String eventKey, Map<String, String> extra) {
        if (extra == null) {
            PageViews.end(eventId, loadType, eventKey, new HashMap<>());
        } else {
            PageViews.end(eventId, loadType, eventKey, extra);
        }
    }

    /**
     * 针对与页面内刷新上报pv，在刷新后需要上报pv时先调用end，再调用start
     *
     * @param pvTracker 页面实例
     * @param extra     上报额外字段
     */
    public static void start(@NonNull IPvTracker pvTracker, Map<String, String> extra) {
        if (pvTracker == null || TextUtils.isEmpty(pvTracker.getPvEventId())) {
            return;
        }
        String uniqueId = getUniqueId(pvTracker, pvTracker.getPvEventId());
        int loadType = PvStateManager.getInstance().getLoadType(uniqueId);
        PvStateManager.getInstance().triggerVisible(uniqueId, pvTracker.getPvEventId(), covertMapToBundle(extra), loadType);
    }

    /**
     * 针对与页面内刷新上报pv，在刷新后需要上报pv时先调用end，再调用start
     *
     * @param pvTracker 页面实例
     */
    public static void end(@NonNull IPvTracker pvTracker) {
        if (pvTracker == null || TextUtils.isEmpty(pvTracker.getPvEventId())) {
            return;
        }
        String uniqueId = getUniqueId(pvTracker, pvTracker.getPvEventId());
        PvStateManager.getInstance().triggerInvisible(uniqueId);
    }

    private static Bundle covertMapToBundle(Map<String, String> map) {
        Bundle bundle = new Bundle();
        if (map == null) {
            return bundle;
        }
        for (String key : map.keySet()) {
            bundle.putString(key, map.get(key));
        }
        return bundle;
    }

    /**
     * h5 pv上报
     *
     * @param eventId   h5页面eventId
     * @param loadType  h5页面loadType：正常浏览：0，回退浏览：1
     * @param startTime h5页面真正开始可见时间
     * @param extra     h5页面上报额外字段
     */
    public static void startInH5(@NonNull String eventId, int loadType, long startTime, Map<String, String> extra) {
        if (extra == null) {
            PageViews.startInH5(eventId, loadType, startTime);
        } else {
            PageViews.startInH5(eventId, loadType, startTime, extra);
        }
    }

    /**
     * h5 pv上报
     *
     * @param eventId  h5页面eventId
     * @param loadType h5页面loadType：正常浏览：0，回退浏览：1
     * @param endTime  h5页面不可见时间
     * @param extra    h5页面上报额外字段
     */
    public static void endInH5(@NonNull String eventId, int loadType, long endTime, Map<String, String> extra) {
        if (extra == null) {
            PageViews.endInH5(eventId, loadType, endTime);
        } else {
            PageViews.endInH5(eventId, loadType, endTime, extra);
        }
    }

    static String getUniqueId(Object object, String eventId) {
        if (object == null) {
            return eventId;
        }
        String uniqueKey;
        if (object instanceof IPvTracker && ((IPvTracker) object).getUniqueKey() != null) {
            uniqueKey = ((IPvTracker) object).getUniqueKey();
        } else {
            uniqueKey = String.valueOf(object.hashCode());
        }
        return eventId + uniqueKey;
    }

    public PageViewsEvent getLastEndPv() {
        return lastEndPv;
    }

    public void setLastEndPv(PageViewsEvent pv) {
        lastEndPv = pv;
    }
}
