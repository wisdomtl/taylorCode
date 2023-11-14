package com.example.taylor.appb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.taylor.ad.AdFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAd()
    }

    private fun loadAd() {
    }
}