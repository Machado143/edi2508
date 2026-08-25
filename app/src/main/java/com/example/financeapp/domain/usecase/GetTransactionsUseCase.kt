package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.repository.TransactionRepository

class GetTransactionsUseCase (private val repository: TransactionRepository) {
    suspend operator fun invoke(): List<Transaction>{
        return repository.getTransactions()
    }
}