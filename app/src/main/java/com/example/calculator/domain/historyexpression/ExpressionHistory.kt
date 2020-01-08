package com.example.calculator.domain.historyexpression

import com.google.gson.Gson

data class ExpressionHistory(val date: String, val expression: String, val result: String) {
    fun toJson() : String = (Gson()).toJson(this)

    companion object {
        fun fromJson(json: String) : ExpressionHistory =
            (Gson()).fromJson(json, ExpressionHistory::class.java)
    }
}