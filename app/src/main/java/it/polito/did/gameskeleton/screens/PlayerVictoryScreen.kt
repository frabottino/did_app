package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun PlayerVictoryScreen(team : String, rank: Int) {
    GenericScreen(title = "FINAL RANKING"){
        GameSkeletonTheme(team = team) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "GAME OVER",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 10.dp)
                )
                Spacer(Modifier.height(80.dp))
                Text(
                    text = "Check your final team ranking on the main screen!",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewVictoryScreen() {
    PlayerVictoryScreen(team = "Red", rank = 2)
}