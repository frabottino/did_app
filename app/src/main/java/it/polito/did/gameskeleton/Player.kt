package it.polito.did.gameskeleton

class Player {
    private val teams : Teams
        get() {
            TODO()
        }
    private val isMyPersonalTurn : Boolean = false
    private val isMyTeamTurn : Boolean = false
    private val cards = List(18) {Card()}
}