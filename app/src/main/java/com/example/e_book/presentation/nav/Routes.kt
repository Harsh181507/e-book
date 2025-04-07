package com.example.e_book.presentation.nav

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object Home

    @Serializable
    data class BookByCategory(
        val category: String
    )

    @Serializable
    object AllBooks

    @Serializable
    data class pdfViewScreen(
        val BookUrl: String
    )
}