package com.zenmen.ad_imp_popad.gdt

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import com.zenmen.ad_imp_popad.AdNetwork
import com.zenmen.ad_imp_popad.AdSource

class TecentBannerAd : AdSource {
    override val adNetwork: AdNetwork
        get() = AdNetwork.TENCENT

    override fun load(activity: Activity, param: String) {
        Log.d("ttaylor", "TecentBannerAD.load: ")
    }

    override fun show(viewGroup: ViewGroup) {
        Log.d("ttaylor", "TecentBannerAD.show: ")
    }

    override fun release() {
        Log.d("ttaylor", "TecentBannerAD.release: ")
    }
}