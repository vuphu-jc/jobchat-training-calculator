package com.example.calculator.application.expressionhistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.calculator.R

class ExpressionHistoryActivity : AppCompatActivity() {

    var fragment = ExpressionHistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_expression)

        fragment = supportFragmentManager.fragments[0] as ExpressionHistoryFragment
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.expression_history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id= item.itemId
        if (id == R.id.clear) {
            fragment.clearData()
        }

        return super.onOptionsItemSelected(item)
    }
}
