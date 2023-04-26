package it.polito.did.gameskeleton

class Player {
    private val teams : Teams
        get() {
            TODO()
        }
    private var playerId : Int = 0
    private var isMyPersonalTurn : Boolean = false
    private var isMyTeamTurn : Boolean = false
    private var nickname = String
    private var playerTeam : Int = 0

    fun getPlayerId() : Int{
        return playerId
    }

    fun getPlayerTeam() : Int{
        return playerTeam
    }

    fun setPlayerId(id : Int){
        playerId = id
    }

    fun setPlayerTeam(team : Int){
        playerTeam = team
    }

    fun isYourTurn(id : Int){
        isMyPersonalTurn = playerId==id
    }

    fun isYourTeamTurn(id : Int){
        isMyTeamTurn = playerTeam == id
    }
}