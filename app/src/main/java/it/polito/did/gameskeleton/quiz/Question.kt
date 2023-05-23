package it.polito.did.gameskeleton.quiz

data class Question(
    val id : Int,
    val question : String,
    //val image : Int,
    val optionOne : String,
    val optionTwo : String,
    val optionThree : String,
    val correctAnswer : Int
)