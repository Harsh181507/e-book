package com.example.e_book.presentation.nav

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object Home

    @Serializable
    object BookByCategory

    @Serializable
    object AllBooks

    @Serializable
    data class pdfViewScreen(
        val BookUrl: String
    )
}