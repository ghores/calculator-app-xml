package com.ghores.calculatorapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    private val input = mutableListOf<String>()
    private var resultTextBox: TextView? = null
    private var infixExpression: Expression? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTextBox = findViewById(R.id.resultTextBox)
    }

    @SuppressLint("SetTextI18n")
    fun onCLick(button: View) {
        when (val buttonText = (button as AppCompatButton).text.toString()) {
            "=" -> {
                infixExpression = Expression(input)
                resultTextBox?.text = infixExpression!!.evaluateExpression().toString()
                input.clear()
                input.add(resultTextBox?.text.toString())
            }

            "CL" -> {
                input.clear()
                resultTextBox?.text = ""
            }

            "C" -> {
                resultTextBox?.text = "${resultTextBox?.text}".dropLast(1)
                if (resultTextBox?.text?.isNotEmpty()!! && resultTextBox?.text?.last() == ' ')
                    resultTextBox?.text = "${resultTextBox?.text}".dropLast(1)
                if (input.last().length == 1)
                    input.removeAt(input.lastIndex)
                else input[input.lastIndex] = input.last().dropLast(1)
            }

            else -> {
                if (Character.isDigit(buttonText[0]) || buttonText[0] == '.') {
                    if (input.isNotEmpty() && Character.isDigit(input.last()[0])) {
                        input[input.lastIndex] = input.last() + buttonText
                        resultTextBox?.text = "${resultTextBox?.text}${button.text}"
                    } else {
                        input.add(buttonText)
                        resultTextBox?.text = "${resultTextBox?.text} ${button.text}"
                    }
                } else {
                    input.add(buttonText)
                    resultTextBox?.text = "${resultTextBox?.text} ${button.text}"
                }
            }
        }
    }
}
