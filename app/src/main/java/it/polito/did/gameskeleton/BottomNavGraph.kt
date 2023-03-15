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
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Managing.route){
            ManagingScreen()
        }
        composable(route = BottomBarScreen.Timeline.route){
            TimelineScreen()
        }/*
        composable(route = BottomBarScreen.Naples.route){
            NaplesScreen()
        }*/
    }
}