package com.example.calculator

import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }



    private fun createButton(character : String) : TextView {
        var view = TextView(this.baseContext)
        if (character.isNotEmpty()) {
            view.text = character
            view.gravity = Gravity.CENTER

            val outValue = TypedValue()
            baseContext.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            view.setBackgroundResource(outValue.resourceId)
            view.focusable = View.FOCUSABLE
            view.isClickable = true
            view.setTextColor(resources.getColor(R.color.black))
            view.textSize = 40f
        }

        var params = GridLayout.LayoutParams()
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        params.width = 0
        params.height = 0
        view.layoutParams = params
        return view
    }

    private fun initialize() {
        var displayMetrics = Utils.getDisplayMetrics(this)
        var displayWidth = displayMetrics.widthPixels * 1f
        var displayHeight = displayMetrics.heightPixels * 1f

        var characters = arrayOf(
            "AC", "%", "(", ")", "",
            "7", "8", "9", "/", "C",
            "4", "5", "6", "*", "",
            "1", "2", "3", "-", "",
            "0", ".", "", "+", "=")

        // default ratio
        glContent.minimumHeight = (displayHeight * 2 / 3).toInt()
        etResult.textSize = (displayHeight * 1 / 3 * 3 / 5) / 8f
        etExpression.textSize = (displayHeight * 1 / 3 * 2 / 5) / 8f

        glContent.columnCount = 5
        for (element in characters) {
            var view = createButton(element)
            glContent.addView(view)
        }

        etResult.inputType = InputType.TYPE_NULL
        etExpression.showSoftInputOnFocus = false
    }
}
