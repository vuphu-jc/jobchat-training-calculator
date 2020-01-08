package com.example.calculator.domain.historyexpression

import com.example.calculator.domain.Base

interface ExpressionHistoryDomain {
    interface Repository {
        fun fetch(): MutableList<ExpressionHistory>
        fun add(data: ExpressionHistory)
        fun clear()
    }

    interface UseCase {
        interface GetAllData :
            Base.UseCase<GetAllData.Params, GetAllData.Response> {
            class Params() {}
            interface Response {
                fun onRetrieved(result: MutableList<ExpressionHistory>)
                fun onError(message: String)
            }
        }

        interface AddData :
            Base.UseCase<AddData.Params, AddData.Response> {
            class Params(val data: ExpressionHistory) {}
            interface Response {
                fun onAdded()
                fun onError(message: String)
            }
        }

        interface ClearData :
                Base.UseCase<ClearData.Params, ClearData.Response> {
            class Params() {}
            interface Response {
                fun onSuccess()
                fun onError(message: String)
            }
        }
    }
}