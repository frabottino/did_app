package it.polito.did.gameskeleton

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import com.example.did_app.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = android.R.drawable.ic_dialog_dialer
    )
    object Managing: BottomBarScreen(
        route = "managing",
        title = "Managing",
        icon = android.R.drawable.ic_menu_manage
    )
    object Timeline: BottomBarScreen(
        route = "timeline",
        title = "Timeline",
        icon = android.R.drawable.ic_menu_recent_history
    )
}
