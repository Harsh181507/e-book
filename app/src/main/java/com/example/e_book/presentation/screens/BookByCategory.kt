package com.example.e_book.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.example.e_book.presentation.nav.Routes
import com.example.e_book.viewModel.MyViewModels

import androidx.compose.material3.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookByCategory(
    category: String,
    viewModels: MyViewModels = hiltViewModel(),
    navController: NavController
) {
    val state = viewModels.GetAllBooksByCategoryState.collectAsState()

    LaunchedEffect(Unit) {
        viewModels.getAllBooksByCategory(category)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = category.replaceFirstChar { it.uppercaseChar() })
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when {
            state.value.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.error.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error: ${state.value.error}")
                }
            }

            state.value.data.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.value.data) { book ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable {
                                    navController.navigate(Routes.pdfViewScreen(BookUrl = book.bookUrl))
                                },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            EachBookBycat(
                                ImageUrl = book.bookImageUrl,
                                bookName = book.bookName,
                                title = book.bookName,
                                bookUrl = book.bookUrl,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EachBookBycat(
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
            loading = { CircularProgressIndicator(modifier = Modifier.size(24.dp)) },
            error = { Text(text = "Image failed") },
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = bookName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))


    }
}
