package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
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
import it.polito.did.gameskeleton.quiz.Constants
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

var quizCount by mutableStateOf(0)

@OptIn(ExperimentalUnitApi::class)
@Composable
fun QuizScreen(

    modifier: Modifier = Modifier
) {
    val matchId = remember {
        mutableStateOf("")
    }
    var nameId = remember {
        mutableStateOf("")
    }

    var questID : Int = java.util.Random().nextInt( 10)

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
                text = Constants.setQuestionText(5).question, textAlign = TextAlign.Center,
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
                    onClick = {Constants.checkAnswer(1, Constants.setQuestionText(questID))},
                    text = Constants.setQuestionText(questID).optionOne
                )
                CustomButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {Constants.checkAnswer(2, Constants.setQuestionText(questID))},
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
                    onClick = {Constants.checkAnswer(3, Constants.setQuestionText(questID))},
                    text = Constants.setQuestionText(questID).optionThree
                )
                CustomButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {Constants.checkAnswer(4, Constants.setQuestionText(questID))},
                    text = Constants.setQuestionText(questID).optionFour
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