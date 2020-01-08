package com.example.calculator.application.expressionhistory


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.calculator.R

/**
 * A simple [Fragment] subclass.
 */
class ExpressionHistoryFragment : Fragment() {

    var viewModel = ExpressionHistoryViewModel()
    lateinit var rvExpressionHistory : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_expression_history, container, false)

        rvExpressionHistory = view.findViewById<RecyclerView>(R.id.rvExpressionHistory)
        rvExpressionHistory.layoutManager = LinearLayoutManager(this.context)

        viewModel.observerRecords.register {
            it.reverse()
            val adapter = ExpressionHistoryAdapter(it, this.context!!)
            rvExpressionHistory.adapter = adapter
        }

        viewModel.loadData()

        return view
    }

    fun clearData() {
        viewModel.clearData()
    }
}
