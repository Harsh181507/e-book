package com.example.e_book.presentation.screens

import android.R.attr.category
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_book.presentation.nav.Routes
import com.example.e_book.viewModel.MyViewModels
import java.util.Locale

@Composable
fun BookByCategory(
    category: String,
    viewModels: MyViewModels= hiltViewModel(),
    navController: NavController
){
    val state = viewModels.GetAllBooksByCategoryState.collectAsState()
    LaunchedEffect(key1=Unit) {
        viewModels.getAllBooksByCategory(category)
    }
    when {
        state.value.isLoading -> {
            CircularProgressIndicator()
        }

        state.value.error.isNotEmpty() -> {
            Text(text = state.value.error)
            Text(text="error")
        }

        state.value.data.isNotEmpty() -> {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.value.data) {
                        Text(text = it.bookName, modifier = Modifier.fillMaxWidth().clickable{
                            navController.navigate(Routes.pdfViewScreen(BookUrl = it.bookUrl))
                        })
                    }
                }
            }
        }
    }

}