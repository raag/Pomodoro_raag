package com.raagpc.pomodororaag.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.raagpc.pomodororaag.R
import com.raagpc.pomodororaag.data.CountdownService
import com.raagpc.pomodororaag.theme.PomodoroRaagTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.i("MainActivity", "requestNotificationPermission: Permission granted")
            } else {
                Log.i("MainActivity", "requestNotificationPermission: Permission denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            PomodoroRaagTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    CheckNotificationPermission()
                }
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS

        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("MainActivity", "CheckNotificationPermission: Permission granted")
            }

            shouldShowRequestPermissionRationale(permission) -> {
                AlertDialog(
                    onDismissRequest = { },
                    text = { Text(text = getString(R.string.notification_permission_required)) },
                    confirmButton = {
                        TextButton(onClick = {
                            val uri: Uri = Uri.fromParts("package", "com.raagpc.pomodororaag", null)
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                data = uri
                                startActivity(this)
                            }
                        }) { Text(text = getString(R.string.go_to_settings)) }
                    },
                )
            }

            else -> {
                requestNotificationPermission.launch(permission)
            }
        }
    }


}
