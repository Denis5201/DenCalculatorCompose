package com.example.dencalculatorcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dencalculatorcompose.ui.theme.gray
import com.example.dencalculatorcompose.ui.theme.lineColor
import com.example.dencalculatorcompose.ui.theme.red
import com.example.dencalculatorcompose.ui.theme.whiteGray

@Composable
fun CalculatorScreen() {
    val viewModel: CalculatorViewModel = viewModel()

    val buttons = listOf(
        listOf(CalcButton.AC, CalcButton.PLUSMINUS, CalcButton.PERCENT, CalcButton.DIVISION),
        listOf(CalcButton.SEVEN, CalcButton.EIGHT, CalcButton.NINE, CalcButton.MULTIPLICATION),
        listOf(CalcButton.FOUR, CalcButton.FIVE, CalcButton.SIX, CalcButton.SUBTRACTION),
        listOf(CalcButton.ONE, CalcButton.TWO, CalcButton.THREE, CalcButton.ADDITION),
        listOf(CalcButton.ZERO, CalcButton.COMMA, CalcButton.EQUALS)
    )

    val input by viewModel.input.observeAsState()
    val isError by viewModel.isError.observeAsState(false)
    val isBackspaceEnabled by viewModel.isBackspaceEnabled.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.calculator),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = if (isError) stringResource(R.string.error) else input!!,
            modifier = Modifier.padding(top = 60.dp),
            style = MaterialTheme.typography.h1,
            color = if (isError)
                    MaterialTheme.colors.primaryVariant
                else
                    MaterialTheme.colors.primary,
            maxLines = 1
        )

        IconButton(
            onClick = { viewModel.backspace() },
            modifier = Modifier
                .padding(top = 40.dp, end = 12.dp)
                .align(Alignment.End),
            enabled = isBackspaceEnabled
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.backspace),
                contentDescription = null,
                tint = if (isBackspaceEnabled) whiteGray else Color.Gray
            )
        }
        Divider(
            modifier = Modifier.padding(top = 32.dp),
            thickness = 1.dp,
            color = lineColor
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            buttons.forEach {
                SimpleInputRow(it, viewModel::getInputByClicking, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SimpleInputRow(listSign: List<CalcButton>, click: (CalcButton) -> Unit, modifier: Modifier) {
    val addWeight = if (listSign.size == 3) 1.2f else 0f
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CalculatorButton(sign = listSign[0], isRight = true, click, Modifier.weight(1f + addWeight))
        Spacer(modifier = Modifier.padding(8.dp))
        for (i in 1 until listSign.size - 1) {
            CalculatorButton(sign = listSign[i], isRight = true, click, Modifier.weight(1f))
            Spacer(modifier = Modifier.padding(8.dp))
        }
        CalculatorButton(sign = listSign[listSign.size - 1], isRight = false, click, Modifier.weight(1f))
    }
}

@Composable
fun CalculatorButton(sign: CalcButton, isRight: Boolean, click: (CalcButton) -> Unit, modifier: Modifier) {
    Button(
        onClick = { click(sign) },
        modifier = modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isRight) gray else red
        )
    ) {
        Text(
            text = sign.sign,
            style = MaterialTheme.typography.body1,
            color = if (isRight) MaterialTheme.colors.secondary else MaterialTheme.colors.secondaryVariant
        )
    }
}