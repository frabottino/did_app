package it.polito.did.gameskeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import it.polito.did.gameskeleton.screens.MainScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //println("ciao")
        setContent {
           GameSkeletonTheme ("default"){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    MainScreen()
                    //BottomNav()
                }
           }
        }
    }
}

