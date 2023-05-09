package it.polito.did.gameskeleton.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit
)
{
    Button(
        onClick = onClick,
        modifier = Modifier.then(modifier),
        shape = RoundedCornerShape(20),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Text(text, modifier = Modifier.padding(10.dp).then(textModifier))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButton() {
    GameSkeletonTheme (){
        CustomButton(text = "Button", onClick = {Log.v("click", "click")})
    }

}