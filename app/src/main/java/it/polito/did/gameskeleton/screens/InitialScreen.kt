package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun InitialScreen(onStartNewGame: () -> Unit,
                  onJoinGame: (String) -> Unit,
                  modifier: Modifier = Modifier
) {
    val matchId = remember {
        mutableStateOf("")
    }
    GenericScreen(title = "Initial Screen") {
        Column(modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onStartNewGame ) {
                Text("Start new match")
            }
            Spacer(Modifier.height(32.dp))
            Row {
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
                Text("or")
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
            TextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                singleLine = true,
                value = matchId.value, onValueChange = {matchId.value = it})
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {onJoinGame(matchId.value)}) {
                Text("Join match")
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInitialScreen() {
    GameSkeletonTheme {
        InitialScreen({},{})
    }

}