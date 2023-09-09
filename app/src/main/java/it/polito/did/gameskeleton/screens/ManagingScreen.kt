package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import java.lang.Integer.min

@Composable
fun ManagingScreen(team: String, cards: ArrayList<Int>) {
    val configuration = LocalConfiguration.current

    val w = configuration.screenWidthDp.dp

    GameSkeletonTheme(team = team) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()), Arrangement.Center
        ) {
            Text(
                text = "Your cards",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(32.dp))

            cards.removeIf { it == 0 }

            for (i in 0 until (cards.size + 2) / 3) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (j in 0 until min(3, cards.size - 3 * i)) {
                        Box(modifier = Modifier.size(height = 220.dp, width = (w / 3 - 20.dp))) {

                            Image(
                                painter = painterResource(
                                    id = GameViewModel.getInstance()
                                        .getCardImage(cards[(3 * i) + j]).image
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                    }

                }
                Spacer(Modifier.height(32.dp))

            }

            Spacer(Modifier.height(80.dp))

        }
    }
}

@Composable
@Preview
fun ManagingScreenPreview() {
}