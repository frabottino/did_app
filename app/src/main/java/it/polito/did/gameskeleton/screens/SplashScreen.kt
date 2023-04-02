package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val painter = painterResource(id = R.drawable.citybg)
        Image(
            //imageVector = Icons.Splash,
            painter = painter,
            contentDescription = "Splash screen",
            modifier = Modifier
                .fillMaxSize(), contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.icon_home),
            contentDescription = "Home Icon",
            modifier = Modifier.size(300.dp, 300.dp)
        )
        Text(text = "betterCity", textAlign = TextAlign.Center, color = Color.Black, fontSize = 60.sp, maxLines = 1)
    }

}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun PreviewSpashScreen() {
    GameSkeletonTheme("Red") {
        SplashScreen()
    }
}