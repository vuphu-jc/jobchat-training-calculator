package com.example.calculator.application.historyexpression

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.R
import com.example.calculator.application.main.AboutDialog
import kotlinx.android.synthetic.main.activity_history_expression.*

class ExpressionHistoryActivity : AppCompatActivity() {

    var viewModel = ExpressionHistoryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_expression)

        rvExpressionHistory.layoutManager = LinearLayoutManager(this)

        viewModel.observerRecords.register {
            it.reverse()
            var adapter = ExpressionHistoryAdapter(it, this)
            rvExpressionHistory.adapter = adapter
        }

        viewModel.loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.expression_history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id= item.itemId
        if (id == R.id.clear) {
            viewModel.clearData()
        }

        return super.onOptionsItemSelected(item)
    }
}
