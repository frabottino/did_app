package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.did_app.R
import it.polito.did.gameskeleton.ui.theme.ThemeBlue
import it.polito.did.gameskeleton.ui.theme.ThemeGreen
import it.polito.did.gameskeleton.ui.theme.ThemeRed
import it.polito.did.gameskeleton.ui.theme.ThemeYellow
import kotlin.reflect.KFunction1

@Composable
fun GameMenuScreen(
    decks: ArrayList<ArrayList<Int>>,
    players: Collection<List<String>>,
    capId: Int,
    currentTeamName: String,
) {

//    GenericScreen(title = ""){
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = decks.toString(),
//                fontSize = MaterialTheme.typography.h3.fontSize,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
//            Text(
//                modifier = Modifier.align(Alignment.Center),
//                text = decks.toString(),
//                fontSize = MaterialTheme.typography.h3.fontSize,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
//            /*
//            ExposedDropdownMenu(
//                items = players.random(),
//                selected = players.random()[capId]
//            )
//             */
//
//            //TODO non dimentichiamoci di rimetterlo
//
//            var images = getDecksImage(decks)
//
//            for (i in 0 until images.size) {
//                Column(){
//                for (j in 0 until 3) {
//
//                        Image(
//                            painter = painterResource(
//                                id = images[i][j]
//                            ),
//                            contentDescription = null,
//                            contentScale = ContentScale.Fit,
//                            modifier = Modifier.size(100.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }

    var images = getDecksImage(decks)

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
                // First Column
                ColumnItem(modifier = Modifier.weight(1f), images[0], ThemeRed, "Red", currentTeamName == "Red")

                // Second Column
                ColumnItem(modifier = Modifier.weight(1f), images[1], ThemeBlue, "Blue", currentTeamName == "Blue")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Third Column
                ColumnItem(modifier = Modifier.weight(1f), images[2], ThemeGreen, "Green", currentTeamName == "Green")

                // Fourth Column
                ColumnItem(modifier = Modifier.weight(1f), images[3], ThemeYellow, "Yellow", currentTeamName == "Yellow")
            }
        }

    }
}

@Composable
fun ColumnItem(
    modifier: Modifier = Modifier,
    images: ArrayList<Int>,
    background: Color,
    team: String,
    active: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .border(5.dp, if (active) background else Color.White)
    ) {
        Text(
            text = "Team $team",
            textAlign = TextAlign.Center,
            color = background,
            fontSize = 40.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        for (j in 0 until 3) {

            Image(
                painter = painterResource(
                    id = images[j]
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(1f)
                    .align(Alignment.Center)
            )
        }
    }

}


fun getDecksImage(decks: ArrayList<ArrayList<Int>>): ArrayList<ArrayList<Int>> {
    var img1 = arrayListOf(0, 0, 0)
    var img2 = arrayListOf(0, 0, 0)
    var img3 = arrayListOf(0, 0, 0)
    var img4 = arrayListOf(0, 0, 0)
    var totalImg = arrayListOf(img1, img2, img3, img4)
    for (i in 0 until decks.size) {
        var transport =
            decks[i][0] * decks[i][0] + decks[i][1] * decks[i][1] + decks[i][2] * decks[i][2]
        var power = decks[i][3]
        var house = decks[i][4]
        if (transport <= 4) totalImg[i][0] = R.drawable.transport1
        else if (transport in 5..12) totalImg[i][0] = R.drawable.transport2
        else if (transport in 13..20) totalImg[i][0] = R.drawable.transport3
        else totalImg[i][0] = R.drawable.transport4

        if (power <= 2) totalImg[i][1] = R.drawable.city1
        else if (power in 3..10) totalImg[i][1] = R.drawable.city2
        else if (power in 11..22) totalImg[i][1] = R.drawable.city3
        else totalImg[i][1] = R.drawable.city4

        if (house <= 2) totalImg[i][2] = R.drawable.house1
        else if (house in 3..8) totalImg[i][2] = R.drawable.house2
        else if (house in 9..20) totalImg[i][2] = R.drawable.house3
        else totalImg[i][2] = R.drawable.house4
    }

    return totalImg
}


@Preview(showBackground = true)
@Composable
fun PreviewGameMenuScreen() {
    fun doNothing(cap: String) {
    }
    GameMenuScreen(
        decks = arrayListOf(arrayListOf(0)),
        players = listOf(listOf("")),
        capId = 0,
        currentTeamName = "Red"
    )
}