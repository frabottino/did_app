package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import it.polito.did.gameskeleton.BottomNav
import it.polito.did.gameskeleton.Card
import it.polito.did.gameskeleton.GameViewModel
//import it.polito.did.gameskeleton.MainBottomScreen
import it.polito.did.gameskeleton.ScreenName
import it.polito.did.gameskeleton.flappyminigame.FlappyBird

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val vm: GameViewModel = viewModel()
    val players = vm.players.observeAsState()
    when (val screenName = vm.screenName.observeAsState().value) {
        is ScreenName.Splash -> SplashScreen(modifier)
        is ScreenName.Initial -> InitialScreen(
            vm::onCreateNewGame,
            vm::onJoinGame,
            modifier)
        is ScreenName.SetupMatch -> SetupMatchScreen(
            screenName.matchId,
            players,
            vm::onStartGame,
            modifier)
        is ScreenName.Memory -> MainContent(
            vm::onStartMemory,
            vm,
            modifier)
        is ScreenName.Home -> BottomNav(screenName.team, modifier)
        is ScreenName.Mascotte -> MascotteScreen(
            screenName.team,
            vm::onStartMemory,
            vm::onStartFlappy,
            vm::onStartQuiz,
            modifier)
        is ScreenName.Cards -> CardsScreen(
            screenName.team,
            vm::onGoHome)
        is ScreenName.Flappy -> FlappyBird()
        is ScreenName.Quiz -> QuizScreen()
        is ScreenName.WaitingStart -> WaitScreen(modifier)
        is ScreenName.Dashboard -> DashboardScreen(modifier)
        is ScreenName.Playing -> PlayerScreen(screenName.team,  modifier)
        is ScreenName.Error -> ErrorScreen(screenName.message, modifier)
        null -> Box(modifier)
    }
}
