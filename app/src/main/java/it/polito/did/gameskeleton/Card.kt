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


    fun getId() : Int{
        return idC
    }

    fun getPower() : Int{
        return powC
    }

    fun getType() : Int{
        return typeC
    }
}
