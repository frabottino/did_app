package it.polito.did.gameskeleton.flappyminigame


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
fun setBg(bg : Background){
    Box(
        Modifier
            .offset(0.dp, 430.dp)//bg.position.dp, bg.y.dp)
            .shadow(0.dp)
            .clip(RectangleShape)
    ) {
        Image(painter = painterResource(id = R.drawable.grass),
            contentDescription = "Pipe", //ground in basso
            modifier = Modifier.size(500.dp, 335.dp)
        )
    }
}

data class Background(var x : Int, val y : Int){
    var position by mutableStateOf(x.toFloat())
    fun render(){
        position = if(position <= -2) 2f else position - 0.1f
    }
}