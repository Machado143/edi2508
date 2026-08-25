package com.example.financeapp.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.model.TransactionType
import com.example.financeapp.domain.usecase.DeleteTransactionUseCase
import com.example.financeapp.domain.usecase.GetTransactionsUseCase
import com.example.financeapp.domain.usecase.InsertTransactionsUseCase
import com.example.financeapp.domain.usecase.UpdateTransactionUseCase
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {
    var state by mutableStateOf(HomeUIState())
        private set

    init {
        loadtransactions()
    }

    private fun loadtransactions() {
        viewModelScope.launch {
            val data = getTransactionsUseCase()
            state = state.copy(transactions = data)
        }
    }

    fun addTransaction(
        description: String,
        amount: Double,
        date: LocalDateTime,
        type: Boolean
    ) {
        viewModelScope.launch {
            val transaction = Transaction(
                id = System.currentTimeMillis(),
                description = description,
                amount = amount,
                date = date,
                type = if (type) TransactionType.INCOME else TransactionType.EXPENSE
            )
            insertTransactionsUseCase(transaction)
            loadtransactions()
        }
    }

    fun updateTransaction(
        id: Long,
        description: String,
        amount: Double,
        date: LocalDateTime,
        type: Boolean
    ) {
        viewModelScope.launch {
            val transaction = Transaction(
                id = id,
                description = description,
                amount = amount,
                date = date,
                type = if (type) TransactionType.INCOME else TransactionType.EXPENSE
            )
            updateTransactionUseCase(transaction)
            loadtransactions()
        }
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            deleteTransactionUseCase(id)
            loadtransactions()
        }
    }

    fun startEditing(transaction: Transaction) {
        state = state.copy(editingTransaction = transaction)
    }

    fun stopEditing() {
        state = state.copy(editingTransaction = null)
    }
}