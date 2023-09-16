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
    fun getTurnFromGM() = gameManager.getTurn()
    fun nextTurn() = gameManager.changeTurn()
    fun getPlayerTurn() = gameManager.isPlayerTurn()
    fun getTeamTurn() = gameManager.isTeamTurn()
    fun sendMiniPts(pts: Int, wait: Boolean = true) = gameManager.sendMiniResult(pts, wait)
    fun pickCards(one : Int, two : Int, c1 : Int?, card1 : Int?, c2 : Int?, card2 : Int?) = gameManager.pickCards(one, two, c1, card1, c2, card2)
    fun getFiveCards() = gameManager.sendFiveCards()
    fun getMyCards() = gameManager.getMyCards()
    fun onGoCards2(first: Int, second: Int, i: Int) = gameManager.goToCards2(first, second, i)
    fun onGoCards3(first: Int, second: Int, i: Int, check : Int, x : Int) = gameManager.goToCards3(first, second, i, check, x )//i carta da controllare, check relativo alla prima, x prima carta controllata
    fun getDecks() = gameManager.getAllDecks()
    fun getMyDeck() = gameManager.getMyDeck()
    fun getCapId() = gameManager.getCapID()
    fun getTeamId() = gameManager.getTeamID()
    fun getCurrentTeamName() = gameManager.getCurrentTeamName()
    fun getTeamNames() = gameManager.getTeamNames()
    fun setTempCap(cap : String) = gameManager.setTemporaryCap(cap)
    fun getTeamList(team : Int) = gameManager.getTeamPlayers(team)
    fun getCardImage(id: Int) = gameManager.getCardImage(id)
    fun getMiniRank() = gameManager.getMiniRank()
    fun getPlayerRank() = gameManager.getPlayerRank()
    fun getFinalRank() = gameManager.getFinalRank()
    fun getFinalPlayerRank() = gameManager.getFinalPlayerRank()
    fun onStartPhase() = gameManager.startNewPhase()
    fun onEndMiniGame() = gameManager.endMinigame()
    fun onEndgame() = gameManager.endGame()
    fun onCheckCards(pay: Int, what: String) = gameManager.checkCard(pay, what)

    val players = gameManager.players
    val screenName = gameManager.screenName
    val matchId = gameManager.matchId

}