package com.taylor.ad

import android.app.Activity

interface InterstitialAd:Ad {
    fun show(activity:Activity)
}