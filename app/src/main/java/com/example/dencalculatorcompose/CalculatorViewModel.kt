package com.example.dencalculatorcompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var input = mutableStateOf("")
        private set
    var isError = mutableStateOf(false)
        private set

    private var number1 = ""
    private var number2 = ""
    private var operation = ""

    fun getInputByClicking(sign: String) {
        when (sign) {
            "AC" -> clear()
            "+","-","×","÷" -> setOperation(sign)
            "%" -> percentResult()
            "±" -> change()
            "=" -> getResult()
            else -> if (input.value.length < 10) setInput(sign)
        }
    }

    fun backspace() {
        if (isError.value) {
            clear()
        }
        else if (input.value.isNotEmpty()) {
            input.value = input.value.dropLast(1)
        }
    }

    private fun change() {
        if (input.value.isNotEmpty()) {
            if (input.value[0] == '-') {
                input.value = input.value.drop(1)
            }
            else {
                input.value = "-${input.value}"
            }
        }
    }

    private fun setInput(sign: String) {
        if (isError.value) {
            clear()
        }
        input.value += sign
    }

    private fun setOperation(sign: String) {
        if (!isError.value) {
            operation = sign
            number1 = input.value
            input.value = ""
        }
    }

    private fun clear() {
        input.value = ""
        operation = ""
        isError.value = false
    }

    private fun getResult() {
        if (setSecondNumberAndCheck() && number1.isNotEmpty() &&
            number2.isNotEmpty() && operation.isNotEmpty()) {

            number1 = number1.replace(',', '.')
            number2 = number2.replace(',', '.')

            val result = when (operation) {
                "+" -> number1.toDouble() + number2.toDouble()
                "-" -> number1.toDouble() - number2.toDouble()
                "×" -> number1.toDouble() * number2.toDouble()
                "÷" -> number1.toDouble() / number2.toDouble()
                else -> 0.0
            }
            setStringResult(result)
        }
    }

    private fun percentResult() {
        if (setSecondNumberAndCheck() && number1.isNotEmpty() &&
            number2.isNotEmpty() && operation.isNotEmpty()) {

            number1 = number1.replace(',', '.')
            number2 = number2.replace(',', '.')

            val result = when (operation) {
                "+" -> number1.toDouble() + number2.toDouble() * number1.toDouble() / 100
                "-" -> number1.toDouble() - number2.toDouble() * number1.toDouble() / 100
                "×" -> number1.toDouble() * number2.toDouble() / 100
                "÷" -> number1.toDouble() / (number2.toDouble() / 100)
                else -> 0.0
            }
            setStringResult(result)
        }

    }

    private fun setSecondNumberAndCheck(): Boolean {
        number2 = input.value

        if (number2 == "0" && operation == "÷") {
            isError.value = true
            return false
        }
        return true
    }

    private fun setStringResult(result: Double) {
        number1 = if (result.compareTo(result.toInt()) == 0) {
            result.toInt().toString()
        } else {
            result.toString().replace('.', ',')
        }
        input.value = number1
    }
}