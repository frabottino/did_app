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
fun MiniRankingScreen(
    rank: List<Pair<String, Int>>,
    onStartNewPhase: () -> Unit,
    modifier: Modifier = Modifier
) {
    /*GenericScreen(title = ""){
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
                    .weight(1f)
            ) {
                TeamPoints(
                    modifier = Modifier.weight(1f),
                    ThemeRed,
                    "Red",
                    rank.find { pair -> pair.first == "Red" }!!.second,
                    (10 - 3*rank.indexOf(red))
                )
                TeamPoints(
                    modifier = Modifier.weight(1f),
                    ThemeBlue,
                    "Blue",
                    rank.find { pair -> pair.first == "Blue" }!!.second,
                    (10 - 3*rank.indexOf(blue))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TeamPoints(
                    modifier = Modifier.weight(1f),
                    ThemeGreen,
                    "Green",
                    rank.find { pair -> pair.first == "Green" }!!.second,
                    (10 - 3*rank.indexOf(green))
                )
                TeamPoints(
                    modifier = Modifier.weight(1f),
                    ThemeYellow,
                    "Yellow",
                    rank.find { pair -> pair.first == "Yellow" }!!.second,
                    (10 - 3*rank.indexOf(yellow))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(
                    text = "START NEXT PHASE",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = { onStartNewPhase() })
            }
        }

    }
}

@Composable
fun TeamPoints(
    modifier: Modifier = Modifier,
    background: Color,
    team: String,
    points: Int,
    vp: Int
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
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Points: $vp",
            textAlign = TextAlign.Center,
            color = background,
            fontSize = 80.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
        Text(
            text = "Phase Points: $points",
            textAlign = TextAlign.Center,
            color = background,
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewMiniRankingScreen() {
}