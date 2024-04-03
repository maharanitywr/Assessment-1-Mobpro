package org.d3if3086.mobpro1.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3086.mobpro1.ui.screen.AboutScreen
import org.d3if3086.mobpro1.ui.screen.AddScreen
import org.d3if3086.mobpro1.ui.screen.MainScreen
import org.d3if3086.mobpro1.ui.screen.MainViewModel

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val viewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController, viewModel)
        }
        composable(route = Screen.Add.route) {
            AddScreen(navController, viewModel)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
    }
}