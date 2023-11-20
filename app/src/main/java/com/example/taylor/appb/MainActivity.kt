package com.example.taylor.appb

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import com.taylor.ad.Ad
import com.taylor.ad.AdFactory
import com.taylor.ad.BannerAd
import com.taylor.ad.StateListener

class MainActivity :Activity() {
    private val BANNER_SLOT_ID = "33"
    private lateinit var bannerContainer: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAd()

    }

    private fun loadAd() {
//        val bannerAd = AdFactory.get(BannerAd::class.java, BANNER_SLOT_ID)
//        bannerAd.load(this)
//        bannerAd.stateListener = { state ->
//            when (state) {
//                Ad.State.LOADED -> {}
//                is Ad.State.ERROR -> {}
//                is Ad.State.SHOWED -> {}
//                is Ad.State.CLICKED -> {}
//                is Ad.State.CLOSED -> {}
//                else -> {}
//            }
//        }
//        bannerAd.show { adView, layoutParam -> bannerContainer.addView(adView, layoutParam) }
    }

}