package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@OptIn(ExperimentalUnitApi::class)
@Composable
fun InitialScreen(
    onStartNewGame: () -> Unit,
    onJoinGame: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val matchId = remember {
        mutableStateOf("")
    }
    var nameId = remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.weight(0.3f))
            Text(
                text = "LogIn Page", textAlign = TextAlign.Center,
                modifier = Modifier.align(CenterHorizontally),
                color = MaterialTheme.colors.primary,
                fontSize = TextUnit(10f, TextUnitType.Em)
            )
            Spacer(Modifier.weight(0.4f))
            CustomButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onStartNewGame,
                text = "START NEW GAME"
            )
            Spacer(Modifier.height(32.dp))
            Row {
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
                Text("OR")
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(
                Modifier.height(32.dp)
                //.background(color = Color.Yellow)
            )
            OutlinedTextField(
                label = { Text(text = "Enter Match ID") },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                singleLine = true,
                shape = RoundedCornerShape(20),
                value = matchId.value, onValueChange = { matchId.value = it })
            OutlinedTextField(
                label = { Text(text = "Enter Your Name") },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                singleLine = true,
                shape = RoundedCornerShape(20),
                value = nameId.value, onValueChange = { nameId.value = it })
            /*TextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                singleLine = true,
                value = matchId.value, onValueChange = {matchId.value = it})
            TextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                singleLine = true,
                value = nameId.value , onValueChange = {nameId.value = it})*/
            Spacer(
                Modifier.height(32.dp)
            )
            CustomButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onJoinGame(matchId.value, nameId.value) },
                text = "JOIN MATCH"
            )

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 1280)
@Composable
fun PreviewInitialScreen() {
    GameSkeletonTheme {
        InitialScreen({}, { s: String, s1: String -> })
    }

}