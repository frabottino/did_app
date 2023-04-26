package it.polito.did.gameskeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.polito.did.gameskeleton.screens.viewModel

class EmojiViewModel : ViewModel() {
    private val emojis: MutableLiveData<MutableList<EmojiModel>> by lazy {
        MutableLiveData<MutableList<EmojiModel>>()
    }

    fun getEmojis(): LiveData<MutableList<EmojiModel>> {
        return emojis
    }

    fun loadEmojis() {
        emojis.value = mutableListOf(
            /*
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
            */
            EmojiModel("\uD83C\uDF33"), //albero
            EmojiModel("\uD83C\uDF33"), //albero
            EmojiModel("\uD83C\uDF3C"), //fiore
            EmojiModel("\uD83C\uDF3C"), //fiore
            EmojiModel("\uD83C\uDF0D"), //pianeta
            EmojiModel("\uD83C\uDF0D"), //pianeta
            EmojiModel("\uD83D\uDCA7"), //acqua
            EmojiModel("\uD83D\uDCA7"), //acqua
            EmojiModel("☀"), //sole
            EmojiModel("☀"), //sole
            EmojiModel("⚡"), //fulmine
            EmojiModel("⚡"), //fulmine
        ).apply { shuffle() }
    }

    fun updateShowVisibleCard(id: String, msn : MutableLiveData<ScreenName>) {
        val selects: List<EmojiModel>? = emojis.value?.filter { it -> it.isSelect }
        val selectCount: Int = selects?.size ?: 0
        var charFind: String = "";
        if (selectCount >= 2) {
            val hasSameChar: Boolean = selects!!.get(0).char == selects.get(1).char
            if (hasSameChar) {
                charFind = selects.get(0).char
            }
        }

        val list: MutableList<EmojiModel>? = emojis.value?.map { it ->
            if (selectCount >= 2) {
                it.isSelect = false
            }

            if (it.char == charFind) {
                it.isVisible = false
            }

            if (it.id == id) {
                it.isSelect = true
            }

            it
        } as MutableList<EmojiModel>?

        val visibleCount: Int = list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            //loadEmojis()
            //msn.value = ScreenName.Home("14")
            msn.value = ScreenName.Cards("14") //TODO mettere a posto il parametro string da passare
            //var vm = GameViewModel() //TODO singleton
            val vm = GameViewModel.getInstance()
            vm.nextTurn()
            return
        }

        emojis.value?.removeAll { true }
        emojis.value = list
    }
}