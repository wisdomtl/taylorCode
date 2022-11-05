/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.os.SystemClock;

import androidx.annotation.NonNull;

import java.util.HashMap;


/**
 * Created by William on 2018/11/15
 */
class PageViewsManager {
    private static final String PV_PREFERENCE_NAME = "bili_pv_pref";
    private static final String PV_EVENT_FROM = "pv_event_from_key";
    private static final String PV_FIRST_EVENT_ID = "0.0.0.0.pv";
    private static final int MAX_COEXIST_PV_NUMBER = 10;
    private static HashMap<String, PageViewsEvent> sEventHashMap = new HashMap<>();
    private static PageViewsEvent sEventInH5 = null;
    private static IPVInterceptor sPvInterceptor = null;

    static void init(IPVInterceptor interceptor) {
        sPvInterceptor = interceptor;
    }

    static void start(@NonNull PageViewsEvent event) {
        if (sPvInterceptor != null) {
            sPvInterceptor.onPVStart(event);
        }
        tryAutoEndInH5();

        sEventHashMap.put(event.key, event);
    }

    static void end(@NonNull PageViewsEvent event) {
        event.endTime = System.currentTimeMillis();
        PageViewsEvent storedEvent = sEventHashMap.get(event.key);
        if (!event.equals(storedEvent)) {
            return;
        }

        if (storedEvent.startTime > 0) {
            event.duration = SystemClock.elapsedRealtime() - storedEvent.ts;
        } else {
            event.duration = 0;
        }


        handleEventIdFrom(event);


        if (sEventHashMap.size() > MAX_COEXIST_PV_NUMBER) {
            sEventHashMap.clear();
        } else {
            sEventHashMap.remove(storedEvent.key);
        }

        PageViewTracker.getInstance().setLastEndPv(event);
    }

    static void storeEventId(@NonNull String eventId) {
//        Context context = BiliContext.application();
//        if (context != null) {
//            Xpref.getSharedPreferences(context, PV_PREFERENCE_NAME).edit()
//                .putString(PV_EVENT_FROM, eventId).apply();
//        }
    }

    static void clearEventId() {
//        Context context = BiliContext.application();
//        if (context != null) {
//            Xpref.getSharedPreferences(context, PV_PREFERENCE_NAME).edit()
//                .clear().apply();
//        }
    }

    private static void handleEventIdFrom(@NonNull PageViewsEvent event) {
//        Context context = BiliContext.application();
//        if (context != null) {
//            event.eventIdFrom = Xpref.getSharedPreferences(context, PV_PREFERENCE_NAME)
//                .getString(PV_EVENT_FROM, PV_FIRST_EVENT_ID);
//            Xpref.getSharedPreferences(context, PV_PREFERENCE_NAME).edit()
//                .putString(PV_EVENT_FROM, event.eventId).apply();
//        }
    }

    static void startInH5(@NonNull PageViewsEvent event) {
        tryAutoEndInH5();

        sEventInH5 = event;
    }

    static void endInH5(@NonNull PageViewsEvent event) {
        if (sEventInH5 == null || !sEventInH5.equals(event)) return;

        if (sEventInH5.startTime > 0) {
            event.duration = SystemClock.elapsedRealtime() - sEventInH5.ts;
        } else {
            event.duration = 0;
        }

        handleEventIdFrom(event);
//        Neurons.reportH5PageView(false, event.eventId, event.eventIdFrom,
//            event.loadType, event.duration, event.extra, sEventInH5.startTime, event.endTime);

        sEventInH5 = null;
    }

    /**
     * Check whether there is a page view event started in H5 to be reported. If so,
     * report it first.
     */
    private static void tryAutoEndInH5() {
        if (sEventInH5 == null) return;

        PageViewsEvent event = new PageViewsEvent(sEventInH5.eventId, sEventInH5.loadType,
            sEventInH5.key, sEventInH5.extra);
        event.endTime = System.currentTimeMillis();

        if (sEventInH5.startTime > 0) {
            event.duration = SystemClock.elapsedRealtime() - sEventInH5.ts;
        } else {
            event.duration = 0;
        }

        handleEventIdFrom(event);

//        Neurons.reportH5PageView(false, event.eventId, event.eventIdFrom,
//            event.loadType, event.duration, event.extra, sEventInH5.startTime, event.endTime);

        sEventInH5 = null;

    }

    static String getEventIdFrom() {
//        Context context = BiliContext.application();
//        if (context == null) {
//            return "";
//        }
//        return Xpref.getSharedPreferences(context, PV_PREFERENCE_NAME)
//            .getString(PV_EVENT_FROM, PV_FIRST_EVENT_ID);
        return "";
    }

}
