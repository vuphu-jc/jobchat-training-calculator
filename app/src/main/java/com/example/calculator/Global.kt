package com.example.calculator

import android.content.Context
import com.example.calculator.domain.historyexpression.ExpressionHistoryDomain
import com.example.calculator.domain.historyexpression.HistoryExpressionInMemoryRepository

object Global {
    private lateinit var expressionHistoryRepository: ExpressionHistoryDomain.Repository
    fun initialize(context: Context) {
        expressionHistoryRepository = HistoryExpressionInMemoryRepository(context)
    }

    fun getHistoryExpressionRepository(): ExpressionHistoryDomain.Repository {
        return expressionHistoryRepository
    }
}