package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.screens.icons.Splash
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .padding(32.dp)){
        Image(
            imageVector = Icons.Splash,
            contentDescription = "Splash",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 1280)
@Composable
fun PreviewSpashScreen() {
    GameSkeletonTheme {
        SplashScreen()
    }
}