package it.polito.did.gameskeleton

sealed class ScreenName {
    object Splash: ScreenName()
    object Initial: ScreenName()
    class SetupMatch(val matchId: String): ScreenName()
    object WaitingStart: ScreenName()
    object WaitingMini: ScreenName()
    class Playing(val team: String): ScreenName()
    object Dashboard: ScreenName()
    object Memory: ScreenName()
    class Error(val message:String): ScreenName()
    class Home(val team: String) : ScreenName()
    object Menu : ScreenName()
    object MiniRank : ScreenName()
    class PlayerRank(val team: String) : ScreenName()
    object Victory : ScreenName()
    class PlayerVictory(val team: String) : ScreenName()
    class WaitingCards(val team: String) : ScreenName()
    class Cards(val team: String) : ScreenName()
    class Cards2(val team: String, val one: Int, val two : Int, val i : Int) : ScreenName()
    class Cards3(val team: String, val one: Int, val two : Int, val i : Int, val check : Int, val x : Int) : ScreenName()

    object Flappy : ScreenName()
    object Quiz : ScreenName()
    object Generic : ScreenName()
    }












