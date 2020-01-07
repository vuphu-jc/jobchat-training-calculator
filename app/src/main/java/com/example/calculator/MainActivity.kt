package com.example.calculator

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun updateResult() {
        var exp = Expression(etExpression.text.toString())
        var res = exp.getResult()
        if (res != null)
            etResult.text = res.toString()
        else
            etResult.text = ""
    }

    private fun createButton(character : String) : View {
        if (character == "_") {
            return ButtonBuilder.create(this).build()
        } else {
            var builder = ButtonBuilder.create(this).setTitle(character).setClickable(true)
            if (character == "AC")
                builder.setOnClickListener(View.OnClickListener {
                    etExpression.text.clear()
                    etResult.text = ""
                })
            else if (character == "C")
                builder.setOnClickListener(View.OnClickListener {
                    if (etExpression.selectionStart != etExpression.selectionEnd)
                        etExpression.text.delete(etExpression.selectionStart, etExpression.selectionEnd)
                    else if (etExpression.selectionStart > 0)
                        etExpression.text.delete(etExpression.selectionStart - 1, etExpression.selectionEnd)
                    updateResult()
                })
            else if (character == "=")
                builder.setOnClickListener(View.OnClickListener {
                    if (etResult.text.isNullOrEmpty()) {
                        etResult.requestFocus()
                        etExpression.error = ""
                    }
                })
            else if (character == ".") {
                builder.setOnClickListener(View.OnClickListener {
                    etExpression.text.insert(etExpression.selectionStart, character)
                    updateResult()
                })
            }
            else
                builder.setOnClickListener(View.OnClickListener {
                    etExpression.text.insert(etExpression.selectionStart, character)
                    updateResult()
                })

            return builder.build()
        }
    }

    private fun initialize() {
        var displayMetrics = Utils.getDisplayMetrics(this)
        var displayWidth = displayMetrics.widthPixels * 1f
        var displayHeight = displayMetrics.heightPixels * 1f

        var characters = arrayOf(
            "AC", "%", "(", ")", "_",
            "7", "8", "9", "/", "C",
            "4", "5", "6", "*", "_",
            "1", "2", "3", "-", "_",
            "0", ".", "_", "+", "=")

        // default ratio
        glContent.columnCount = 5
        glContent.minimumHeight = (displayHeight * 2 / 3).toInt()
        etResult.textSize = (displayHeight * 1 / 3 * 3 / 5) / 8f
        etExpression.textSize = (displayHeight * 1 / 3 * 2 / 5) / 8f
        for (element in characters)
            glContent.addView(createButton(element))

        etExpression.showSoftInputOnFocus = false
        etExpression.requestFocus()

        etExpression.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                v?.onTouchEvent(event)
                etExpression.error = null
                return true
            }
        })
    }
}
