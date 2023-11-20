package com.zenmen.ad_imp_popad

import com.taylor.ad.AdFactory
import org.koin.core.context.startKoin
import org.koin.dsl.module

val adModules = module {
    single<AdFactory> { PopAdFactory }
}