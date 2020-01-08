package com.example.calculator.application.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.example.calculator.R

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