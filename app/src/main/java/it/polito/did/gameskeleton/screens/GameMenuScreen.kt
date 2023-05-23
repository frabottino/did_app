package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun GameMenuScreen(team: String, modifier: Modifier = Modifier) {
    GenericScreen(title = "(${team})"){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MENU",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGaemMenuScreen() {
    GameSkeletonTheme ("Red"){
        PlayerScreen("Teams A")
    }
}