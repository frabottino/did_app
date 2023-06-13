package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MiniRankingScreen(rank: List<Pair<String, Int>>, onStartNewPhase: () -> Unit, modifier: Modifier = Modifier) {
    GenericScreen(title = ""){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.toString(),
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Button(
                modifier = modifier.align(Alignment.Center),
                onClick = { onStartNewPhase() }) {
                Text("START NEXT PHASE")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMiniRankingScreen() {
}