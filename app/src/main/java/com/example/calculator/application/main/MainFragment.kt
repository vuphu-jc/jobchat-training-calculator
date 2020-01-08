package com.example.calculator.application.main


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView

import com.example.calculator.R
import com.example.calculator.other.Utils

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var viewModel : MainViewModel

    lateinit var glContent: GridLayout
    lateinit var etExpression: EditText
    lateinit var etResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        glContent = view.findViewById(R.id.glContent)
        etExpression = view.findViewById(R.id.etExpression)
        etResult = view.findViewById(R.id.etResult)

        viewModel = MainViewModel()
        initialize()

        return view
    }

    private fun updateResult() {
        viewModel.calculate()
    }

    private fun createButton(character : String) : View {
        if (character == "_") {
            return ButtonBuilder.create(this.context!!).build()
        } else {
            var builder = ButtonBuilder.create(this.context!!).setTitle(character).setClickable(true)
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
        var displayMetrics = Utils.getDisplayMetrics(this.activity!!)
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
