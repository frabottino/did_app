package it.polito.did.gameskeleton


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
    object Mascotte: BottomBarScreen(
        route = "mascotte",
        title = "Mascotte",
        icon = android.R.drawable.ic_menu_recent_history
    )
}
