package com.example.openlibrary.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.openlibrary.ui.BookViewModel
import com.example.openlibrary.ui.screen.DetailScreen
import com.example.openlibrary.ui.screen.HomeScreen

private const val HOME = "home"
private const val DETAIL = "detail/{bookKey}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = HOME) {
        composable(HOME) {
            HomeScreen(
                viewModel = viewModel,
                onBookClick = { book ->
                    // key contains slashes (/works/OL123W), so encode it
                    val key = Uri.encode(book.key.orEmpty())
                    navController.navigate("detail/$key")
                }
            )
        }
        composable(
            route = DETAIL,
            arguments = listOf(navArgument("bookKey") { type = NavType.StringType })
        ) { backStackEntry ->
            val key = backStackEntry.arguments?.getString("bookKey").orEmpty()
            DetailScreen(
                book = viewModel.getBookByKey(key),
                onBack = { navController.popBackStack() }
            )
        }
    }
}
