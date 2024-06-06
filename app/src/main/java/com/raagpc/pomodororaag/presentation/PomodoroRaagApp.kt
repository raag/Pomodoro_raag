package com.raagpc.pomodororaag.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raagpc.pomodororaag.presentation.home.HomeScreen
import com.raagpc.pomodororaag.presentation.home.HomeScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PomodoroRaagApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state by viewModel.state

            val context = LocalContext.current

            LaunchedEffect(Unit) {
                viewModel.initBroadcast(context)
            }
            
               DisposableEffect(Unit) {
                    onDispose {
                        Log.i("PomodoroRaagApp", "onDispose: Unregistering broadcast")
                        viewModel.unregisterBroadcast(context)
                    }
                }

            HomeScreen(
                state = state,
                onToggleTimer = { viewModel.toggleTimer(context) },
                onRestartTimer = { viewModel.restartTimer(context) }
            )
        }
    }
}