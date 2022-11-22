package it.polito.did.gameskeleton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Managing: BottomBarScreen(
        route = "managing",
        title = "Managing",
        icon = Icons.Default.Person
    )
    object Timeline: BottomBarScreen(
        route = "timeline",
        title = "Timeline",
        icon = Icons.Default.Settings
    )
}
