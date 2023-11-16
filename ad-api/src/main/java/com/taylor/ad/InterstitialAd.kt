package com.taylor.ad

import android.app.Activity

abstract class InterstitialAd(slotId: String) : Ad(slotId) {
    abstract fun show(activity: Activity)
}