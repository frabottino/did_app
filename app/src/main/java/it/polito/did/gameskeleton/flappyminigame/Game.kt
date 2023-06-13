package it.polito.did.gameskeleton.flappyminigame

import android.os.CountDownTimer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.did_app.R
import it.polito.did.gameskeleton.GameViewModel

class Game {
    var bird = mutableStateOf(Bird(15f))
    val currentPipes = mutableStateListOf<Pipe>()
    var passCount by mutableStateOf(0)
    var addFlags by mutableStateOf(false)
    var gameRunning by mutableStateOf(true)
    var bg by mutableStateOf(Background(0, 400))

    var highScore = 0

    fun start(){
        currentPipes.add(Pipe((5..15).random() * 10))
        currentPipes.first().position = 500f
        currentPipes.add(Pipe((5..15).random() * 10))
        currentPipes.last().position = 750f
    }

    fun updatePipe(){
        if (currentPipes.last().position < 750f) {
            currentPipes.add(Pipe((5..15).random() * 10))
            addFlags = true
        }
        if (currentPipes.first().position < -150)
            currentPipes.removeAt(0)
        currentPipes.asReversed().forEach { it.run(this) }
    }

    fun update(flags: Boolean) {
        if(flags)
            bird.value.update()
        else
            bird.value.fly()
    }

    fun restart(){
        currentPipes.clear()
        passCount = 0
        gameRunning = true
        start()
    }

    fun setHigh(score : Int) : Int{
        if(score >= highScore) highScore = score
        return highScore
    }

    fun getHigh() : Int{
        return highScore
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun FlappyBird() {
    val vm = GameViewModel.getInstance()
    val game = remember { Game() }
    var switchsFlagWindow by remember { mutableStateOf(1) }
    var upOrDown by remember { mutableStateOf(true) }
    var st by remember { mutableStateOf(System.currentTimeMillis()) }
    var end: Long

    val timer = object: CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            game.setHigh(game.passCount)
        }
        override fun onFinish() {
            vm.sendMiniPts(game.highScore)
            vm.onEndMiniGame()
        }
    }
    timer.start()

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(
            onClick = {
                if (switchsFlagWindow == 2)
                    upOrDown = !upOrDown
                st = System.currentTimeMillis()
            })) {
        Image(
            painter = painterResource(id = R.drawable.street_bg),//"drawable/background.png"),
            contentDescription = "Icon", //sfondo di gioco
            modifier = Modifier.fillMaxSize()
        )
        if (switchsFlagWindow == 1)
            Button(
                onClick = { switchsFlagWindow = 2
                    game.start()},
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(height = 68.dp, width = 150.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Image(
                    painter = painterResource(android.R.drawable.ic_menu_manage),//"drawable/start.png"),
                    contentDescription = "Start",
                    modifier = Modifier.fillMaxSize()
                )
            }
        else if (switchsFlagWindow == 2){
            var text by remember { mutableStateOf("60 FPS") }
            var timeF by remember { mutableStateOf(System.nanoTime()) }
            game.currentPipes.forEach { PutPipe(it) }
            InitBird(game.bird.value)
            //Image(painter = painterResource(id = android.R.drawable.arrow_down_float), //era commentato
            //contentDescription = "ground", //era commentato
            //modifier = Modifier.offset(x = 0.dp, y = 400.dp)) //era commentato
            setBg(game.bg)
            Text(text = text,
                modifier = Modifier.background(color = Color.White))
            Text(text = "${game.passCount}",
                style = TextStyle( color = Color.White, fontSize = 24.sp),
                modifier = Modifier
                    .align(Alignment.BottomCenter))
            PutPipe(Pipe(20)) //era commentato
            LaunchedEffect(Unit){
                while (game.bird.value.position <= 400f && game.gameRunning) {
                    withFrameNanos {
                        end = System.currentTimeMillis()
                        if (!upOrDown && end - st > 150) {
                            upOrDown = true
                        }
                        game.update(upOrDown)
                        game.updatePipe()
                        end = System.nanoTime()
                        if (end - timeF > 1000_000_000L) {
                            text = "${((2000_000_000L - (end - timeF)) % 1000000L / 10000).coerceAtLeast(30)} FPS"
                            timeF = System.nanoTime()
                        }
                    }
                    game.bg.render()
                }
                switchsFlagWindow = 3
            }
        }
        else {
            Button(
                onClick = { switchsFlagWindow = 2
                    game.restart()
                    game.bird.value.position = 100f}
                ){
                    Image(
                        painter = painterResource(android.R.drawable.ic_dialog_dialer),//"drawable/gameover.png"),
                        contentDescription = "gameover"
                        //modifier = Modifier.align(Alignment.Center)
                    )
                }
            Text(
                text = "Score: ${game.passCount}\nHighscore: ${game.setHigh(game.passCount)}",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
                    .background(Color.White)
                    .size(height = 50.dp, width = 120.dp),
                textAlign = TextAlign.Center)
        }
    }

}
