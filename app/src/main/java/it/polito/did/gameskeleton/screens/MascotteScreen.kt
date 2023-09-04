package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.gameskeleton.GameManager
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

private val vm = GameViewModel.getInstance()

@Composable
fun MascotteScreen(
    team: String,
    onStartMemory: () -> Unit,
    onStartFlappy: () -> Unit,
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    GameSkeletonTheme(team = team) {
        Column(
            modifier
                .fillMaxSize()
                .background(Color.White),
            Arrangement.Top
        ) {
            if (vm.getPlayerTurn() && vm.getTeamTurn()) {
                Text(
                    text = "It is your team's turn, now it's up to you!",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 10.dp)
                        .offset(y = 100.dp)
                )
            }

            Spacer(modifier.height(280.dp))
            when (vm.getTurnFromGM()) {
                3 -> {
                    CustomButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        enabled = vm.getPlayerTurn() && vm.getTeamTurn(),
                        onClick = { onStartMemory() },
                        text = "GO TO MEMORY",
                        fontSize = 18.sp
                    )
                }
                2 -> {
                    CustomButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        enabled = vm.getPlayerTurn() && vm.getTeamTurn(),
                        onClick = { onStartFlappy() },
                        text = "GO TO FLAPPY",
                        fontSize = 18.sp
                    )
                }
                1 -> {
                    CustomButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        enabled = vm.getPlayerTurn() && vm.getTeamTurn(),
                        onClick = { onStartQuiz() },
                        text = "GO TO QUIZ",
                        fontSize = 18.sp
                    )
                }
            }
            /*
            Button(
                modifier = modifier.align(Alignment.Center),
                //enabled = vm.getPlayerTurn()&&vm.getTeamTurn(),
                onClick = { onStartMemory() }) {
                Text("GO TO QUIZ")
            }

             */
        }
    }
}

@Composable
@Preview
fun MascotteScreenPreview() {
    MascotteScreen(
        team = "Green",
        onStartMemory = { /*TODO*/ },
        onStartFlappy = { /*TODO*/ },
        onStartQuiz = { /*TODO*/ })
}
