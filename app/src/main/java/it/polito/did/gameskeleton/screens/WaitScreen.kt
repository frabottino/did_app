package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun WaitScreen(modifier: Modifier = Modifier) {
    GenericScreen(title = "Waiting", modifier) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaitScreen() {
    GameSkeletonTheme {
        WaitScreen()
    }

}