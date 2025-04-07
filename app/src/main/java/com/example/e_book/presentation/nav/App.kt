package com.example.e_book.presentation.nav

import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.presentation.screens.BookByCategory
import com.example.e_book.presentation.screens.TabScreen
import com.example.e_book.presentation.screens.pdfViewScreen

@Composable
fun App(){
    val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        modifier= Modifier
    ){
        composable<Routes.Home>{
            TabScreen(navController)

        }
        composable<Routes.pdfViewScreen>{
            val data=it.toRoute<Routes.pdfViewScreen>()
            pdfViewScreen(BookUrl = data.BookUrl)

        }
        composable<Routes.BookByCategory>{
            val data=it.toRoute<Routes.BookByCategory>()
            BookByCategory(navController=navController,category = data.category)

        }



    }
}