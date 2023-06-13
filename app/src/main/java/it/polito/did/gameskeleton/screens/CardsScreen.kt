package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    val openDialog = remember { mutableStateOf(0)  }

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
                onClick = {if(first == -1 && second != 1) first = 1
                            else if (second == -1 && first != 1) second = 1
                            else if (first != 1 && second != 1){
                                first = second
                                second = 1 }
                    else if (first == 1) first = -1
                    else if (second == 1) second = -1
                            }) {
                Box() {
                    Image(painter = painterResource(id = vm.getCardImage(cards[0]).image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.TopEnd),
                colors = ButtonDefaults.buttonColors(backgroundColor = color2),
                onClick = {if(first == -1 && second != 2) first = 2
                            else if (second == -1 && first != 2) second = 2
                            else if (first != 2 && second != 2){
                                first = second
                                second = 2}
                else if (first == 2) first = -1
                else if (second == 2) second = -1}) {
                Box() {
                    Image(
                        painter = painterResource(id = vm.getCardImage(cards[1]).image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterStart),
                colors = ButtonDefaults.buttonColors(backgroundColor = color3),
                onClick = {if(first == -1 && second != 3) first = 3
                            else if (second == -1 && first != 3) second = 3
                            else if (first != 3 && second != 3){
                                first = second
                                second = 3}
                else if (first == 3) first = -1
                else if (second == 3) second = -1}) {
                Box() {
                    Image(
                        painter = painterResource(id = vm.getCardImage(cards[2]).image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(backgroundColor = color4),
                onClick = {if(first == -1 && second != 4) first = 4
                            else if (second == -1 && first != 4) second = 4
                            else if (first != 4 && second != 4){
                                first = second
                                second = 4}
                else if (first == 4) first = -1
                else if (second == 4) second = -1}) {
                Box() {
                    Image(
                        painter = painterResource(id = vm.getCardImage(cards[3]).image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterEnd),
                colors = ButtonDefaults.buttonColors(backgroundColor = color5),
                onClick = {if(first == -1 && second != 5) first = 5
                            else if (second == -1 && first != 5) second = 5
                            else if (first != 5 && second != 5){
                                first = second
                                second = 5}
                else if (first == 5) first = -1
                else if (second == 5) second = -1}) {
                Box() {
                    Image(
                        painter = painterResource(id = vm.getCardImage(cards[4]).image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    try {
                        var eTemp = 0
                        var mTemp = 0
                        println("first second $first $second")
                        if(first == -1 || second == -1)
                            openDialog.value = 1
                        if(first != -1) {
                            if (cards[first - 1] in 21..25) eTemp++
                            if (cards[first - 1] in 26..30) eTemp += 2
                            if (cards[first - 1] in 36..40) mTemp++
                            if (cards[first - 1] in 41..45) mTemp += 2
                        }
                        if(second != -1) {
                            if (cards[second - 1] in 21..25) eTemp++
                            if (cards[second - 1] in 26..30) eTemp += 2
                            if (cards[second - 1] in 36..40) mTemp++
                            if (cards[second - 1] in 41..45) mTemp += 2
                        }
                        if(eTemp != 0)
                            if(!vm.onCheckCards(eTemp, "Energy"))
                                openDialog.value = 2
                        if(mTemp != 0)
                            if(!vm.onCheckCards(mTemp, "Money"))
                                openDialog.value = 2
                        if(openDialog.value == 0) continueCards(first, second, vm, cards, sendCards)
                    } catch (e: Exception) {
                        openDialog.value = 5
                    }
                }) {
                Text("Confirm")
            }
        }
        if (openDialog.value == 1) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = 0
                },
                title = {
                    Text(text = "Conferma")
                },
                text = {
                    if(first == -1 && second == -1)
                        Text("Sei sicuro di non pescare nessuna carta e ricevere due monete?")
                    else if(first == -1)
                        Text("Sei sicuro di pescare solo una carta e ricevere una moneta?")
                    else if(second == -1)
                        Text("Sei sicuro di pescare solo una carta e ricevere una moneta?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = 0
                            if(first == -1 && second == -1)
                                continueCards(6, 6, vm, cards, sendCards)
                            else if(first == -1)
                                continueCards(6, second, vm, cards, sendCards)
                            else if(second == -1)
                                continueCards(6, second, vm, cards, sendCards)
                        }) {
                        Text("Conferma")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = 0
                        }) {
                        Text("Annulla")
                    }
                },
                modifier = Modifier.border(width = 2.dp, color = MaterialTheme.colors.primary)
            )
        }
        if (openDialog.value == 2) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = 0
                },
                title = {
                    Text(text = "Errore!")
                },
                text = {
                    Text("Non disponi delle risorse necessarie per ottenere una delle carte!")
                },
                confirmButton = {},
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = 0
                        }) {
                        Text("OK")
                    }
                },
                modifier = Modifier.border(width = 2.dp, color = MaterialTheme.colors.primary)
            )
        }
    }
}

fun continueCards (first: Int, second: Int, vm: GameViewModel, cards: ArrayList<Int>, sendCards: KFunction6<Int, Int, Int?, Int?, Int?, Int?, Unit>){
    if(first != 6 && (cards[first-1] in 82..85  || cards[first-1] == 88))
        vm.onGoCards2(first, second, cards[first-1])
    else if(second!=6 && (cards[second-1] in 82..85 || cards[second-1] == 88))
        vm.onGoCards2(second, first, cards[second-1])
    else
        sendCards(first, second, null, null, null, null)
}


@Composable
@Preview
fun CardsScreenPreview() {
    //CardsScreen("") {}
}