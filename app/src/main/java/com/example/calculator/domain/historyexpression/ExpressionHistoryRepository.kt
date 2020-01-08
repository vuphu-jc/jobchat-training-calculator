package com.example.calculator.domain.historyexpression

import android.content.Context

class HistoryExpressionInMemoryRepository :
    ExpressionHistoryDomain.Repository {

    companion object {
        const val TABLE_NAME: String = "EXPRESSION"
        private val SIZE_TITLE: String = "SIZE"
        private val DATA_TITLE: String = "DATA"
    }

    var context: Context? = null
    constructor(context: Context) {
        this.context = context
    }

    override fun fetch(): MutableList<ExpressionHistory> {
        var result : MutableList<ExpressionHistory> = mutableListOf()
        var sharedPref = context?.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            val size= sharedPref.getInt(SIZE_TITLE, 0)
            for (i in 0 until size) {
                val json = sharedPref.getString("$SIZE_TITLE-$i", "{}")
                result.add(ExpressionHistory.fromJson(json as String))
            }
        }
        return result
    }

    override fun add(data: ExpressionHistory) {
        var sharedPref = context?.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            val size= sharedPref.getInt(SIZE_TITLE, 0)
            var editor = sharedPref.edit()
            editor.putString("$DATA_TITLE-$size", data.toJson())
            editor.putInt(SIZE_TITLE, 1 + size)
            editor.apply()
        }
    }

    override fun clear() {
        var sharedPref = context?.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            var editor = sharedPref.edit()
            editor.clear()
            editor.putInt(SIZE_TITLE, 0)
            editor.apply()
        }
    }
}