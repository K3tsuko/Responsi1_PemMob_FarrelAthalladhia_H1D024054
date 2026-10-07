package com.example.openlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.openlibrary.ui.navigation.AppNavigation
import com.example.openlibrary.ui.theme.OpenLibraryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenLibraryAppTheme {
                AppNavigation()
            }
        }
    }
}
