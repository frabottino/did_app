package it.polito.did.gameskeleton.screens

import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import kotlin.math.min

@Composable
fun SetupMatchScreen(
    matchId: String,
    players: State<Map<String, String>?>,
    onStartMatch: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Waiting for players...",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                Modifier.height(100.dp)
            )
        }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))

            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .align(Alignment.CenterHorizontally)
            ) {
                val size =
                    (Math.min(constraints.maxHeight, constraints.maxWidth)).toInt()
                var encoder = QRGEncoder(matchId, null, QRGContents.Type.TEXT, size)
                encoder.colorWhite = Color.BLACK
                encoder.colorBlack = Color.WHITE
                Log.d("GameVM", "size: $size")
                val wRatio = if (constraints.maxHeight > constraints.maxWidth) 0.8f else 0.5f
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 10.dp,
                    modifier = Modifier
                        .fillMaxWidth(wRatio)
                        .fillMaxHeight()
                        .align(
                            Alignment.Center
                        )

                ) {
                    if (constraints.maxHeight > constraints.maxWidth) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                encoder.bitmap.asImageBitmap(),
                                "QRCode",
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .padding(20.dp)
                                    .padding(top = 20.dp)
                            )
                            Text(
                                text = matchId,
                                style = MaterialTheme.typography.h4,

                                )
                        }
                    } else {
                        Row() {
                            Column(
                                Modifier.fillMaxWidth(.5f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(Modifier.fillMaxSize()) {
                                    Image(
                                        encoder.bitmap.asImageBitmap(),
                                        "QRCode",
                                        alignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(10.dp)
                                    )
                                }
                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = matchId,
                                    style = MaterialTheme.typography.h4,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "Connected players: ${players.value?.size ?: 0}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(1f))
            CustomButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textModifier = Modifier.padding(horizontal = 50.dp),
                onClick = onStartMatch,
                text = "START NEW GAME"
            )
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, widthDp = 1080, heightDp = 2340)
@Composable
fun DefaultPreview1() {
    val players: State<Map<String, String>> = remember {
        mutableStateOf(mapOf("Mario" to "team1", "Paola" to "team2"))
    }
    GameSkeletonTheme() {
        SetupMatchScreen("abc", players, {})
    }
}

@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun DefaultPreview2() {
    val players: State<Map<String, String>> = remember {
        mutableStateOf(mapOf("Mario" to "team1", "Paola" to "team2"))
    }
    GameSkeletonTheme() {
        SetupMatchScreen("abc", players, {})
    }
}