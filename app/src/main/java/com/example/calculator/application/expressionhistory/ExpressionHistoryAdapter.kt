package com.example.calculator.application.expressionhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import com.example.calculator.domain.historyexpression.ExpressionHistory

class ExpressionHistoryAdapter(private val data: MutableList<ExpressionHistory>,
                               private val context: Context):
    RecyclerView.Adapter<ExpressionHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(context).inflate(R.layout.item_expression_history, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDate.text = data[position].date
        holder.tvExpression.text = data[position].expression
        holder.tvResult.text = data[position].result
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvExpression: TextView = itemView.findViewById(R.id.tvExpression)
        var tvResult: TextView = itemView.findViewById(R.id.tvResult)
    }
}