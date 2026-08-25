package com.example.financeapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.domain.model.Transaction
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {

    val state = viewModel.state
    var showBottonSheet by remember { mutableStateOf(false) }
    var transactionToDelete by remember { mutableStateOf<Transaction?>(null) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.stopEditing()
            showBottonSheet = true
        }) {
            Text("+")
        }
    }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            SummarySection(state)
            Spacer(modifier = Modifier.height(16.dp))
            TransactionList(
                state = state,
                onEdit = { transaction ->
                    viewModel.startEditing(transaction)
                    showBottonSheet = true
                },
                onDelete = { transaction ->
                    transactionToDelete = transaction
                }
            )
        }
        if (showBottonSheet) {
            TransactionBottonSheet(
                transactionToEdit = state.editingTransaction,
                onDismiss = {
                    showBottonSheet = false
                    viewModel.stopEditing()
                },
                onSave = { description, value, date, isIncome ->
                    val editing = state.editingTransaction
                    if (editing != null) {
                        viewModel.updateTransaction(
                            editing.id,
                            description,
                            value,
                            date,
                            isIncome
                        )
                    } else {
                        viewModel.addTransaction(
                            description,
                            value,
                            date,
                            isIncome
                        )
                    }
                    showBottonSheet = false
                    viewModel.stopEditing()
                }
            )
        }

        transactionToDelete?.let { transaction ->
            AlertDialog(
                onDismissRequest = { transactionToDelete = null },
                title = { Text("Apagar transação") },
                text = { Text("Deseja realmente apagar \"${transaction.description}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteTransaction(transaction.id)
                        transactionToDelete = null
                    }) {
                        Text("Apagar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { transactionToDelete = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
