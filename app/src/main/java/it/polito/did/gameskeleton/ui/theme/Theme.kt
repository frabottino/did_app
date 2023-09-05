package it.polito.did.gameskeleton.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = ThemeBlue,
    primaryVariant = ThemeBlue,
    secondary = ThemeBlue
)

private val LightColorPaletteRed = lightColors(
    primary = ThemeRed,
    primaryVariant = ThemeRed,
    secondary = ThemeRed
)

private val LightColorPaletteBlue = lightColors(
    primary = ThemeBlue,
    primaryVariant = ThemeBlue,
    secondary = ThemeBlue
)

private val LightColorPaletteGreen = lightColors(
    primary = ThemeGreen,
    primaryVariant = ThemeGreen,
    secondary = ThemeGreen
)

private val LightColorPaletteYellow = lightColors(
    primary = ThemeYellow,
    primaryVariant = ThemeYellow,
    secondary = ThemeYellow
)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple200,
    secondary = Purple200
)

private val DarkColorPaletteBlue = darkColors(
    primary = ThemeBlue,
    primaryVariant = ThemeBlue,
    secondary = ThemeBlue
)

private val DarkColorPaletteGreen = darkColors(
    primary = ThemeGreen,
    primaryVariant = ThemeGreen,
    secondary = ThemeGreen
)

private val DarkColorPaletteYellow = darkColors(
    primary = ThemeYellow,
    primaryVariant = ThemeYellow,
    secondary = ThemeYellow
)

private val DarkColorPaletteRed = darkColors(
    primary = ThemeRed,
    primaryVariant = ThemeRed,
    secondary = ThemeRed

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