package it.polito.did.gameskeleton.screens

import android.graphics.Color
import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun SetupMatchScreen(matchId: String,
                     players: State<Map<String,String>?>,
                     onStartMatch: () -> Unit,
                     modifier: Modifier = Modifier) {

    GenericScreen(title = "Waiting for Players", modifier) {
        BoxWithConstraints() {
            if (constraints.maxHeight> constraints.maxWidth) {
                val size = constraints.maxWidth*9/10;
                var encoder = QRGEncoder(matchId, null, QRGContents.Type.TEXT, size)
                encoder.colorWhite = Color.BLACK
                encoder.colorBlack = Color.WHITE
                Column(Modifier.fillMaxSize()) {
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = matchId,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Image(
                        encoder.bitmap.asImageBitmap(),
                        "QRCode",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Connected players: ${players.value?.toString() ?: 0}",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.weight(1f))
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        onClick = { onStartMatch() }) {
                        Text("Start Match")
                    }
                    Spacer(Modifier.weight(1f))
                }
            } else {
                val size = constraints.maxHeight*9/10;
                var encoder = QRGEncoder(matchId, null, QRGContents.Type.TEXT, size)
                encoder.colorWhite = Color.BLACK
                encoder.colorBlack = Color.WHITE
                Row(Modifier.fillMaxSize()) {
                    Column(Modifier.fillMaxHeight().weight(1f)) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = matchId,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Image(
                            encoder.bitmap.asImageBitmap(),
                            "QRCode",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                    Column(Modifier.fillMaxHeight().weight(1f)) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Connected players: ${players.value?.size ?: 0}",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.weight(1f))
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            onClick = { onStartMatch() }) {
                            Text("Start Match")
                        }
                        Spacer(Modifier.weight(1f))

                    }
                }
            }
        }
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(1f))
            Text(
                text = matchId,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            BoxWithConstraints() {
                val size = (Math.min(constraints.maxHeight, constraints.maxWidth) * .95f).toInt()
                var encoder = QRGEncoder(matchId, null, QRGContents.Type.TEXT, size)
                encoder.colorWhite = Color.BLACK
                encoder.colorBlack = Color.WHITE
                Log.d("GameVM", "size: $size")
                Image(
                    encoder.bitmap.asImageBitmap(),
                    "QRCode",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "Connected players: ${players.value?.size ?: 0}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = { /*TODO*/ }) {
                Text("Start Match")
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 1280)
@Composable
fun DefaultPreview1() {
    val players: State<Map<String,String>> = remember {
        mutableStateOf(mapOf("Mario" to "team1", "Paola" to "team2"))
    }
    GameSkeletonTheme {
        SetupMatchScreen("abc", players, {})
    }
}