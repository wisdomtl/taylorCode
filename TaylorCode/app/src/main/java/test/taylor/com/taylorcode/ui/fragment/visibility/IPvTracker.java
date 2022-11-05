/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.os.Bundle;
import androidx.annotation.Nullable;

/**
 * @author : windfall
 * @date : 2018/11/8
 * @email : liuchangjiang@bilibili.com
 */
public interface IPvTracker {
    /**
     * 获取pv event id
     *
     * @return 要上报的页面的event id
     */
    String getPvEventId();

    /**
     * 获取业务方额外字段
     *
     * @return 业务方额外字段
     */
    Bundle getPvExtra();

    /**
     * 一些特殊页面业务方可自行拦截上报，默认都上报
     */
    default boolean shouldReport() {
        return true;
    }

    /**
     * 可选方法。
     *
     * 为埋点数据相互独立，本地采用eventId + uniqueKey 作为最终的key保存数据。
     * @return 可自行设置uniqueKey，默认值为当前页面的hashcode
     */
    @Nullable
    default String getUniqueKey() {
        return null;
    }
}
