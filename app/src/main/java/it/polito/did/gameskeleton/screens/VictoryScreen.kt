package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.gameskeleton.ui.theme.ThemeBlue
import it.polito.did.gameskeleton.ui.theme.ThemeGreen
import it.polito.did.gameskeleton.ui.theme.ThemeRed
import it.polito.did.gameskeleton.ui.theme.ThemeYellow

@Composable
fun VictoryScreen(
    rank: List<Pair<String, Int>>, onEndgame: () -> Unit, modifier: Modifier = Modifier
) {/*GenericScreen(title = "FINAL RANKING"){
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
                onClick = { onEndgame() }) {
                Text("ENDGAME")
            }
        }
    }*/
    val red = rank.find { pair -> pair.first == "Red" }
    val blue = rank.find { pair -> pair.first == "Blue" }
    val green = rank.find { pair -> pair.first == "Green" }
    val yellow = rank.find { pair -> pair.first == "Yellow" }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "FINAL RANKING",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    fontSize = 40.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TeamRank(
                    modifier = Modifier.weight(1f),
                    ThemeRed,
                    "Red",
                    red!!.second,
                    rank.indexOf(red) + 1
                )
                TeamRank(
                    modifier = Modifier.weight(1f),
                    ThemeBlue,
                    "Blue",
                    blue!!.second,
                    rank.indexOf(blue) + 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TeamRank(
                    modifier = Modifier.weight(1f),
                    ThemeGreen,
                    "Green",
                    green!!.second,
                    rank.indexOf(green) + 1
                )
                TeamRank(
                    modifier = Modifier.weight(1f),
                    ThemeYellow,
                    "Yellow",
                    yellow!!.second,
                    rank.indexOf(yellow) + 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(text = "FINISH GAME",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = { onEndgame() })
            }
        }

    }
}

@Composable
fun TeamRank(
    modifier: Modifier = Modifier,
    background: Color,
    team: String,
    points: Int,
    rank: Int,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .border(5.dp, background)
    ) {
        Text(
            text = "Team $team",
            textAlign = TextAlign.Center,
            color = background,
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Text(
            text = "$rank",
            textAlign = TextAlign.Center,
            color = background,
            fontSize = 80.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = "Points: $points",
            textAlign = TextAlign.Center,
            color = background,
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun VictoryScreen() {
}