package it.polito.did.gameskeleton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Filled.Home
    )
    object Managing: BottomBarScreen(
        route = "managing",
        title = "Managing",
        icon = Icons.Filled.Settings
    )
    object Mascotte: BottomBarScreen(
        route = "mascotte",
        title = "Mascotte",
        icon = Icons.Filled.AccountBox
    )
}
