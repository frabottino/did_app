package it.polito.did.gameskeleton


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.ui.unit.sp

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

@Composable
fun BottomBar(team : String, navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Managing,
        BottomBarScreen.Mascotte,
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    BottomNavigation(
        backgroundColor = ChangeInColor(team),
        modifier = Modifier.height(80.dp)
    ) {
        screens.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(40.dp)) },
                label = { Text(text = item.title,
                    fontSize = 15.sp) },
                selectedContentColor = Color.Black,
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

fun ChangeInColor(color: String) : Color{
    return when(color) {
        "Red" -> Color.Red
        "Blue" -> Color.Blue
        "Green" -> Color.Green
        "Yellow" -> Color.Yellow
        else -> Color.Gray
    }
}

@Composable
@Preview
fun BottomNavPreview() {
    BottomNav("Green", arrayListOf(1, 2, 3), {}, {}, {}, arrayListOf(1, 2, 3, 4, 5))
}
