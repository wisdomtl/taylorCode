package com.taylor.ad

import android.app.Activity

interface Ad {
    var stateListener: StateListener?
    fun load(activity: Activity)
    fun release()

    sealed class State(private val order: Int) : Comparable<State> {
        override fun compareTo(other: State): Int {
            return this.order - other.order
        }

        fun isAtLeast(state: State): Boolean {
            return this >= state
        }

        object INIT : State(0)
        object LOADED : State(1)
        class ERROR(val map: Map<Int?, String?>) : State(1)
        object BID : State(3)
        object SHOWED : State(4)
        object RENDERED : State(5)
        object CLICKED : State(6)
        object CLOSED : State(7)
        object SKIP : State(8)
    }

    interface StateListener {
        /**
         * A callback when ad state changes.
         * All States is defined in [Ad.State]
         */
        fun onStateChange(state: State)
    }
}