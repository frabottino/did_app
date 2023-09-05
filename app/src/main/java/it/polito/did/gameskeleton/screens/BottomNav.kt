package it.polito.did.gameskeleton


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun BottomNav(
    team: String,
    cards: ArrayList<Int>,
    onMemory: () -> Unit,
    onFlappy: () -> Unit,
    onQuiz: () -> Unit,
    deck: ArrayList<Int>,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    println(team)
    GameSkeletonTheme(team = team) {
        Scaffold(
            bottomBar = { BottomBar(team, navController = navController) }
        ) {
            Modifier.padding(it)
            BottomNavGraph(
                team,
                cards,
                onMemory,
                onFlappy,
                onQuiz,
                deck,
                navController = navController
            )
        }
    }
}

@Composable
fun BottomBar(team: String, navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Managing,
        BottomBarScreen.Mascotte,
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.height(80.dp)
    ) {
        screens.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(40.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 15.sp
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun BottomNavPreview() {
    BottomNav("Green", arrayListOf(1, 2, 3), {}, {}, {}, arrayListOf(1, 2, 3, 4, 5))
}
