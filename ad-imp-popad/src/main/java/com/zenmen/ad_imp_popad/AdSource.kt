package com.zenmen.ad_imp_popad

import android.app.Activity
import android.view.ViewGroup

interface AdSource {
    val adNetwork: AdNetwork

    fun load(activity: Activity, param: String)

    fun show(viewGroup: ViewGroup)

    fun release()
}

enum class AdNetwork {
    KS,
    BAIDU,
    TENCENT,
    PANGLE,
    UNKNOWN
}