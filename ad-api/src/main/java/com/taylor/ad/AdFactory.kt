package com.taylor.ad

interface AdFactory {
    fun <T> get(cls: Class<out T>, slotId: String): T
}