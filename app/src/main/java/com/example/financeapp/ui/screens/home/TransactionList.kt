package com.example.financeapp.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.financeapp.ui.components.TransactionCard

@Composable
fun TransactionList(state: HomeUIState) {
    LazyColumn {
        items(state.transactions){transaction ->
            TransactionCard(transaction)
        }
    }
}