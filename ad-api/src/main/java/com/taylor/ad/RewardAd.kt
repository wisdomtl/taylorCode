package com.taylor.ad

import android.app.Activity

abstract class RewardAd(slotId:String):Ad(slotId) {
    abstract fun show(activity: Activity)
}