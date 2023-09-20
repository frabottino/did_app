package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import kotlin.reflect.KFunction6


@Composable
fun Cards3Screen(team: String, sendCards: KFunction6<Int, Int, Int?, Int?, Int?, Int?, Unit>, cards: ArrayList<Int>, first: Int, second: Int, i: Int, check1: Int, x: Int, teamNames: List<String>) {

    val vm = GameViewModel.getInstance()
    var check by remember { mutableStateOf(-1) }
    var text by remember { mutableStateOf("") }
    var opt = 0

    when (i) {
        82 -> {
            text = "Choose a team from which to remove house points:"
        }

        83 -> {
            text = "Choose a team from which to remove power plant points:"
        }

        84 -> {
            text = "Choose a team from which to remove energy points:"
        }

        85 -> {
            text = "Choose a team from which to remove coins:"
        }

        88 -> {
            text = "Choose a bonus transportation card:"; opt = 1
        }
    }

    //val teams = teamNames.filter { it != team }
    val teams = teamNames.toMutableList()
    teams.remove(team)
    val w = LocalConfiguration.current.screenWidthDp.dp


    GameSkeletonTheme(team = team) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "CARDS",
//                fontSize = MaterialTheme.typography.h3.fontSize,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.TopStart),
//                colors = ButtonDefaults.buttonColors(backgroundColor = color1),
//                onClick = {check = 1}
//            ) {
//                Text("Car")
//            }
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.TopEnd),
//                colors = ButtonDefaults.buttonColors(backgroundColor = color2),
//                onClick = {check = 2}) {
//                Text("Bike")
//            }
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.CenterStart),
//                colors = ButtonDefaults.buttonColors(backgroundColor = color3),
//                onClick = {check = 3}) {
//                Text("Public")
//            }
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.BottomCenter),
//                onClick = {
//                        sendCards(first, second, check1, x, check, i )}) {
//                Text("Confirm")
//            }
//        }
//    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            Arrangement.SpaceAround
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(32.dp))


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (j in 0 until 3) {

                    Box(modifier = Modifier.size(height = 220.dp, width = (w / 3 - 10.dp))) {
                        Button(
                            modifier = Modifier.align(Alignment.Center),
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (check == j + 1) Color.Green else if (opt == 0 ) Color.DarkGray else Color.LightGray),
                            onClick = {
                                check = j + 1
                            }) {
                            if (opt == 1) {
                                Image(
                                    painter = painterResource(
                                        id = vm.getCardImage(91 + j).image
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(teams[j], Modifier.fillMaxSize().align(Alignment.CenterVertically).padding(10.dp))
                            }
                        }
                    }

                }

            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = check != -1,
                onClick = {
                    sendCards(first, second, check1, x, check, i )
                }) {
                Text("Confirm")
            }
            Spacer(Modifier.height(32.dp))

        }

        Spacer(Modifier.height(80.dp))

    }
}


@Composable
@Preview
fun Cards3ScreenPreview() {
    //CardsScreen("") {}
}