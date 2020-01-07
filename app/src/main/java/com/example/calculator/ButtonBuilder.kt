package com.example.calculator

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView

class ButtonBuilder {
    private var mView: TextView? = null
    private var baseContext: Context? = null
    
    private constructor(context: Context) {
        mView = TextView(context)
        baseContext = context

        mView?.setTextColor(baseContext!!.resources.getColor(R.color.black))
        mView?.textSize = 40f
        mView?.gravity = Gravity.CENTER

        var params = GridLayout.LayoutParams()
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        params.width = 0
        params.height = 0
        mView?.layoutParams = params
    }

    companion object {
        fun create(context : Context) : ButtonBuilder  {
            return ButtonBuilder(context)
        }
    }
    
    fun setTitle(character: String) : ButtonBuilder {
        if (character != "_") {
            mView?.text = character
        }
        return this
    }

    fun setClickable(value: Boolean) : ButtonBuilder {
        mView?.focusable = View.FOCUSABLE
        mView?.isClickable = true
        val outValue = TypedValue()
        baseContext?.theme?.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
        mView?.setBackgroundResource(outValue.resourceId)
        return this
    }

    fun setOnClickListener(listener: View.OnClickListener) : ButtonBuilder {
        mView?.setOnClickListener(listener)
        return this
    }

    fun build() : View {
        return mView as View
    }
}