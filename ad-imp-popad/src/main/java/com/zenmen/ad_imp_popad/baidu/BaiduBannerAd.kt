package com.zenmen.ad_imp_popad.baidu

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import com.zenmen.ad_imp_popad.AdNetwork
import com.zenmen.ad_imp_popad.AdSource

class BaiduBannerAd : AdSource {
    override val adNetwork: AdNetwork
        get() = AdNetwork.BAIDU

    override fun load(activity: Activity, param: String) {
        Log.d("ttaylor", "BaiduBannerAd.load: ")
    }

    override fun show(viewGroup: ViewGroup) {
        Log.d("ttaylor", "BaiduBannerAd.show: ")
    }

    override fun release() {
        Log.d("ttaylor", "BaiduBannerAd.release: ")
    }
}