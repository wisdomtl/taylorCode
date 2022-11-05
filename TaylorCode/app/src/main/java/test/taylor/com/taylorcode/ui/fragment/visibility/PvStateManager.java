/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : windfall
 * @date : 2018/11/8
 * @email : liuchangjiang@bilibili.com
 */
class PvStateManager {

    private static final String TAG = "PageViewTracker";
    public static final int LOAD_TYPE_ENTER = 0;
    public static final int LOAD_TYPE_BACK = 1;

    private Map<String, PvPageInfo> mVisiblePagesId = new LinkedHashMap<>();

    @SuppressWarnings("NullAway")
    private static volatile PvStateManager sInstance;

    private String mCurVisibleUniqueId;

    private PvStateManager() {

    }

    static PvStateManager getInstance() {
        if (sInstance == null) {
            synchronized (PvStateManager.class) {
                if (sInstance == null) {
                    sInstance = new PvStateManager();
                }
            }
        }
        return sInstance;
    }

    void triggerVisible(String uniqueId, String eventId, Bundle extra, int loadType) {
        if (mVisiblePagesId.containsKey(uniqueId)) {
            return;
        }
        mCurVisibleUniqueId = uniqueId;

        PvPageInfo pvPageInfo = new PvPageInfo(eventId, extra, loadType, uniqueId);
        mVisiblePagesId.put(uniqueId, pvPageInfo);
        doStartReport(pvPageInfo);
    }

    void triggerInvisible(String uniqueId) {
        if (TextUtils.isEmpty(uniqueId) || !mVisiblePagesId.containsKey(uniqueId)) {
            return;
        }
        doEndReport(mVisiblePagesId.get(uniqueId));
        mVisiblePagesId.remove(uniqueId);
    }

    void triggerVisible(String uniqueId, String eventId, Bundle extra, int loadType, boolean endLastPage) {
        if (mVisiblePagesId.containsKey(uniqueId)) {
            return;
        }

        if (endLastPage) {
            triggerInvisibleManual();
        }
        mCurVisibleUniqueId = uniqueId;

        PvPageInfo pvPageInfo = new PvPageInfo(eventId, extra, loadType, uniqueId);
        mVisiblePagesId.put(uniqueId, pvPageInfo);
        doStartReport(pvPageInfo);
    }

    void setExtra(String uniqueId, Bundle extra) {
        if (TextUtils.isEmpty(uniqueId) || extra == null || !mVisiblePagesId.containsKey(uniqueId)) {
            return;
        }
        mVisiblePagesId.get(uniqueId).bundle = extra;
    }

    private void doStartReport(PvPageInfo pvPageInfo) {
        if (pvPageInfo == null) {
            return;
        }
        PageViewTracker.getInstance().onReceiveEventIdFrom(PageViews.getEventIdFrom());
        PageViews.start(pvPageInfo.eventId, pvPageInfo.loadType, pvPageInfo.eventKey, pvPageInfo.asArgs());

        PageViewTracker.getInstance().onReceiveCurrentEventId(pvPageInfo.eventId);
    }

    private void doEndReport(PvPageInfo pvPageInfo) {
        if (pvPageInfo == null) {
            return;
        }
        PageViews.end(pvPageInfo.eventId, pvPageInfo.loadType, pvPageInfo.eventKey, pvPageInfo.asArgs());

        PageViewTracker.getInstance().onReceiveCurrentEventId("");
    }

    public void triggerInvisibleManual() {
        if (mVisiblePagesId.isEmpty()) {
            return;
        }
        for (String key : mVisiblePagesId.keySet()) {
            PvPageInfo pageInfo = mVisiblePagesId.get(key);
            if (pageInfo == null) {
                continue;
            }
            doEndReport(pageInfo);
        }
        mVisiblePagesId.clear();
    }

    @Nullable
    public PvPageInfo getLastVisiblePageInfo(String uniqueId) {
        if (TextUtils.isEmpty(uniqueId) || !mVisiblePagesId.containsKey(uniqueId)) {
            return null;
        }

        return mVisiblePagesId.get(uniqueId);
    }

    public String getCurVisibleUniqueId() {
        return mCurVisibleUniqueId;
    }

    public int getLoadType(String uniqueId) {
        if (TextUtils.isEmpty(uniqueId) || !mVisiblePagesId.containsKey(uniqueId)) {
            return LOAD_TYPE_ENTER;
        }
        PvPageInfo info = mVisiblePagesId.get(uniqueId);
        if (info != null) {
            return info.loadType;
        }
        return LOAD_TYPE_ENTER;
    }

    static class PvPageInfo {
        String eventId;
        Bundle bundle;
        int loadType;
        String eventKey;

        PvPageInfo(String eventId, Bundle bundle, int loadType, String eventKey) {
            this.eventId = eventId;
            this.bundle = bundle;
            this.loadType = loadType;
            this.eventKey = eventKey;
        }

        public Map<String, String> asArgs() {
            Map<String, String> map = new HashMap<>();
            if (bundle == null) {
                return map;
            }
            for (String key : bundle.keySet()) {
                if (bundle.get(key) != null) {
                    map.put(key, String.valueOf(bundle.get(key)));
                }
            }
            return map;
        }
    }

}
