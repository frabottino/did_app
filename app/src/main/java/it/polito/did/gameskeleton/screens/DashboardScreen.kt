package it.polito.did.gameskeleton.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    //GenericScreen(title = "Dashboard", modifier) {

    //}
    it.polito.did.gameskeleton.MainScreen()
    // TODO: ricordarsi di mettere questo in interfaccia studente e non docente
}

@Preview(showBackground = true)
@Composable
fun PreviewDashboardScreen() {
    GameSkeletonTheme {
        it.polito.did.gameskeleton.MainScreen()
    }
}