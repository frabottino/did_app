package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import kotlin.reflect.KFunction6


@Composable
fun Cards2Screen(team: String, sendCards: KFunction6<Int, Int, Int?, Int?, Int?, Int?, Unit>, cards: ArrayList<Int>, first: Int, second: Int, i: Int) {

    val vm = GameViewModel.getInstance()
    var check by remember { mutableStateOf(-1) }
    val color1 = if (check == 1) Color.Red else Color.Blue
    val color2 = if (check == 2) Color.Red else Color.Blue
    val color3 = if (check == 3) Color.Red else Color.Blue
    println("mannaggiaaaaa $first, $second, $i")

    GameSkeletonTheme(team = team) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CARDS",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.TopStart),
                colors = ButtonDefaults.buttonColors(backgroundColor = color1),
                onClick = {check = 1}
                ) {
                Text("Car")
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.TopEnd),
                colors = ButtonDefaults.buttonColors(backgroundColor = color2),
                onClick = {check = 2}) {
                Text("Bike")
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterStart),
                colors = ButtonDefaults.buttonColors(backgroundColor = color3),
                onClick = {check = 3}) {
                Text("Public")
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    if(cards[second-1] != i && (cards[second-1] in 82..85 || cards[second-1] == 88))
                        vm.onGoCards3(first, second, cards[second-1], check, i)
                    else
                        sendCards(first, second, check, i, null, null )}) {
                Text("Confirm")
            }
        }
    }
}


@Composable
@Preview
fun Cards2ScreenPreview() {
    //CardsScreen("") {}
}