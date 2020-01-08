package com.example.calculator.domain.historyexpression

import android.content.Context

class HistoryExpressionInMemoryRepository :
    ExpressionHistoryDomain.Repository {

    companion object {
        const val TABLE_NAME: String = "EXPRESSION"
    }

    var context: Context? = null
    constructor(context: Context) {
        this.context = context
    }

    override fun fetch(): MutableList<ExpressionHistory> {
        var result : MutableList<ExpressionHistory> = mutableListOf()
        var sharedPref = context?.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            val size= sharedPref.getInt("SIZE", 0)
            for (i in 0 until size) {
                val json = sharedPref.getString("DATA-$i", "{}")
                result.add(ExpressionHistory.fromJson(json as String))
            }
        }
        return result
    }

    override fun add(data: ExpressionHistory) {
        var sharedPref = context?.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            val size= sharedPref.getInt("SIZE", 0)
            var editor = sharedPref.edit()
            editor.putString("DATA-$size", data.toJson())
            editor.putInt("SIZE", 1 + size)
            editor.apply()
        }
    }

    override fun clear() {
        var sharedPref = context?.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            var editor = sharedPref.edit()
            editor.clear()
            editor.putInt("SIZE", 0)
            editor.apply()
        }
    }
}