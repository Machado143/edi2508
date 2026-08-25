package com.example.financeapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.financeapp.utils.currencyBr

@Composable
fun SummarySection(state: HomeUIState) {
    //total de entradas
    val totalIncome = state.transactions
        .filter { it.type.name == "INCOME" }
        .sumOf { it.amount }

    //total de saídas
    val totalExpense = state.transactions
        .filter { it.type.name == "EXPENSE" }
        .sumOf { it.amount }

    //Saldo
    val balance = totalIncome - totalExpense

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        )
        {
            Text("Saldo", color = Color.White)
            Text(
                text = currencyBr(balance),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

                Text("Entradas: ${currencyBr(totalIncome)}", color = Color.Green)
                Text("Saídas: ${currencyBr(totalExpense)}", color = Color(0xFFAB4038))

        }
    }
}