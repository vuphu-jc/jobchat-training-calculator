package com.example.calculator.other

import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

class Expression {

     companion object InfixToPostfix {
        private fun isOperator(s: String) : Boolean {
            var pattern = "\\+|\\-|\\*|\\/|\\%".toRegex()
            return pattern.matches(s)
        }

        private fun getPriority(operator: String) : Int {
            if (operator == "*" || operator == "/" || operator == "%")
                return 2
            if (operator == "+" || operator == "-")
                return 1
            return 0
        }

        private fun run(infix: MutableList<String>) : MutableList<String> {
            var postfix : MutableList<String> = mutableListOf()
            var stack : Stack<String> = Stack()

            infix.forEach {
                if (isOperator(
                        it
                    )
                ) {
                    while (stack.size > 0 && getPriority(
                            it
                        ) <= getPriority(
                            stack.peek()
                        )
                    )
                        postfix.add(stack.pop())
                    stack.push(it)
                }
                else if (it == "(")
                    stack.push(it)
                else if (it == ")") {
                    var s = stack.pop()
                    while (s != "(") {
                        postfix.add(s)
                        s = stack.pop()
                    }
                } else      // operand
                    postfix.add(it)
            }
            while (stack.size > 0)
                postfix.add(stack.pop())

            return postfix
        }
    }


    val TAG: String = "EXPRESSION"
    var infix: MutableList<String> = mutableListOf()

    constructor(rawExpression: String) {
        val expression = formatExpression(rawExpression)
        infix = detachExpression(expression)
    }

    private fun formatExpression(rawExpression: String) : String {
        if (rawExpression.isEmpty())
            return rawExpression

        var builder = StringBuilder(rawExpression)

        var multiplePosition = "\\)\\(|\\d\\(|\\)\\d".toRegex().findAll(rawExpression).toList().reversed()
        multiplePosition.forEach {
            var idx = it.range.first + 1
            builder.insert(idx, "*")
        }

        var zeroPosition = "(^[-|+])|\\([+|-]".toRegex().findAll(rawExpression).toList().reversed()
        zeroPosition.forEach {
            var idx = it.range.first
            builder.insert(idx, "0")
        }

        return builder.toString()
    }

    private fun detachExpression(rawExpression: String) : MutableList<String> {
        var pattern = "\\+|-|\\*|/|%|\\(|\\)|\\d+\\.?\\d*|\\.\\d+".toRegex()
        var result : MutableList<String> = mutableListOf()
        for (element in pattern.findAll(rawExpression)) {
            result.add(element.value)
        }
        return result
    }

    fun getResult() : String? {
        try {
            var postfix =
                run(infix)

            var stack: Stack<Float> = Stack()
            postfix.forEach {
                if (isOperator(
                        it
                    )
                ) {
                    if (it == "%") {
                        var value = stack.pop()
                        value *= 0.01f
                        stack.push(value)
                    } else {
                        var second = stack.pop()
                        var first = stack.pop()
                        var res : Float = 0f
                        when (it) {
                            "+" -> res = first + second
                            "-" -> res = first - second
                            "*" -> res = first * second
                            "/" -> res = first / second
                        }
                        stack.push(res)
                    }
                } else {
                    stack.push(it.toFloat())
                }
            }
            var res = stack.pop()
            if (stack.size > 0)
                return null
            if (res.compareTo(res.toInt()) == 0)
                return res.toInt().toString()
            return res.toString()
        } catch (e: Exception) {
            return null
        }
    }
}