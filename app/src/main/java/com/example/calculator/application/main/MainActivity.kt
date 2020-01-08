package com.example.calculator.application.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.Global
import com.example.calculator.R
import com.example.calculator.application.historyexpression.ExpressionHistoryActivity
import com.example.calculator.other.Utils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Global.initialize(this)
        viewModel = MainViewModel()
        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.about) {
            var dialog = AboutDialog(this)
            dialog.show()
        } else if (id == R.id.history) {
            var intent = Intent(this, ExpressionHistoryActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateResult() {
        viewModel.calculate()
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
                    viewModel.saveData()
                })
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            etExpression.showSoftInputOnFocus = false
        }
        etExpression.requestFocus()
        etExpression.setOnTouchListener { v, event ->
            v?.onTouchEvent(event)
            etExpression.error = null
            true
        }

        // 1 way binding
        viewModel.observerSaveData.register {
            if (!it) {
                etResult.requestFocus()
                etExpression.error = ""
            } else {
                etExpression.setText(etResult.text)
                etExpression.setSelection(etResult.text.length)
                etResult.text = ""
            }
        }

        // 2 way binding
        viewModel.observerExpression.register {
            if (it != etExpression.text.toString())
                etExpression.setText(it)

        }
        viewModel.observerResult.register {
            if (it != etResult.text.toString())
                etResult.text = it
        }

        etExpression.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.observerExpression.setValue(etExpression.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        etResult.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.observerResult.setValue(etResult.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
}
