package it.polito.did.gameskeleton.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Blue800,
    primaryVariant = Blue800,
    secondary = Teal200
)

private val LightColorPaletteRed = lightColors(
    primary = Color.Red,
    primaryVariant = Color.Red,
    secondary = Color.Red
)

private val LightColorPaletteBlue = lightColors(
    primary = Color.Blue,
    primaryVariant = Color.Blue,
    secondary = Color.Blue
)

private val LightColorPaletteGreen = lightColors(
    primary = Color.Green,
    primaryVariant = Color.Green,
    secondary = Color.Green
)

private val LightColorPaletteYellow = lightColors(
    primary = Color.Yellow,
    primaryVariant = Color.Yellow,
    secondary = Color.Yellow
)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple200,
    secondary = Purple200
)

private val DarkColorPaletteBlue = darkColors(
    primary = Color.Blue,
    primaryVariant = Color.Blue,
    secondary = Color.Blue
)

private val DarkColorPaletteGreen = darkColors(
    primary = Color.Green,
    primaryVariant = Color.Green,
    secondary = Color.Green
)

private val DarkColorPaletteYellow = darkColors(
    primary = Color.Yellow,
    primaryVariant = Color.Yellow,
    secondary = Color.Yellow
)

private val DarkColorPaletteRed = darkColors(
    primary = Color.Red,
    primaryVariant = Color.Red,
    secondary = Color.Red

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun GameSkeletonTheme(team: String? = null, darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        if(team == "Red") DarkColorPaletteRed
        else if(team == "Blue") DarkColorPaletteBlue
        else if(team == "Green") DarkColorPaletteGreen
        else if(team == "Yellow") DarkColorPaletteYellow
        else DarkColorPalette

    } else {
        if(team == "Red") LightColorPaletteRed
        else if(team == "Blue") LightColorPaletteBlue
        else if(team == "Green") LightColorPaletteGreen
        else if(team == "Yellow") LightColorPaletteYellow
        else LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
@Composable
fun setSkeleton(x : String){
    GameSkeletonTheme(team = x) {
    }
}

@Composable
fun BottomNavBarTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

    @Composable
    fun EmojiMemoryJetpackTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable() () -> Unit
    ) {
        val colors = if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
}