package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun PlayerRankingScreen(team: String, rank: Int) {
    GameSkeletonTheme(team = team) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "End of turn",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(80.dp))
            Text(
                text = "This turn is finished: check your team ranking on the main screen!",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp)
            )
            /*
            Text(
                text = "${rank +1}",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 80.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp)
            )
            */
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPlayerRankingScreen() {
    PlayerRankingScreen(team = "Red", rank = 2)
}