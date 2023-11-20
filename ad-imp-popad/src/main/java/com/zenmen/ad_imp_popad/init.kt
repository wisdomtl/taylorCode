package com.zenmen.ad_imp_popad

import org.koin.core.context.startKoin


fun initAd() {
    startKoin {
        modules(adModules)
    }
}