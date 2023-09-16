package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import kotlin.reflect.KFunction6


@Composable
fun Cards2Screen(
    team: String,
    sendCards: KFunction6<Int, Int, Int?, Int?, Int?, Int?, Unit>,
    cards: ArrayList<Int>,
    first: Int,
    second: Int,
    i: Int,
    teamNames: List<String>
) {

    val vm = GameViewModel.getInstance()
    var check by remember { mutableStateOf(-1) }
    println("mannaggiaaaaa $first, $second, $i")

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

    val teams = teamNames.filter { it != team }
    val w = LocalConfiguration.current.screenWidthDp.dp

    GameSkeletonTheme(team = team) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = text,
//                fontSize = MaterialTheme.typography.h3.fontSize,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.TopStart),
//                colors = ButtonDefaults.buttonColors(backgroundColor = color1),
//                onClick = { check = 1 }
//            ) {
//                Text(if(opt == 0) teams[0] else "Car")
//            }
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.TopEnd),
//                colors = ButtonDefaults.buttonColors(backgroundColor = color2),
//                onClick = { check = 2 }) {
//                Text(if(opt == 0) teams[1] else "Bike")
//            }
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.CenterStart),
//                colors = ButtonDefaults.buttonColors(backgroundColor = color3),
//                onClick = { check = 3 }) {
//                Text(if(opt == 0) teams[2] else "Public")
//            }
//            Spacer(Modifier.height(32.dp))
//            Button(
//                modifier = Modifier.align(Alignment.BottomCenter),
//                onClick = {
//                    if (cards[second - 1] != i && second != 6 && (cards[second - 1] in 82..85 || cards[second - 1] == 88))
//                        vm.onGoCards3(first, second, cards[second - 1], check, i)
//                    else
//                        sendCards(first, second, check, i, null, null)
//                }) {
//                Text("Confirm")
//            }
//        }

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
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (check == j + 1) Color.Green else Color.LightGray),
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
                    if (second != -1 && (cards[second - 1] in 82..85 || cards[second - 1] == 88))
                        vm.onGoCards3(first, second, cards[second - 1], check, i)
                    else
                        sendCards(first, second, check, i, null, null)
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
fun Cards2ScreenPreview() {
    //CardsScreen("") {}
}