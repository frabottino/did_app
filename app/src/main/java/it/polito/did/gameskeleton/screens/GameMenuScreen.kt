package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
            ExposedDropdownMenu(
                items = players.random(),
                selected = players.random()[capId]
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGameMenuScreen() {
}