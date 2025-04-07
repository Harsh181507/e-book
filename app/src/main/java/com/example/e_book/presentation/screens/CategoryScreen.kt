package com.example.e_book.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.example.e_book.viewModel.MyViewModels
import com.example.e_book.R

@Composable
fun CategoryScreen(
    viewModel: MyViewModels = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.GetAllCategoriesState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategories()

    }

    val context = LocalContext.current
    when {
        state.value.isLoading -> {
            CircularProgressIndicator()
        }

        state.value.error.isNotEmpty() -> {
            Text(text = state.value.error.toString())
            Toast.makeText(context, "${state.value.error}", Toast.LENGTH_SHORT).show()
        }

        state.value.data.isNotEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),

                ) {
                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.value.data) {
                        EachCategoryItem(
                            imageUrl = it.BookImage,
                            CategoryName = it.CategoryName,
                            navController = navController
                        )

                    }
                }
            }
        }

    }
}

@Composable
fun EachCategoryItem(
    imageUrl: String,
    CategoryName: String = "Category Name",
    navController: NavController
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier.size(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = "",
                error = { Text(text = "Error To Load Image") }
            )





            Spacer(modifier = Modifier.height(8.dp))
            Text(text = CategoryName, fontSize = 16.sp, fontWeight = FontWeight.Bold)


        }
    }
}

