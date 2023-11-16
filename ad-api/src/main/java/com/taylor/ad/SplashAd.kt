package com.taylor.ad

import android.view.ViewGroup

abstract class SplashAd(slotId: String) : Ad(slotId) {
    abstract fun show(viewGroup: ViewGroup)
}