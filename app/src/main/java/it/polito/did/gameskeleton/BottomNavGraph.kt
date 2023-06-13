package it.polito.did.gameskeleton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import it.polito.did.gameskeleton.screens.*

@Composable
fun BottomNavGraph(
    team: String,
    cards: ArrayList<Int>,
    onMemory: () -> Unit,
    onFlappy: () -> Unit,
    onQuiz: () -> Unit,
    deck: ArrayList<Int>,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(team, deck)
        }
        composable(route = BottomBarScreen.Managing.route){
            ManagingScreen(team, cards)
        }
        composable(route = BottomBarScreen.Mascotte.route){
            MascotteScreen(team, onMemory, onFlappy, onQuiz)
        }
    }
}