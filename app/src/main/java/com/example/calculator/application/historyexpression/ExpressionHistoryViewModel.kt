package com.example.calculator.application.historyexpression

import com.example.calculator.Global
import com.example.calculator.domain.historyexpression.ExpressionHistory
import com.example.calculator.domain.historyexpression.ExpressionHistoryDomain
import com.example.calculator.domain.historyexpression.ExpressionHistoryUseCase
import com.example.calculator.other.Utils

class ExpressionHistoryViewModel {
    var observerRecords = Utils.ObserverData<MutableList<ExpressionHistory>>()

    fun loadData() {
        val ucLoadData = ExpressionHistoryUseCase.GetAllData(Global.getHistoryExpressionRepository())
        ucLoadData.run(
            ExpressionHistoryDomain.UseCase.GetAllData.Params(),
            object : ExpressionHistoryDomain.UseCase.GetAllData.Response {
                override fun onRetrieved(result: MutableList<ExpressionHistory>) {
                    observerRecords.setValue(result)
                }

                override fun onError(message: String) {
                }
            })
    }

    fun clearData() {
        val ucClearData = ExpressionHistoryUseCase.ClearData(Global.getHistoryExpressionRepository())
        ucClearData.run(
            ExpressionHistoryDomain.UseCase.ClearData.Params(),
            object : ExpressionHistoryDomain.UseCase.ClearData.Response {
                override fun onSuccess() {
                    observerRecords.setValue(mutableListOf())
                }

                override fun onError(message: String) {
                }

            })
    }
}