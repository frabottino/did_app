package it.polito.did.gameskeleton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class GameViewModel: ViewModel() {
    private val gameManager = GameManager(viewModelScope)

    fun onCreateNewGame() = gameManager.createNewGame()
    fun onJoinGame(matchId:String) = gameManager.joinGame(matchId)
    fun onStartGame() = gameManager.startGame()

    val players = gameManager.players
    val screenName = gameManager.screenName
    val matchId = gameManager.matchId

}