package com.example.calculator.other

import android.app.Activity
import android.util.DisplayMetrics


object Utils {
    fun getDisplayMetrics(activity: Activity) : DisplayMetrics {
        var res = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(res)
        return res
    }

    class ObserverData<T>() {
        private var item: T? = null

        private val handlers : MutableList<(T) -> Unit> = mutableListOf()
        fun register(handler: (T)->Unit) {
            handlers.add(handler)
        }
        fun broadcast() {
            for (handler in handlers)
                handler.invoke(item as T)
        }
        fun setValue(value: T) {
            this.item = value
            broadcast()
        }
        fun getValue() : T? = this.item
    }
}