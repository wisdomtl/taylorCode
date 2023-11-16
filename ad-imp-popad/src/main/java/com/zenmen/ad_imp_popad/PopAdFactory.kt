package com.zenmen.ad_imp_popad

import com.taylor.ad.AdFactory

object PopAdFactory : AdFactory {
    override fun <T> get(cls: Class<out T>, slotId: String): T {
        return cls.getConstructor(PopAd::class.java, String::class.java).newInstance(PopAd(slotId), slotId)
    }
}