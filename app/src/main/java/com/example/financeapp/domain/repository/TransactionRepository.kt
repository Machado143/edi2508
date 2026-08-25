package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.Transaction

interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: Long)
    suspend fun updateTransaction(transaction: Transaction)
}