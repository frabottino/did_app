package it.polito.did.gameskeleton.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun PlayerScreen(team: String, modifier: Modifier = Modifier) {
    GenericScreen(title = "Player(${team})", modifier) {

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayerScreen() {
    GameSkeletonTheme {
        PlayerScreen("Team A")
    }
}