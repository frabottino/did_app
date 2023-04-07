package it.polito.did.gameskeleton.flappyminigame


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.did_app.R

@Composable
fun PutPipe(pipe : Pipe) {
    Box(
        Modifier
            .offset(pipe.position.dp, pipe.yCoor.dp - 120.dp)
            .shadow(0.dp)
            .clip(RectangleShape)
    ) {
        Image(painter = painterResource(R.drawable.barrier_bg),
            contentDescription = "Pipe", //ostacoli
            modifier = Modifier.size(500.dp, 500.dp))
    }
}

data class Pipe(val yCoor : Int){
    var position by mutableStateOf(1000f)
    var pass = true
    fun run(game : Game){
        position -= 1
        //println("position " + position)
        if (position in -100.0..00.0) {
            if(!(game.bird.value.position + 17.5 >= yCoor + 57.5 && game.bird.value.position + 47.5 <= yCoor + 233.5))
                game.gameRunning = false
        }
        if (position < -105 && pass){
            pass = false
            game.passCount++
        }
    }
}