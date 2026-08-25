package com.example.financeapp.domain.model

import java.time.LocalDateTime

data class Transaction (
    val id: Long = 0,
    val description: String,
    val amount: Double,
    val date: LocalDateTime,
    val type: TransactionType
)

enum class TransactionType {
    INCOME,
    EXPENSE
}