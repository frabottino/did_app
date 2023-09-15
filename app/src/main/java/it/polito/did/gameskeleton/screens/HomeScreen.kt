package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.did_app.R
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun HomeScreen(team: String, deck: ArrayList<Int>) {
    var images = getDeckImage(deck)

    GameSkeletonTheme(team = team) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {

            for (i in 0 until images.size) {

                Image(
                    painter = painterResource(
                        id = images[i]
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(1f)
                )

            }
        }
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "Your city",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

fun getDeckImage(deck: ArrayList<Int>): ArrayList<Int> {
    println("DECK $deck")
    var img = arrayListOf(0, 0, 0)
    var transport = deck[0] * deck[0] + deck[1] * deck[1] + deck[2] * deck[2]
    var power = deck[3]
    var house = deck[4]
    if (transport <= 4) img[0] = R.drawable.transport1
    else if (transport in 5..12) img[0] = R.drawable.transport2
    else if (transport in 13..20) img[0] = R.drawable.transport3
    else img[0] = R.drawable.transport4

    if (power <= 2) img[1] = R.drawable.city1
    else if (power in 3..10) img[1] = R.drawable.city2
    else if (power in 11..22) img[1] = R.drawable.city3
    else img[1] = R.drawable.city4

    if (house <= 2) img[2] = R.drawable.house1
    else if (house in 3..8) img[2] = R.drawable.house2
    else if (house in 9..20) img[2] = R.drawable.house3
    else img[2] = R.drawable.house4

    return img
}


@Composable
@Preview
fun HomeScreenPreview() {
}