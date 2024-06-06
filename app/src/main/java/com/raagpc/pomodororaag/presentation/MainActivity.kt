package com.raagpc.pomodororaag.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.raagpc.pomodororaag.data.CountdownService
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroRaagTheme {
                PomodoroRaagApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // stop service
        Log.i("MainActivity", "onDestroy: Stopping service")
        stopService(Intent(this, CountdownService::class.java))
    }
}
