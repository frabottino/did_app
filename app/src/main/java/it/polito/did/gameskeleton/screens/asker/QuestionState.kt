package it.polito.did.gameskeleton.screens.asker


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import it.polito.did.gameskeleton.QuestionDTO

data class Questions(
    val questionsState: List<QuestionState>
) {
    var currentQuestionIndex by mutableStateOf(0)
}

class QuestionState(
    val question: QuestionDTO,
    val questionIndex: Int,
    val totalQuestions: Int,
    val showPrevious: Boolean,
    val showDone: Boolean
) {
    var enableNext by mutableStateOf(false)
    var givenAnswerId by mutableStateOf("")
}