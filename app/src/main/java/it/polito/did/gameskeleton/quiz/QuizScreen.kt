package it.polito.did.gameskeleton.screens

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.quiz.Constants
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@OptIn(ExperimentalUnitApi::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier
) {

    var pts = 15
    var questionCount = 0
    var timer = object: CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if(questionCount == 2){
                pts = (millisUntilFinished/1000).toInt()
                onFinish()
            }
        }
        override fun onFinish() {
            if(Constants.quizCount == 0) pts = (pts/2)
            else if(Constants.quizCount == -2) pts = 0
            if(questionCount == 2) GameViewModel.getInstance().sendMiniPts(pts)
            questionCount = -1
        }
    }

    var questID : Int = java.util.Random().nextInt( 10)
    var questID2: Int
    do {
        questID2 = java.util.Random().nextInt(10)
        println("quest 1 $questID quest2 $questID2")
    }while(questID==questID2)

    timer.start()
//TODO riposizionare decentemente i pezzi

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.weight(0.3f))
            Text(
                text = "Quiz", textAlign = TextAlign.Center,
                modifier = Modifier.align(CenterHorizontally),
                color = MaterialTheme.colors.primary,
                fontSize = TextUnit(10f, TextUnitType.Em)
            )
            Spacer(Modifier.weight(0.4f))
            Text(
                text = Constants.setQuestionText(questID).question, textAlign = TextAlign.Center,
                modifier = Modifier.align(CenterHorizontally),
                color = MaterialTheme.colors.secondary,
                fontSize = TextUnit(10f, TextUnitType.Em)
            )
            Spacer(Modifier.height(32.dp))
            Row {
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(Modifier.height(32.dp))
            Row{
                CustomButton(
                    modifier = Modifier
                        .padding(56.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {Constants.checkAnswer(1, Constants.setQuestionText(questID))
                                questID = questID2
                                if(questionCount++ == 2) timer.cancel()
                              },
                    text = Constants.setQuestionText(questID).optionOne
                )
                CustomButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {Constants.checkAnswer(2, Constants.setQuestionText(questID))
                                questID = questID2
                                if(questionCount++ == 2) timer.cancel()
                              },
                    text = Constants.setQuestionText(questID).optionTwo
                )
            }
            Spacer(
                Modifier.height(32.dp)
            )
            Row{
                CustomButton(
                    modifier = Modifier
                        .padding(56.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {Constants.checkAnswer(3, Constants.setQuestionText(questID))
                                questID = questID2
                                if(questionCount++ == 2) timer.cancel()
                              },
                    text = Constants.setQuestionText(questID).optionThree
                )
            }
            Spacer(Modifier.weight(1f))
            Text(text = "${Constants.quizCount}",
                style = TextStyle( color = Color.Black, fontSize = 24.sp)
            )
        }
    }
}




@Preview
@Composable
fun PreviewQuizScreen() {
    GameSkeletonTheme {
        QuizScreen()
    }

}