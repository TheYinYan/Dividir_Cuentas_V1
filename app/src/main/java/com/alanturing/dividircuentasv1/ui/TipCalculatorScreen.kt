package com.alanturing.dividircuentasv1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.remote.creation.toFloat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alanturing.dividircuentasv1.R
import com.alanturing.dividircuentasv1.ui.theme.DividirCuentasV1Theme

@Composable
@Preview
fun TipCalculatorScreenPreview(){
    DividirCuentasV1Theme {
        TipCalculatorScreen()
    }
}

@Composable
fun TipCalculatorScreen() {
    var totalAmount = remember { TextFieldState("0.0") }
    val guestNumber = remember { TextFieldState("0") }
    var checked by remember { mutableStateOf(false) }
    var tip by remember { mutableStateOf(0f) }
    var showCalculate by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val columnModifier = Modifier
            .consumeWindowInsets(innerPadding)
            .padding(innerPadding)
        Column(
            modifier = columnModifier
        ) {
            val textFieldModifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
            val customQualityKeyboardOption = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            )
            TextField(
                modifier = textFieldModifier,
                state = totalAmount,
                keyboardOptions = customQualityKeyboardOption

            )
            val customQualityKeyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
            TextField(
                modifier = textFieldModifier,
                state = guestNumber,
                keyboardOptions = customQualityKeyboardOptions
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(stringResource(R.string.RoundUpTip))
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        newChecked ->
                        checked = newChecked
                        if (!checked) tip = 0f
                    },
                )
            }
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)) {
                Text(stringResource(R.string.tipLabel), fontWeight = FontWeight.Bold)
            }
            Slider(
                enabled = checked,
                value = tip,
                onValueChange = {
                    tip = it
                },
                steps = 3,
                valueRange = 0f..4f
            )
            var totalCalcular by remember { mutableStateOf(0F) }
            val isButtonEnable = guestNumber.text.toString().toInt() > 0 && totalAmount.text.toString().toFloat() > 0
            var totalDiv by remember { mutableStateOf(0F) }
            Button(
                enabled = isButtonEnable ,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showCalculate = true
                    totalCalcular = totalAmount.text.toString().toFloat()
                    when(tip){
                        0F -> totalCalcular *= 1F
                        1F -> totalCalcular *= 1.05F
                        2F -> totalCalcular *= 1.10F
                        3F -> totalCalcular *= 1.15F
                        4F -> totalCalcular *= 1.20F
                    }
                    totalDiv = totalCalcular / guestNumber.text.toString().toFloat()
                }
            ) {
                Icon(painterResource(R.drawable.Icon_Calculo),
                    contentDescription = "Calcular")
                Text(stringResource(R.string.Calculate))
            }
            if (showCalculate){
                Text("Cantidad Total: ${totalAmount.text.toString().toInt()}")
                Text("Cada uno: ${totalDiv.toInt()}")
            }
        }
    }
}
