package com.taylor.ad

import android.view.View
import android.view.ViewGroup

abstract class BannerAd(slotId:String) : Ad(slotId) {
    abstract fun show(block: (View, ViewGroup.LayoutParams) -> Unit)
}