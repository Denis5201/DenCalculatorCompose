package com.example.dencalculatorcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val _input = MutableLiveData("")
    val input: LiveData<String> = _input

    private val _isBackspaceEnabled = MutableLiveData(false)
    val isBackspaceEnabled: LiveData<Boolean> = _isBackspaceEnabled

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private var number1 = EMPTY_STRING
    private var number2 = EMPTY_STRING
    private var operation: CalcButton? = null

    fun getInputByClicking(sign: CalcButton) {
        when (sign) {
            CalcButton.AC -> clear()
            CalcButton.ADDITION, CalcButton.SUBTRACTION, CalcButton.MULTIPLICATION, CalcButton.DIVISION -> setOperation(sign)
            CalcButton.PERCENT -> getResult(true)
            CalcButton.PLUSMINUS -> changeSignOfNumber()
            CalcButton.EQUALS -> getResult()
            else -> if (input.value!!.length < 10) setInput(sign.sign)
        }
        _isBackspaceEnabled.value = _input.value!!.isNotEmpty()
    }

    fun backspace() {
        if (_isError.value!!) {
            clear()
        }
        else if (_input.value!!.isNotEmpty()) {
            _input.value = _input.value!!.dropLast(1)
        }
    }

    private fun changeSignOfNumber() {
        if (_input.value!!.isEmpty()) {
            return
        }
        if (_input.value!![0] == MINUS) {
            _input.value = _input.value!!.drop(1)
        }
        else {
            _input.value = "${MINUS}${_input.value}"
        }
    }

    private fun setInput(sign: String) {
        if (_isError.value!!) {
            clear()
        }
        _input.value += sign
    }

    private fun setOperation(sign: CalcButton) {
        if (!_isError.value!!) {
            operation = sign
            number1 = _input.value!!
            _input.value = EMPTY_STRING
        }
    }

    private fun clear() {
        _input.value = EMPTY_STRING
        operation = null
        number1 = EMPTY_STRING
        number2 = EMPTY_STRING
        _isError.value = false
    }

    private fun getResult(isPercent: Boolean = false) {

        if (number2.isEmpty() && operation == null && isPercent) {
            setStringResult(_input.value!!.toDouble() / 100)
            return
        }
        if (checkInputBeforeCalculating()) {

            number1 = number1.replace(COMMA, DOT)
            number2 = number2.replace(COMMA, DOT)

            var secondNumber = number2.toDouble()

            if (isPercent) {
                secondNumber = when (operation) {
                    CalcButton.ADDITION, CalcButton.SUBTRACTION -> secondNumber * number1.toDouble() / 100
                    CalcButton.MULTIPLICATION, CalcButton.DIVISION -> secondNumber / 100
                    else -> secondNumber
                }
            }
            val result = when (operation) {
                CalcButton.ADDITION -> number1.toDouble() + secondNumber
                CalcButton.SUBTRACTION-> number1.toDouble() - secondNumber
                CalcButton.MULTIPLICATION -> number1.toDouble() * secondNumber
                CalcButton.DIVISION -> number1.toDouble() / secondNumber
                else -> 0.0
            }
            setStringResult(result)
        }
    }

    private fun checkInputBeforeCalculating() =
        setSecondNumberAndCheck() && number1.isNotEmpty() && number2.isNotEmpty() && operation != null

    private fun setSecondNumberAndCheck(): Boolean {
        number2 = _input.value!!

        if (number2 == ZERO && operation == CalcButton.DIVISION) {
            _isError.value = true
            return false
        }
        return true
    }

    private fun setStringResult(result: Double) {
        number1 = if (result.compareTo(result.toInt()) == 0) {
            result.toInt().toString()
        } else {
            result.toString().replace(DOT, COMMA)
        }
        _input.value = number1
    }

    private companion object Constants {
        const val EMPTY_STRING = ""
        const val ZERO = "0"
        const val COMMA = ','
        const val DOT = '.'
        const val MINUS = '-'
    }
}