package com.example.e_book.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun pdfViewScreen(
    BookUrl:String,
){

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(BookUrl),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = pdfState.error) {
        pdfState.error?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }


    
}
