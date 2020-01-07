package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        var characters = arrayOf(
            "7", "8", "9", "/", "<-",
            "4", "5", "6", "*", "(",
            "1", "2", "3", "-", ")",
            "-", ".", "", "+", "=")

        glContent.columnCount = 5
        for (char in characters) {
            var textView = TextView(this.baseContext)
            textView.setText(char)
            textView.gravity = Gravity.CENTER
            var params = GridLayout.LayoutParams()
            params.setGravity(GridLayout.LayoutParams.FILL_PARENT)
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            textView.layoutParams = params
            glContent.addView(textView)
        }
    }
}
