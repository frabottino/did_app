package it.polito.did.gameskeleton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class GameViewModel: ViewModel() {
    private val gameManager = GameManager(viewModelScope)

    fun onCreateNewGame() = gameManager.createNewGame()
    fun onJoinGame(matchId:String, nameId:String) = gameManager.joinGame(matchId, nameId)
    fun onStartGame() = gameManager.startGame()
    fun onStartMemory() = gameManager.startMemory()

    val players = gameManager.players
    val screenName = gameManager.screenName
    val matchId = gameManager.matchId

}