package it.polito.did.gameskeleton

sealed class ScreenName {
    object Splash: ScreenName()
    object Initial: ScreenName()
    class SetupMatch(val matchId: String): ScreenName()
    object WaitingStart: ScreenName()
    class Playing(val team: String): ScreenName()
    object Dashboard: ScreenName()
    object Memory: ScreenName()
    class Error(val message:String): ScreenName()
    class Home(val team: String) : ScreenName()
}












