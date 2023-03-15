package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    GenericScreen(title = "Error", modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(1.0f,0.8f, 0.8f))
                .padding(16.dp)
        ) {
            Text(text = message,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreen() {
    GameSkeletonTheme ("Red"){
        ErrorScreen("Unknown MatchID")
    }
}