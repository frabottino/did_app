package it.polito.did.gameskeleton

class Player {
    private var playerId : Int = -1
    private var isMyPersonalTurn : Boolean = false
    private var isMyTeamTurn : Boolean = false
    private var nickname = String
    private var playerTeam : Int = -1

    fun getPlayerId() : Int{
        return playerId
    }

    fun getPlayerTeam() : Int{
        return playerTeam
    }

    fun setPlayerId(id : Int){
        if(playerId == -1) playerId = id
    }

    fun setPlayerTeam(team : Int){
        if(playerTeam == -1) playerTeam = team
    }

    fun isYourTurn(id: Int, size: ArrayList<Int>, team: String) : Boolean{
        return when(team) {
            "Red" -> playerId == (id % size[0])
            "Blue" -> playerId == (id % size[1])
            "Green" -> playerId == (id % size[2])
            "Yellow" -> playerId == (id % size[3])
            else -> playerId == id
        }
    }

    fun isYourTeamTurn(id: Int, team: String, teams: MutableList<String>) : Boolean{
        return team == teams[id]
    }
}