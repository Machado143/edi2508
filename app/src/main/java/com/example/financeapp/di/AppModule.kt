package com.example.financeapp.di

import com.example.financeapp.data.repository.FakeTransactionRepository
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.domain.usecase.GetTransactionsUseCase
import com.example.financeapp.domain.usecase.InsertTransactionsUseCase
import com.example.financeapp.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //repositório
    single<TransactionRepository>{
        FakeTransactionRepository()
    }

    //UseCase
    factory {
        GetTransactionsUseCase(get ())
    }

    factory {
        InsertTransactionsUseCase(get())
    }

    //ViewModel
    viewModel {
        HomeViewModel(get(), get())
    }
}