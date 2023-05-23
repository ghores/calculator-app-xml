package com.ghores.calculatorapp

import android.util.Log
import java.util.Stack

class Expression(var infixExpression: MutableList<String>) {
    private var postFix: String = ""
    private fun infixToPostfix() {
        Log.d("infixToPostFixFunction", "infixExpresion is : $infixExpression")
        var result = ""
        val stack = Stack<String>()
        for (element in infixExpression) {
            if (element.all { it.isDigit() } || element.any { it == '.' }) {
                result += "$element "
            } else if (element == "(") {
                stack.push(element)
            } else if (element == ")") {
                while (stack.peek() != "(" && stack.isNotEmpty()) {
                    result += "${stack.pop()} "
                }
                if (stack.isNotEmpty())
                    stack.pop()
            } else {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(element)) {
                    result += "${stack.pop()} "
                }
                stack.push(element)
            }
        }
        while (stack.isNotEmpty()) {
            result += "${stack.pop()} "
        }
        postFix = result
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            "×", "÷" -> 2
            "+", "-" -> 1
            else -> -1
        }
    }

    fun evaluateExpression(): Number {
        infixToPostfix()
        val stack = Stack<Double>()
        var i = 0
        while (i < postFix.length) {
            if (postFix[i] == ' ') {
                i++
                continue
            } else if (Character.isDigit(postFix[i])) {
                var number = ""
                while (Character.isDigit(postFix[i]) || postFix[i] == '.') {
                    number += postFix[i]
                    i++
                }
                stack.push(number.toDouble())
            } else {
                val x = stack.pop()
                val y = stack.pop()
                when (postFix[i]) {
                    '×' -> stack.push(x * y)
                    '÷' -> stack.push(y / x)
                    '+' -> stack.push(x + y)
                    '-' -> stack.push(y - x)
                }
            }
            i++
        }
        return if (stack.peek() / stack.peek().toInt() == 1.0) stack.peek()
            .toInt() else stack.peek()
    }
}