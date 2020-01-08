package com.example.calculator.application.main

import com.example.calculator.Global
import com.example.calculator.domain.historyexpression.ExpressionHistory
import com.example.calculator.domain.historyexpression.ExpressionHistoryDomain
import com.example.calculator.domain.historyexpression.ExpressionHistoryUseCase
import com.example.calculator.other.Expression
import com.example.calculator.other.Utils
import java.util.*

class MainViewModel {
    val observerExpression = Utils.ObserverData<String>()
    val observerResult = Utils.ObserverData<String>()
    val observerSaveData = Utils.ObserverData<Boolean>()

    private val ucAddData = ExpressionHistoryUseCase.AddData(Global.getHistoryExpressionRepository())

    fun calculate() {
        var exp = Expression(observerExpression.getValue()!!)
        var res = exp.getResult()
        if (res != null)
            observerResult.setValue(res)
        else
            observerResult.setValue("")
    }

    fun saveData() {
        if (observerResult.getValue().isNullOrEmpty()) {
            observerSaveData.setValue(false)
        } else {
            var data = ExpressionHistory(Calendar.getInstance().time.toString(), observerExpression.getValue()!!, observerResult.getValue()!!)
            ucAddData.run(ExpressionHistoryDomain.UseCase.AddData.Params(data),
                object: ExpressionHistoryDomain.UseCase.AddData.Response {
                    override fun onAdded() {
                        observerSaveData.setValue(true)
                    }

                    override fun onError(message: String) {
                        observerSaveData.setValue(false)
                    }
                })
        }
    }
}