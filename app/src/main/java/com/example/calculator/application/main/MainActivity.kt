package com.example.calculator.application.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.Global
import com.example.calculator.R
import com.example.calculator.application.expressionhistory.ExpressionHistoryActivity

class MainActivity : AppCompatActivity() {

    init {
        Global.initialize(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.beginTransaction()
//            .add(R.id.main_fragment, MainFragment())
//            .addToBackStack(null)
//            .commit()
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
}
