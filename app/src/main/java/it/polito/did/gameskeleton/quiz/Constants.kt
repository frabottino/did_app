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
            1,"Quando sono state costruite le prime centrali idroelettriche funzionanti al mondo?",
            "Nel XVIII secolo","Nel XIX secolo",
            "Nel XX secolo", 2

        )
        questionsList.add(que1)

        val que2 = Question(
            2," Qual è il processo che le centrali idroelettriche utilizzano per generare energia elettrica? ",
            "Trasformazione dell'energia solare in meccanica e poi in elettrica",
            "Trasformazione dell'energia cinetica dell'acqua in solare e poi in elettrica",
            "Trasformazione dell'energia cinetica dell'acqua in meccanica e poi in elettrica",
            3

        )
        questionsList.add(que2)

        val que3 = Question(
            3,"Quali sono i diversi tipi di centrali idroelettriche?",
            "Solo centrali ad acqua fluente",
            "Solo centrali a serbatoio",
            "Centrali ad acqua fluente, centrali a serbatoio, centrali ad accumulo, centrali a pompaggio, centrali mareomotrici",
            3

        )
        questionsList.add(que3)

        val que4 = Question(
            4,"Come funzionano le centrali ad acqua fluente?",
            "Utilizzano l'energia termica dell'acqua per far girare le turbine",
            "Utilizzano l'energia cinetica dell'acqua in movimento per far girare le turbine e generare energia elettrica ",
            "Utilizzano l'energia solare per far girare le turbine",
            2

        )
        questionsList.add(que4)

        val que5 = Question(
            5,"What country does this flag belong to?",
            "Denmark","Poland",
            "Brazil", 1

        )
        questionsList.add(que5)

        val que6 = Question(
            6,"Come funzionano le centrali a serbatoio? ",
            " Possono immagazzinare energia per restituirlo in momenti di picco di domanda ",
            "Non necessitano di un corso d'acqua costante",
            "Sono più economiche da costruire delle altre centrali idroelettriche",
            1

        )
        questionsList.add(que6)

        val que7 = Question(
            7,"Come funzionano le centrali a pompaggio? ",
            "Utilizzano l'energia solare per generare energia elettrica",
            "Sfruttano la forza del vento per far girare le turbine",
            "Pompano l'acqua in un serbatoio posto a un'altezza superiore per immagazzinare energia ",
            3

        )
        questionsList.add(que7)

        return questionsList
    }

    fun setQuestionText(id : Int) : Question{
        return getQuestions()[id]
    }

    fun checkAnswer(ansId : Int, questId : Question) : Boolean{
        if(ansId == questId.correctAnswer) {
            quizCount++
        }
        else {
            quizCount--
        }
        return ansId == questId.correctAnswer
    }
}