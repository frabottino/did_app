package it.polito.did.gameskeleton

class Card(
    val typeC : Int,
    val idC : Int,
    val powC : Int
    //val image : Int
)
    {

    val id = Int
    val power = Int
    val level = Int
    val team = String
    val type = String
    //val image : Int

    fun displayCards() {
        //TODO selezionare le 5 carte o le 3+2 da mostrare
    }

    fun pickCard() {
        //TODO metodo per assegnare carta al team, forse da mettere in altra classe capiamo
    }

    fun assignCard() {
        //TODO eventualmente da ausilio per associare team e carta
    }
}
