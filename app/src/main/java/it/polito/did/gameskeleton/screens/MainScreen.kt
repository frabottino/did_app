package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import it.polito.did.gameskeleton.BottomNav
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ScreenName
import it.polito.did.gameskeleton.flappyminigame.FlappyBird

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val vm = GameViewModel.getInstance()
    val players = vm.players.observeAsState()
    when (val screenName = vm.screenName.observeAsState().value) {
        is ScreenName.Splash -> SplashScreen(modifier)
        is ScreenName.Initial -> InitialScreen(
            vm::onCreateNewGame,
            vm::onJoinGame,
            modifier
        )
        is ScreenName.SetupMatch -> SetupMatchScreen(
            screenName.matchId,
            players,
            vm::onStartGame,
            modifier
        )
        is ScreenName.Memory -> MainContent(
            vm::onStartMemory,
            vm,
            modifier
        )
        is ScreenName.Home -> BottomNav(
            screenName.team,
            vm.getMyCards(),
            vm::onStartMemory,
            vm::onStartFlappy,
            vm::onStartQuiz,
            vm.getMyDeck(),
            modifier
        )
        is ScreenName.Cards -> CardsScreen(
            screenName.team,
            vm::pickCards,
            vm.getFiveCards()
        )
        is ScreenName.Cards2 -> Cards2Screen(
            screenName.team,
            vm::pickCards,
            vm.getFiveCards(),
            screenName.one,
            screenName.two,
            screenName.i
        )
        is ScreenName.Cards3 -> Cards3Screen(
            screenName.team,
            vm::pickCards,
            vm.getFiveCards(),
            screenName.one,
            screenName.two,
            screenName.i,
            screenName.check,
            screenName.x
        )
        is ScreenName.MiniRank -> MiniRankingScreen(
            vm.getMiniRank(),
            vm::onStartPhase
        )
        is ScreenName.PlayerRank -> PlayerRankingScreen(
            screenName.team,
            vm.getPlayerRank()
        )
        is ScreenName.Victory -> VictoryScreen(
            vm.getFinalRank(),
            vm::onEndgame
        )
        is ScreenName.PlayerVictory -> PlayerVictoryScreen(
            vm.getFinalPlayerRank()
        )
        is ScreenName.Menu -> GameMenuScreen(
            vm.getDecks(),
            vm.getTeamList(vm.getTeamId()),
            vm.getCapId(),
            vm::setTempCap
        )
        is ScreenName.Flappy -> FlappyBird()
        is ScreenName.Quiz -> QuizScreen()
        is ScreenName.WaitingStart -> WaitScreen(modifier, "We are building your team...")
        is ScreenName.WaitingMini -> WaitScreen(modifier, "Wait for the minigame to end...")
        is ScreenName.WaitingCards -> WaitForCardsScreen(screenName.team)
        is ScreenName.Dashboard -> DashboardScreen(modifier)
        is ScreenName.Playing -> PlayerScreen(screenName.team,  modifier)
        is ScreenName.Error -> ErrorScreen(screenName.message, modifier)
        is ScreenName.Generic -> GenericScreen(title = "Play")
        null -> Box(modifier)
    }
}
