package com.zenmen.ad_imp_popad

import android.app.Activity
import android.util.Log
import com.taylor.ad.Ad

class PopAd : Ad {
    override var stateListener: Ad.StateListener? = null

    override fun load(activity: Activity) {
        Log.d("test", "PopAd.load: ")
    }

    override fun release() {
        Log.d("test", "PopAd.release: ")
    }
}