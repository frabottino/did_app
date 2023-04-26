package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.GameManager
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

//private val vm = GameViewModel() TODO singleton
private val vm = GameViewModel.getInstance()
@Composable
fun MascotteScreen(team: String, onStartMemory: () -> Unit, onStartFlappy: () -> Unit, onStartQuiz: () -> Unit, modifier: Modifier = Modifier) {
    GameSkeletonTheme(team = team) {
        Box(
            modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "supercazzolata del giorno:",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier.height(32.dp))
            when (vm.getTurnFromGM()) {
                1 -> {
                    Button(
                        modifier = modifier.align(Alignment.Center),
                        onClick = { onStartMemory() }) {
                        Text("GO TO MEMORY")
                    }
                }
                2 -> {
                    Button(
                        modifier = modifier.align(Alignment.Center),
                        onClick = { onStartFlappy() }) {
                        Text("GO TO FLAPPY")
                    }
                }
                3 -> {
                    Button(
                        modifier = modifier.align(Alignment.Center),
                        onClick = { onStartQuiz() }) {
                        Text("GO TO QUIZ")
                    }
                }
            }
        }
    }
}