package com.example.e_book.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.example.e_book.presentation.nav.Routes

@Composable

fun AllBooksScreen(viewModels: MyViewModels = hiltViewModel(), navController: NavController) {
    val state = viewModels.GetAllBooksState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModels.getAllBooks()
    }


    when {
        state.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error.isNotEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = state.error)
            }
        }

        state.data.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(androidx.compose.foundation.layout.WindowInsets.systemBars.asPaddingValues()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
                items(state.data) { book ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Routes.pdfViewScreen(BookUrl = book.bookUrl)
                                )
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        EachBookItem(
                            ImageUrl = book.bookImageUrl, // make sure this field exists
                            bookName = book.bookName,
                            title = book.bookName,       // assuming you have title
                            bookUrl = book.bookUrl,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun EachBookItem(
    ImageUrl: String,
    bookName: String,
    title: String,
    bookUrl: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = ImageUrl,
            contentDescription = null,
            loading = { CircularProgressIndicator() },
            error = { Text(text = "Image failed") },
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = bookName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
