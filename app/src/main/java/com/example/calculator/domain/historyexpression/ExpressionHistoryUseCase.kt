package com.example.calculator.domain.historyexpression

object ExpressionHistoryUseCase {
    class GetAllData(private val repository: ExpressionHistoryDomain.Repository) :
        ExpressionHistoryDomain.UseCase.GetAllData {

        override fun run(
            params: ExpressionHistoryDomain.UseCase.GetAllData.Params,
            response: ExpressionHistoryDomain.UseCase.GetAllData.Response
        ) {
            var data = repository.fetch()
            response.onRetrieved(data)
        }
    }

    class AddData(private val repository: ExpressionHistoryDomain.Repository) :
            ExpressionHistoryDomain.UseCase.AddData {

        override fun run(
            params: ExpressionHistoryDomain.UseCase.AddData.Params,
            response: ExpressionHistoryDomain.UseCase.AddData.Response
        ) {
            repository.add(params.data)
            response.onAdded()
        }
    }

    class ClearData(private val repository: ExpressionHistoryDomain.Repository) :
            ExpressionHistoryDomain.UseCase.ClearData {
        override fun run(
            params: ExpressionHistoryDomain.UseCase.ClearData.Params,
            response: ExpressionHistoryDomain.UseCase.ClearData.Response
        ) {
            repository.clear()
            response.onSuccess()
        }

    }
}