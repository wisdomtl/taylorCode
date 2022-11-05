/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.os.SystemClock;
import androidx.annotation.NonNull;

import java.util.Map;

/**
 * Created by William on 2018/11/2
 */
public class PageViewsEvent {

    public PageViewsEvent(String eventId, int loadType, @NonNull String key, Map<String, String> extra) {
        this.eventId = eventId;
        this.loadType = loadType;
        this.key = key;
        this.extra = extra;
    }

    public PageViewsEvent(String eventId, int loadType, @NonNull String key, Map<String, String> extra, boolean initStart) {
        this.eventId = eventId;
        this.loadType = loadType;
        this.key = key;
        this.extra = extra;
        if (initStart) {
            this.ts = SystemClock.elapsedRealtime();
            this.startTime = System.currentTimeMillis();
        }
    }

    public String eventId;

    public int loadType;

    @NonNull
    public String key;

    @NonNull
    public String eventIdFrom = "0.0.0.0.pv";

    public long duration;

    public Map<String, String> extra;

    public long startTime;

    public long endTime;

    public long ts;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PageViewsEvent) {
            if (eventId == null || ((PageViewsEvent) obj).eventId == null) return false;
            return eventId.equals(((PageViewsEvent) obj).eventId)
                && loadType == ((PageViewsEvent) obj).loadType
                && key.equals(((PageViewsEvent) obj).key);
        }
        return false;
    }

    public String toString() {
        return String.format("[id=%s, event=%s, loadType=%d, duration=%s, extra=%s]", key, eventId, loadType, duration, extra.toString());
    }
}
