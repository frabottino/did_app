package it.polito.did.gameskeleton.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import it.polito.did.gameskeleton.GameViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun ExposedDropdownMenu(
    items: List<String>,
    selected: String
) {
    var topText by remember { mutableStateOf(selected) }
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions
            .filter { it is PressInteraction.Press }
            .collect {
                expanded = !expanded
            }
    }
    ExposedDropdownMenuStack(
        textField = {
            OutlinedTextField(
                value = topText,
                onValueChange = {},
                interactionSource = interactionSource,
                readOnly = true,
                trailingIcon = {
                    val rotation by animateFloatAsState(if (expanded) 180F else 0F)
                    Icon(
                        rememberVectorPainter(Icons.Default.ArrowDropDown),
                        contentDescription = "Dropdown Arrow",
                        Modifier.rotate(rotation),
                    )
                }
            )
        },
        dropdownMenu = { boxWidth, itemHeight ->
            Box(
                Modifier
                    .width(boxWidth)
                    .wrapContentSize(Alignment.TopStart)
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .height(itemHeight)
                                .width(boxWidth),
                            onClick = {
                                expanded = false
                                GameViewModel.getInstance().setTempCap(item)
                                topText = item
                            }
                        ) {
                            Text(item)
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun ExposedDropdownMenuStack(
    textField: @Composable () -> Unit,
    dropdownMenu: @Composable (boxWidth: Dp, itemHeight: Dp) -> Unit
) {
    SubcomposeLayout { constraints ->
        val textFieldPlaceable =
            subcompose(ExposedDropdownMenuSlot.TextField, textField).first().measure(constraints)

        val dropdownPlaceable = subcompose(ExposedDropdownMenuSlot.Dropdown) {
            dropdownMenu(textFieldPlaceable.width.toDp(), textFieldPlaceable.height.toDp())
        }.first().measure(constraints)

        layout(textFieldPlaceable.width, textFieldPlaceable.height) {
            textFieldPlaceable.placeRelative(0, 0)
            dropdownPlaceable.placeRelative(0, textFieldPlaceable.height)
        }
    }
}

private enum class ExposedDropdownMenuSlot { TextField, Dropdown }