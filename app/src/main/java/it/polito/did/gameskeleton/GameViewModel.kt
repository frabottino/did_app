package it.polito.did.gameskeleton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class GameViewModel: ViewModel() {

    companion object {
        @Volatile
        private var instance: GameViewModel? = null

        fun getInstance(): GameViewModel {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = GameViewModel()
                    }
                }
            }
            return instance!!
        }
    }

    private val gameManager = GameManager(viewModelScope)

    fun onCreateNewGame() = gameManager.createNewGame()
    fun onJoinGame(matchId:String, nameId:String) = gameManager.joinGame(matchId, nameId)
    fun onStartGame() = gameManager.startGame()
    fun onStartMemory() = gameManager.startMemory()
    fun onStartFlappy() = gameManager.startFlappy()
    fun onStartQuiz() = gameManager.startQuiz()
    fun onGoHome() = gameManager.goToHome()
    fun onGoMascotte() = gameManager.goToMascotte()
    fun getTurnFromGM() = gameManager.getTurn()
    fun nextTurn() = gameManager.changeTurn()

    val players = gameManager.players
    val screenName = gameManager.screenName
    val matchId = gameManager.matchId

}