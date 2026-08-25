package com.example.financeapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {

    val state = viewModel.state
    var showBottonSheet by remember { mutableStateOf(false) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {showBottonSheet = true}) {
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
            TransactionList(state)
        }
        if(showBottonSheet){
            TransactionBottonSheet(
                onDismiss =  {showBottonSheet = false},
                onSave = { description, value, date, isIncoe ->
                    //Lógica para salvar
                    viewModel.addTransaction(
                        description,
                        value,
                        date,
                        isIncoe)
                    showBottonSheet = false
                }
            )
        }
    }
}
