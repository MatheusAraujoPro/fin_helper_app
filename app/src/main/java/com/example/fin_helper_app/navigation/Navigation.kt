package com.example.fin_helper_app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fin_helper_app.navigation.Routes.SUMMARY_SCREEN
import com.example.fin_helper_app.presentation.summary.SummaryScreen
import com.example.fin_helper_app.presentation.summary.SummaryViewModel

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "summary_screen",
    ) {
        composable(SUMMARY_SCREEN) { SummaryScreen(viewModel = hiltViewModel<SummaryViewModel>()) }
    }
}

object Routes {
    const val SUMMARY_SCREEN = "summary_screen"
}