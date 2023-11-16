package com.example.taylor.appb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.taylor.ad.Ad
import com.taylor.ad.AdFactory
import com.taylor.ad.BannerAd
import com.taylor.ad.StateListener
import com.zenmen.ad_imp_popad.PopAdFactory

class MainActivity : AppCompatActivity() {
    private val BANNER_SLOT_ID = "33"
    private lateinit var bannerContainer: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAd()
    }

    private fun loadAd() {
        val bannerAd = PopAdFactory.get(BannerAd::class.java, BANNER_SLOT_ID)
        bannerAd.load(this)
        bannerAd.stateListener = { state ->
            when (state) {
                Ad.State.LOADED -> {}
                is Ad.State.ERROR -> {}
                is Ad.State.SHOWED -> {}
                is Ad.State.CLICKED -> {}
                is Ad.State.CLOSED -> {}
                else -> {}
            }
        }
        bannerAd.show { adView, layoutParam -> bannerContainer.addView(adView, layoutParam) }
    }

}