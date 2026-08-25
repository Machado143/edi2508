package com.example.financeapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.model.TransactionType
import com.example.financeapp.domain.repository.TransactionRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class FakeTransactionRepository : TransactionRepository {


    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    private val transactions = mutableListOf(
        Transaction(
            1,
            "Salário",
            2500.00,
            LocalDateTime.parse(
                "01/07/2026 10:00", formatter
            ),
            TransactionType.INCOME
        ),
        Transaction(
            2,
            "Supermercado",
            25.01,
            LocalDateTime.parse("02/07/2026 21:32"  ,
                formatter),
            TransactionType.EXPENSE
        )
    )

    override suspend fun getTransactions(): List<Transaction> {
        return transactions.toList()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override suspend fun deleteTransaction(id: Long) {
        transactions.removeIf { it.id == id }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        val index = transactions.indexOfFirst { it.id == transaction.id }
        if (index != -1) {
            transactions[index] = transaction
        }
    }
}