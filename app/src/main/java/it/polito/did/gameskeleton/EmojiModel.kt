package it.polito.did.gameskeleton

import java.util.*

class EmojiModel(
    var char: String,
    var isVisible: Boolean = true,
    var isSelect: Boolean = false,
    var id: String = UUID.randomUUID().toString(),
) {}