/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * <p>
 * Created by gongzhen on 2018/11/1
 */
public class PageViews {

    public static void init(IPVInterceptor interceptor) {
        PageViewsManager.init(interceptor);
            PageViewsManager.clearEventId();
    }

    public static void start(@NonNull String eventId, int loadType, String eventKey) {
        start(eventId, loadType, eventKey, new HashMap<>());
    }

    public static void start(@NonNull String eventId, int loadType, String eventKey,
        @NonNull Map<String, String> extra) {
        PageViewsManager.start(new PageViewsEvent(eventId, loadType, eventKey, extra, true));
    }

    public static void end(@NonNull String eventId, int loadType, String eventKey) {
        end(eventId, loadType, eventKey, new HashMap<>());
    }

    public static void end(@NonNull String eventId, int loadType, String eventKey,
        @NonNull Map<String, String> extra) {
        PageViewsManager.end(new PageViewsEvent(eventId, loadType, eventKey, extra));
    }

    /**
     * Call this method when you start a page view event via JSBridge call.
     */
    public static void startInH5(@NonNull String eventId, int loadType, long startTime) {
        startInH5(eventId, loadType, startTime, new HashMap<>());
    }

    /**
     * Call this method when you start a page view event via JSBridge call.
     */
    public static void startInH5(@NonNull String eventId, int loadType, long startTime,
        @NonNull Map<String, String> extra) {
        PageViewsEvent event = new PageViewsEvent(eventId, loadType, "", extra, true);
        event.startTime = startTime;
        PageViewsManager.startInH5(event);
    }

    /**
     * Call this method when you end a page view event via JSBridge call.
     */
    public static void endInH5(@NonNull String eventId, int loadType, long endTime) {
        endInH5(eventId, loadType, endTime, new HashMap<>());
    }

    /**
     * Call this method when you end a page view event via JSBridge call.
     */
    public static void endInH5(@NonNull String eventId, int loadType, long endTime,
        @NonNull Map<String, String> extra) {
        PageViewsEvent event = new PageViewsEvent(eventId, loadType, "", extra);
        event.endTime = endTime;
        PageViewsManager.endInH5(event);
    }

    public static void passBy(@NonNull String eventId) {
        PageViewsManager.storeEventId(eventId);
    }

    public static String getEventIdFrom(){
        return PageViewsManager.getEventIdFrom();
    }
}
