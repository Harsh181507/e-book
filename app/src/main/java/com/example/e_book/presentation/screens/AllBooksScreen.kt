package com.example.e_book.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_book.viewModel.MyViewModels
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.collectAsState
import com.example.e_book.presentation.nav.Routes

@Composable
fun AllBooksScreen(viewModels: MyViewModels = hiltViewModel(), navController: NavController) {
    val state = viewModels.GetAllBooksState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModels.getAllBooks()
    }

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error.isNotEmpty() -> {
            Text(text = state.error)
        }

        state.data.isNotEmpty() -> {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.data) {
                        Text(text = it.bookName, modifier = Modifier.fillMaxWidth().clickable{
                            navController.navigate(Routes.pdfViewScreen(BookUrl = it.bookUrl))
                        })
                    }
                }
            }
        }
    }
}
@Composable
fun EachBookItem(ImageUrl: String,bookName: String,title: String,bookUrl:String, navController: NavController)
 {

}

