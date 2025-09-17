package com.braiso_22.terracambio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.braiso_22.terracambio.listing.presentation.newListingPanel.NewListingPanel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppAndroid()
        }
    }
}

@Preview
@Composable
fun AppAndroid() {
    MaterialTheme {
        NewListingPanel()
    }
}