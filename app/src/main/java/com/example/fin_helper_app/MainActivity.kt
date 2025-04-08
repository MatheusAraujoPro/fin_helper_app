package com.example.fin_helper_app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.fin_helper_app.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.graphics.Color

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            SetStatusBarColor()
            val navHost = rememberNavController()
            Navigation(navHostController = navHost)
        }
    }

    @Composable
    private fun SetStatusBarColor() {
        val view = LocalView.current
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor =
                Color.Black.toArgb()// change color status bar here
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                false
        }
    }
}