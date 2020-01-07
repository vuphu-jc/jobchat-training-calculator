package com.example.calculator

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import kotlinx.android.synthetic.main.view_about.*

class AboutDialog(context: Context) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_about)

        initialize()
    }

    private fun initialize() {
        findViewById<ImageView>(R.id.btnClose).setOnClickListener {
            dismiss()
        }
    }
}