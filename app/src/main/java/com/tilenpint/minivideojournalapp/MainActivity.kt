package com.tilenpint.minivideojournalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tilenpint.minivideojournalapp.ui.VideoNavGraph
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MiniVideoJournalAppTheme {
                VideoNavGraph()
            }
        }
    }
}
