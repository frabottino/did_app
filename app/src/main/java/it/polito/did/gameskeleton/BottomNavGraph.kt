package it.polito.did.gameskeleton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import it.polito.did.gameskeleton.screens.HomeScreen
import it.polito.did.gameskeleton.screens.ManagingScreen
import it.polito.did.gameskeleton.screens.NaplesScreen
import it.polito.did.gameskeleton.screens.TimelineScreen

@Composable
fun BottomNavGraph(team: String, navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(team)
        }
        composable(route = BottomBarScreen.Managing.route){
            ManagingScreen(team)
        }
        composable(route = BottomBarScreen.Timeline.route){
            TimelineScreen(team)
        }/*
        composable(route = BottomBarScreen.Naples.route){
            NaplesScreen()
        }*/
    }
}