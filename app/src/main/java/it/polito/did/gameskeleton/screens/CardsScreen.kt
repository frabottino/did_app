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
fun CardsScreen(team: String, sendCards: KFunction6<Int, Int, Int?, Int?, Int?, Int?, Unit>, cards: ArrayList<Int>) {

    val vm = GameViewModel.getInstance()
    var first by remember { mutableStateOf(-1) }
    var second by remember { mutableStateOf(-1) }
    val color1 = if (first == 1 || second == 1) Color.Red else Color.Blue
    val color2 = if (first == 2 || second == 2) Color.Red else Color.Blue
    val color3 = if (first == 3 || second == 3) Color.Red else Color.Blue
    val color4 = if (first == 4 || second == 4) Color.Red else Color.Blue
    val color5 = if (first == 5 || second == 5) Color.Red else Color.Blue


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
                onClick = {if(first == -1) first = 1
                            else if (second == -1) second = 1
                            else if (first != 1 && second != 1){
                                first = second
                                second = 1 }
                            }) {
                Text(cards[0].toString())
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.TopEnd),
                colors = ButtonDefaults.buttonColors(backgroundColor = color2),
                onClick = {if(first == -1) first = 2
                            else if (second == -1) second = 2
                            else if (first != 2 && second != 2){
                                first = second
                                second = 2}}) {
                Text(cards[1].toString())
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterStart),
                colors = ButtonDefaults.buttonColors(backgroundColor = color3),
                onClick = {if(first == -1) first = 3
                            else if (second == -1) second = 3
                            else if (first != 3 && second != 3){
                                first = second
                                second = 3}}) {
                Text(cards[2].toString())
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(backgroundColor = color4),
                onClick = {if(first == -1) first = 4
                            else if (second == -1) second = 4
                            else if (first != 4 && second != 4){
                                first = second
                                second = 4}}) {
                Text(cards[3].toString())
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterEnd),
                colors = ButtonDefaults.buttonColors(backgroundColor = color5),
                onClick = {if(first == -1) first = 5
                            else if (second == -1) second = 5
                            else if (first != 5 && second != 5){
                                first = second
                                second = 5}}) {
                Text(cards[4].toString())
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {println("first second $first $second")
                    if(cards[first-1] in 82..85  || cards[first-1] == 88)
                        vm.onGoCards2(first, second, cards[first-1])
                    else if(cards[second-1] in 82..85 || cards[second-1] == 88)
                        vm.onGoCards2(second, first, cards[second-1])
                    else
                        sendCards(first, second, null, null, null, null)}) {
                Text("Confirm")
            }
        }
    }
}


@Composable
@Preview
fun CardsScreenPreview() {
    //CardsScreen("") {}
}