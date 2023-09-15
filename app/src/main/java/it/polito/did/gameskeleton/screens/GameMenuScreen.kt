package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.did_app.R
import kotlin.reflect.KFunction1

@Composable
fun GameMenuScreen(decks: ArrayList<ArrayList<Int>>, players: Collection<List<String>>, capId: Int, onSetTempCap: KFunction1<String, Unit>, modifier: Modifier = Modifier) {

    GenericScreen(title = ""){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = decks.toString(),
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = decks.toString(),
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            /*
            ExposedDropdownMenu(
                items = players.random(),
                selected = players.random()[capId]
            )
             */

            //TODO non dimentichiamoci di rimetterlo

            var images = getDecksImage(decks)
            for (i in 0 until images.size) {
                for (j in 0 until 3) {
                    Image(
                        painter = painterResource(
                            id = images[i][j]
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }
}

fun getDecksImage(decks: ArrayList<ArrayList<Int>>) : ArrayList<ArrayList<Int>>{
    var img1 = arrayListOf(0,0,0)
    var img2 = arrayListOf(0,0,0)
    var img3 = arrayListOf(0,0,0)
    var img4 = arrayListOf(0,0,0)
    var totalImg = arrayListOf(img1, img2, img3, img4)
    for(i in 0 until decks.size){
        var transport = decks[i][0]*decks[i][0] + decks[i][1]*decks[i][1] + decks[i][2]*decks[i][2]
        var power = decks[i][3]
        var house = decks[i][4]
        if (transport <= 4) totalImg[i][0] = R.drawable.transport1
        else if (transport > 4 || transport <= 12) totalImg[i][0] = R.drawable.transport2
        else if (transport > 12 || transport <= 20) totalImg[i][0] = R.drawable.transport3
        else totalImg[i][0] = R.drawable.transport4

        if (power <= 2) totalImg[i][1] = R.drawable.city1
        else if (power > 2 || power <= 10) totalImg[i][1] = R.drawable.city2
        else if (power > 10 || power <= 22) totalImg[i][1] = R.drawable.city3
        else totalImg[i][1] = R.drawable.city4

        if (house <= 2) totalImg[i][2] = R.drawable.house1
        else if (house > 2 || house <= 8) totalImg[i][2] = R.drawable.house2
        else if (house > 8 || house <= 20) totalImg[i][2] = R.drawable.house3
        else totalImg[i][2] = R.drawable.house4
    }

    return totalImg
}


@Preview(showBackground = true)
@Composable
fun PreviewGameMenuScreen() {
}