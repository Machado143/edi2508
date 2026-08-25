package com.example.financeapp.ui.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.model.TransactionType
import com.example.financeapp.ui.components.DatePickerDialogComponent
import com.example.financeapp.ui.components.DateTimeField
import com.example.financeapp.ui.components.TimePickerDialogComponent
import com.example.financeapp.utils.formatCurrency
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionBottonSheet(
    transactionToEdit: Transaction? = null,
    onDismiss: () -> Unit,
    onSave: (String, Double, LocalDateTime, Boolean) -> Unit
) {
    val isEditing = transactionToEdit != null

    var description by remember { mutableStateOf(transactionToEdit?.description ?: "") }
    var amount by remember {
        mutableStateOf(
            transactionToEdit?.let {
                String.format(Locale.US, "%.0f", it.amount * 100)
            } ?: ""
        )
    }
    var amountField by remember {
        mutableStateOf(
            TextFieldValue(
                transactionToEdit?.let { formatCurrency(amount) } ?: ""
            )
        )
    }
    var isIncome by remember {
        mutableStateOf(transactionToEdit?.type != TransactionType.EXPENSE)
    }

    var descriptionError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    var dateTime by remember { mutableStateOf(transactionToEdit?.date ?: LocalDateTime.now()) }
    var formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")

    var showDatePiccker by remember { mutableStateOf(false) }
    var showTimePiccker by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Text(
                if (isEditing) "Editar transação" else "Nova transação",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            DateTimeField(
                dateTime = dateTime.format(formatter),
                onClick = {
                    showDatePiccker = true
                }
            )

            if (showDatePiccker) {
                DatePickerDialogComponent(
                    onDateSelected = { selected ->
                        dateTime = selected
                    },
                    onDismiss = {
                        showDatePiccker = false
                        showTimePiccker = true
                    }
                )
            }
            if (showTimePiccker) {
                TimePickerDialogComponent(
                    onTimeSelected = { hour, minute ->
                        dateTime = dateTime.withHour(hour).withMinute(minute)
                    },
                    onDismiss = { showTimePiccker = false }
                )
            }
            //descrição
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(),
                isError = descriptionError
            )
            Spacer(modifier = Modifier.height(8.dp))
            //Valor
            OutlinedTextField(
                value = amountField,
                onValueChange = { newValue ->

                    val digits = newValue.text.replace("\\D".toRegex(), "")
                    amount = digits
                    val formatted = formatCurrency(digits)

                    amountField = TextFieldValue(
                        text = formatted,
                        selection = androidx.compose.ui.text.TextRange(formatted.length)
                    )
                    amountError = false
                },
                label = { Text("Valor") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = amountError
            )
            Spacer(modifier = Modifier.height(8.dp))
            //Tipo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = isIncome,
                    onClick = { isIncome = true },
                    label = { Text("Entrada") }
                )
                FilterChip(
                    selected = !isIncome,
                    onClick = { isIncome = false },
                    label = { Text("Saída") })
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (descriptionError || amountError) {
                Text("Preencha todos os campos", color = Color.Red)
                //import androidx.compose.ui.graphics.Color
            }

            Button(
                onClick = {
                    val value = amount.toDoubleOrNull()?.div(100) ?: 0.0
                    descriptionError = description.isBlank()
                    amountError = value <= 0.0

                    if (!descriptionError && !amountError) {
                        Log.d("SalvarBanco", dateTime.toString())
                        Log.d("SalvarBanco", description)
                        Log.d("SalvarBanco", value.toString())
                        Log.d("SalvarBanco", isIncome.toString())
                        onSave(description, value, dateTime, isIncome)
                    }

                }
            )
            {
                Text(if (isEditing) "Atualizar" else "Salvar")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}