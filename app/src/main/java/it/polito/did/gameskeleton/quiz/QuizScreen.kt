package it.polito.did.gameskeleton.screens

import android.os.CountDownTimer
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.did_app.R
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.quiz.Constants
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import it.polito.did.gameskeleton.ui.theme.Purple200
import it.polito.did.gameskeleton.ui.theme.Teal200
import it.polito.did.gameskeleton.ui.theme.robotoFamily


@OptIn(ExperimentalUnitApi::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier
) {

    var pts = 15
    var questionCount = 0
    var ansColor1 by remember { mutableStateOf(Color.White) }
    var ansColor2 by remember { mutableStateOf(Color.White) }

    var timer = object : CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            pts = (millisUntilFinished / 1000).toInt()
            if (questionCount == 2) onFinish()
        }

        override fun onFinish() {
            if (Constants.quizCount == 0) pts = (pts / 2)
            else if (Constants.quizCount < 0) pts = 0
            when (questionCount) {
                2 -> {
                    GameViewModel.getInstance().sendMiniPts(pts)
                    questionCount = -1
                }
                1 -> {
                    if (Constants.quizCount == 1) pts = 2
                    GameViewModel.getInstance().sendMiniPts(pts)
                    questionCount = -1
                    GameViewModel.getInstance().onEndMiniGame()
                }
                else -> GameViewModel.getInstance().onEndMiniGame()
            }
            questionCount = 0
            Constants.quizCount = 0
        }
    }

    var questID: Int = java.util.Random().nextInt(55)
    var questID2: Int
    do {
        questID2 = java.util.Random().nextInt(55)
        println("quest1 $questID quest2 $questID2")
    } while (questID == questID2)

    timer.start()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        backgroundColor = Color.Transparent,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.question_bg),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(64.dp))
            Column(
                //Modifier.padding(24.dp).background(Color.DarkGray.copy(alpha = 0.2f)).border(2.dp, Color.White).padding(6.dp)
            ) {
                Text(
                    text = "QUIZ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally),
                    color = Color.White,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = TextUnit(5f, TextUnitType.Em)
                )
                Text(
                    text = Constants.setQuestionText(questID).question,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 8.dp, horizontal = 24.dp),
                    color = Color.White,
                    fontSize = TextUnit(5.5f, TextUnitType.Em),


                    )
            }
            Spacer(Modifier.height(32.dp))
            Row {
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)

                )
            }
            Spacer(Modifier.height(32.dp))
            Column {
                modifier.padding(horizontal = 28.dp)
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                        .padding(24.dp),
                    fontSize = 20.sp,
                    onClick = {
                        if(Constants.checkAnswer(1, Constants.setQuestionText(questID))){
                            if(questionCount == 0) ansColor1 = Color.Green
                            if(questionCount == 1) ansColor2 = Color.Green
                        }
                        else{
                            if(questionCount == 0) ansColor1 = Color.Red
                            if(questionCount == 1) ansColor2 = Color.Red
                        }
                        questID = questID2
                        if (questionCount++ == 2) timer.onFinish()
                    },
                    text = Constants.setQuestionText(questID).optionOne
                )
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                        .padding(24.dp),
                    fontSize = 20.sp,
                    onClick = {
                        if(Constants.checkAnswer(2, Constants.setQuestionText(questID))){
                            if(questionCount == 0) ansColor1 = Color.Green
                            if(questionCount == 1) ansColor2 = Color.Green
                        }
                        else{
                            if(questionCount == 0) ansColor1 = Color.Red
                            if(questionCount == 1) ansColor2 = Color.Red
                        }
                        questID = questID2
                        if (questionCount++ == 2) timer.onFinish()
                    },
                    text = Constants.setQuestionText(questID).optionTwo
                )

                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                        .padding(24.dp),
                    fontSize = 20.sp,
                    onClick = {
                        if(Constants.checkAnswer(3, Constants.setQuestionText(questID))){
                            if(questionCount == 0) ansColor1 = Color.Green
                            if(questionCount == 1) ansColor2 = Color.Green
                        }
                        else{
                            if(questionCount == 0) ansColor1 = Color.Red
                            if(questionCount == 1) ansColor2 = Color.Red
                        }
                        questID = questID2
                        if (questionCount++ == 2) timer.onFinish()
                    },
                    text = Constants.setQuestionText(questID).optionThree
                )
            }
            Spacer(Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.bonuscard3),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(40.dp, ansColor1, CircleShape) // add a border (optional)
                )
                Image(
                    painter = painterResource(R.drawable.bonuscard3),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(40.dp, ansColor2, CircleShape)   // add a border (optional)
                )
                /*
                Text(
                    text = "${Constants.quizCount}",
                    style = TextStyle(color = Color.Black, fontSize = 24.sp)
                )

                 */
            }
            Spacer(Modifier.weight(1f))
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