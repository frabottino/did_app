package it.polito.did.gameskeleton.screens

import androidx.compose.ui.graphics.vector.ImageVector
import it.polito.did.gameskeleton.screens.icons.Splash
import kotlin.collections.List as ____KtList

public object Icons

private var __Icons: ____KtList<ImageVector>? = null

public val Icons.Icons: ____KtList<ImageVector>
  get() {
    if (__Icons != null) {
      return __Icons!!
    }
    __Icons= listOf(Splash)
    return __Icons!!
  }
