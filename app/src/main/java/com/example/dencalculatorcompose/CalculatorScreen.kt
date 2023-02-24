package com.example.dencalculatorcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    val input by viewModel.input
    val isError by viewModel.isError

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
            text = if (isError) stringResource(R.string.error) else input,
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
            enabled = input.isNotEmpty()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.backspace),
                contentDescription = null,
                tint = if (input.isNotEmpty()) whiteGray else Color.Gray
            )
        }
        Divider(
            modifier = Modifier.padding(top = 32.dp),
            thickness = 1.dp, color = lineColor
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SimpleInputRow(modifier = Modifier.weight(1f), listOf("AC", "±", "%", "÷"), viewModel)
            SimpleInputRow(modifier = Modifier.weight(1f), listOf("7", "8", "9", "×"), viewModel)
            SimpleInputRow(modifier = Modifier.weight(1f), listOf("4", "5", "6", "-"), viewModel)
            SimpleInputRow(modifier = Modifier.weight(1f), listOf("1", "2", "3", "+"), viewModel)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .weight(1f)
            ) {
                CalculatorButton(sign = "0", isGray = true, viewModel, Modifier.weight(2.2f))
                Spacer(modifier = Modifier.padding(8.dp))
                CalculatorButton(sign = ",", isGray = true, viewModel, Modifier.weight(1f))
                Spacer(modifier = Modifier.padding(8.dp))
                CalculatorButton(sign = "=", isGray = false, viewModel, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SimpleInputRow(modifier: Modifier, listSign: List<String>, viewModel: CalculatorViewModel) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CalculatorButton(sign = listSign[0], isGray = true, viewModel, Modifier.weight(1f))
        Spacer(modifier = Modifier.padding(8.dp))
        CalculatorButton(sign = listSign[1], isGray = true, viewModel, Modifier.weight(1f))
        Spacer(modifier = Modifier.padding(8.dp))
        CalculatorButton(sign = listSign[2], isGray = true, viewModel, Modifier.weight(1f))
        Spacer(modifier = Modifier.padding(8.dp))
        CalculatorButton(sign = listSign[3], isGray = false, viewModel, Modifier.weight(1f))
    }
}

@Composable
fun CalculatorButton(sign: String, isGray: Boolean, viewModel: CalculatorViewModel, modifier: Modifier) {
    Button(
        onClick = { viewModel.getInputByClicking(sign) },
        modifier = modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isGray) gray else red
        )
    ) {
        Text(
            text = sign,
            style = MaterialTheme.typography.body1,
            color = if (isGray) MaterialTheme.colors.secondary else MaterialTheme.colors.secondaryVariant
        )
    }
}