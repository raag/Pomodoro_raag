package com.raagpc.pomodororaag.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raagpc.pomodororaag.presentation.home.HomeScreen
import com.raagpc.pomodororaag.presentation.home.HomeScreenViewModel

@Composable
fun PomodoroRaagApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state by viewModel.state

            val context = LocalContext.current

            LaunchedEffect(Unit) {
                Log.i("PomodoroRaagApp", "Setting context for view model")
                viewModel.setContext(context)
            }
            

            HomeScreen(
                state = state,
                onToggleTimer = { viewModel.toggleTimer() },
                onRestartTimer = { viewModel.restartTimer() }
            )
        }
    }
}