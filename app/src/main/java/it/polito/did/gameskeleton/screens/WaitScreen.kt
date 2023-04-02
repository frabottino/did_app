package it.polito.did.gameskeleton.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
fun WaitScreen(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val value by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column() {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center,){
                Image(
                    painter = painterResource(id = R.drawable.housewait),
                    contentDescription = "Wait screen",
                    modifier = Modifier.size(320.dp).alpha(value)
                )
                Text(text = "We are building your team...", textAlign = TextAlign.Center, color = MaterialTheme.colors.primary, fontSize = 40.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 200.dp))

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaitScreen() {
    GameSkeletonTheme (){
        WaitScreen()
    }

}