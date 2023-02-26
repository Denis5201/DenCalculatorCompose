package com.example.dencalculatorcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val _input = MutableLiveData("")
    val input: LiveData<String> = _input

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private var number1 = Constants.EMPTY_STRING
    private var number2 = Constants.EMPTY_STRING
    private var operation = Constants.EMPTY_STRING

    fun getInputByClicking(sign: CalcButton) {
        when (sign) {
            CalcButton.AC -> clear()
            CalcButton.ADDITION, CalcButton.SUBTRACTION, CalcButton.MULTIPLICATION, CalcButton.DIVISION -> setOperation(sign.name)
            CalcButton.PERCENT -> percentResult()
            CalcButton.PLUSMINUS -> change()
            CalcButton.EQUALS -> getResult()
            else -> if (input.value!!.length < 10) setInput(sign.sign)
        }
    }

    fun backspace() {
        if (_isError.value!!) {
            clear()
        }
        else if (_input.value!!.isNotEmpty()) {
            _input.value = _input.value!!.dropLast(1)
        }
    }

    private fun change() {
        if (_input.value!!.isNotEmpty()) {
            if (_input.value!![0] == Constants.MINUS) {
                _input.value = _input.value!!.drop(1)
            }
            else {
                _input.value = "${Constants.MINUS}${_input.value}"
            }
        }
    }

    private fun setInput(sign: String) {
        if (_isError.value!!) {
            clear()
        }
        _input.value += sign
    }

    private fun setOperation(sign: String) {
        if (!_isError.value!!) {
            operation = sign
            number1 = _input.value!!
            _input.value = Constants.EMPTY_STRING
        }
    }

    private fun clear() {
        _input.value = Constants.EMPTY_STRING
        operation = Constants.EMPTY_STRING
        _isError.value = false
    }

    private fun getResult() {
        if (setSecondNumberAndCheck() && number1.isNotEmpty() &&
            number2.isNotEmpty() && operation.isNotEmpty()) {

            number1 = number1.replace(Constants.COMMA, Constants.DOT)
            number2 = number2.replace(Constants.COMMA, Constants.DOT)

            val result = when (operation) {
                CalcButton.ADDITION.name -> number1.toDouble() + number2.toDouble()
                CalcButton.SUBTRACTION.name -> number1.toDouble() - number2.toDouble()
                CalcButton.MULTIPLICATION.name -> number1.toDouble() * number2.toDouble()
                CalcButton.DIVISION.name -> number1.toDouble() / number2.toDouble()
                else -> 0.0
            }
            setStringResult(result)
        }
    }

    private fun percentResult() {
        if (setSecondNumberAndCheck() && number1.isNotEmpty() &&
            number2.isNotEmpty() && operation.isNotEmpty()) {

            number1 = number1.replace(Constants.COMMA, Constants.DOT)
            number2 = number2.replace(Constants.COMMA, Constants.DOT)

            val result = when (operation) {
                CalcButton.ADDITION.name -> number1.toDouble() + number2.toDouble() * number1.toDouble() / 100
                CalcButton.SUBTRACTION.name -> number1.toDouble() - number2.toDouble() * number1.toDouble() / 100
                CalcButton.MULTIPLICATION.name -> number1.toDouble() * number2.toDouble() / 100
                CalcButton.DIVISION.name -> number1.toDouble() / (number2.toDouble() / 100)
                else -> 0.0
            }
            setStringResult(result)
        }
    }

    private fun setSecondNumberAndCheck(): Boolean {
        number2 = _input.value!!

        if (number2 == Constants.ZERO && operation == CalcButton.DIVISION.name) {
            _isError.value = true
            return false
        }
        return true
    }

    private fun setStringResult(result: Double) {
        number1 = if (result.compareTo(result.toInt()) == 0) {
            result.toInt().toString()
        } else {
            result.toString().replace(Constants.DOT, Constants.COMMA)
        }
        _input.value = number1
    }
}