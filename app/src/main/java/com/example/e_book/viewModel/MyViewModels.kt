package com.example.e_book.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_book.common.ResultState
import com.example.e_book.data.BookCategoryModels
import com.example.e_book.data.BookModels
import com.example.e_book.repo.repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModels @Inject constructor(val repo: repo) : ViewModel() {
    private val _GetAllBooksState: MutableStateFlow<GetAllBooksState> = MutableStateFlow(GetAllBooksState())
    val GetAllBooksState = _GetAllBooksState.asStateFlow()

    private val _GetAllCategoriesState: MutableStateFlow<GetAllCategoriesState> = MutableStateFlow(GetAllCategoriesState())
    val GetAllCategoriesState = _GetAllCategoriesState.asStateFlow()

    private val _GetAllBooksByCategoryState: MutableStateFlow<GetAllBooksByCategoryState> = MutableStateFlow(GetAllBooksByCategoryState())
    val GetAllBooksByCategoryState = _GetAllBooksByCategoryState.asStateFlow()

    fun getAllBooksByCategory(category:String) {
        viewModelScope.launch (Dispatchers.IO) {
           repo.getAllBooksByCategory(category).collect{
               when (it) {
                   is ResultState.Success -> {
                       _GetAllBooksByCategoryState.value = GetAllBooksByCategoryState(data = it.data)
                   }
                   is ResultState.Loading -> {
                       _GetAllBooksByCategoryState.value = GetAllBooksByCategoryState(isLoading = true)
                   }
                   is ResultState.Error -> {
                       _GetAllBooksByCategoryState.value = GetAllBooksByCategoryState(error = it.message)
                   }
               }
           }
        }
    }
    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCategories().collect {
                when (it) {
                    is ResultState.Success -> {
                        _GetAllCategoriesState.value = GetAllCategoriesState(data = it.data)
                    }

                    is ResultState.Loading -> {
                        _GetAllCategoriesState.value = GetAllCategoriesState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _GetAllCategoriesState.value = GetAllCategoriesState(error = it.message)

                    }
                }
            }
        }

    }

    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllBooks().collect {
                when (it) {
                    is ResultState.Success -> {
                        _GetAllBooksState.value = GetAllBooksState(data = it.data)
                    }

                    is ResultState.Loading -> {
                        _GetAllBooksState.value = GetAllBooksState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _GetAllBooksState.value = GetAllBooksState(error = it.message)
                    }
                }
            }
        }
    }
}


data class GetAllBooksState(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: String = ""

)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val data: List<BookCategoryModels> = emptyList(),
    val error: String = ""
)
data class GetAllBooksByCategoryState(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: String = ""
)
