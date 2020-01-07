package com.example.calculator

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService


object Utils {
    fun getDisplayMetrics(activity: Activity) : DisplayMetrics {
        var res = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(res)
        return res
    }
}