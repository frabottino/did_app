package it.polito.did.gameskeleton.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import it.polito.did.gameskeleton.screens.QuizScreen

object Constants {

    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANS : String = "correct_answers"

    var quizCount by mutableStateOf(0)

    fun getQuestions():ArrayList<Question>{

        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,"What country does this flag belong to?",
            "Argentina","Spain",
            "Egypt","Seychelles", 1

        )
        questionsList.add(que1)

        val que2 = Question(
            2,"What country does this flag belong to?",
            "America","Australia",
            "New Zealand","Uzbekistan", 2

        )
        questionsList.add(que2)

        val que3 = Question(
            3,"What country does this flag belong to?",
            "Germany","Belgium",
            "Armenia","France", 2

        )
        questionsList.add(que3)

        val que4 = Question(
            4,"What country does this flag belong to?",
            "Japan","India",
            "Pakistan","Brazil", 4

        )
        questionsList.add(que4)

        val que5 = Question(
            5,"What country does this flag belong to?",
            "Denmark","Poland",
            "Brazil","UK", 1

        )
        questionsList.add(que5)

        val que6 = Question(
            6,"What country does this flag belong to?",
            "USA","Vietnam",
            "Fiji","UAE", 3

        )
        questionsList.add(que6)

        val que7 = Question(
            7,"What country does this flag belong to?",
            "Greece","Australia",
            "Germany","Austria", 3

        )
        questionsList.add(que7)

        val que8 = Question(
            8,"What country does this flag belong to?",
            "India","China",
            "Kuwait","Australia", 1

        )
        questionsList.add(que8)

        val que9 = Question(
            9,"What country does this flag belong to?",
            "Russia","New Zealand",
            "Armenia","Chile", 2

        )
        questionsList.add(que9)

        return questionsList
    }

    val provaQue = Question(
        10, "Daje Roma Daje?",
        "Ya Ya Uuh", "Come On",
        "Aaa Lazio", "Ok Google", 1
    )

    fun setQuestionText(id : Int) : Question{
        return getQuestions()[id]
    }

    fun checkAnswer(ansId : Int, questId : Question) : Boolean{
        if(ansId == questId.correctAnswer) {
            quizCount++
        }
        else quizCount--
        return ansId == questId.correctAnswer
    }
}