package it.polito.did.gameskeleton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import it.polito.did.gameskeleton.screens.*

@Composable
fun BottomNavGraph(
    team: String,
    onMascotte: () -> Unit,
    cards: ArrayList<Int>,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(team, onMascotte)
        }
        composable(route = BottomBarScreen.Managing.route){
            ManagingScreen(team, cards)
        }
        composable(route = BottomBarScreen.Timeline.route){
            TimelineScreen(team)
        }/*
        composable(route = BottomBarScreen.Naples.route){
            NaplesScreen()
        }*/
    }
}