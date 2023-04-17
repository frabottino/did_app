package it.polito.did.gameskeleton


data class QuestionDTO(
    val question: String,
    val answers: List<AnswerDTO>,
    val correctAnswerId: String
)

data class AnswerDTO(
    val id: String,
    val text: String
)