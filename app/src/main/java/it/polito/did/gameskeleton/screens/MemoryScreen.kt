package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import it.polito.did.gameskeleton.EmojiModel
import it.polito.did.gameskeleton.EmojiViewModel
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ScreenName

val viewModel : EmojiViewModel = EmojiViewModel()

@Composable
fun MainContent(
    vm: GameViewModel
) {
    viewModel.loadEmojis()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("MEMORY")
                },
                actions = {
                    IconButton(onClick = { viewModel.loadEmojis()}) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = "Reload Game"
                        )
                    }
                }
            )
        }
    ) {
        val cards: List<EmojiModel> by viewModel.getEmojis().observeAsState(listOf())
        CardsGrid(cards = cards, vm)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardsGrid(cards: List<EmojiModel>, vm: GameViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4),
    ) {
        items(cards.count()) { cardIndex ->
            CardItem(cards[cardIndex], vm)
        }
    }
}

@Composable
private fun CardItem(emoji: EmojiModel, vm : GameViewModel ) {
    Box(
        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(150.dp)
                .background(
                    color = Color.Black.copy(alpha = if (emoji.isVisible) 0.4F else 0.0F),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    if (emoji.isVisible) {
                        viewModel.updateShowVisibleCard(
                            emoji.id,
                            vm.screenName as MutableLiveData<ScreenName>,
                            vm
                        )
                    }
                }

        ) {
            if (emoji.isSelect) {
                Text(
                    text = emoji.char,
                    fontSize = 32.sp
                )
            }
        }
    }
}